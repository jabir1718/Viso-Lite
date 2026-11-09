import assert from 'node:assert/strict';
import fs from 'node:fs';
import vm from 'node:vm';
import { TextDecoder, TextEncoder } from 'node:util';

const html = fs.readFileSync('app/src/main/assets/index.html', 'utf8');
const start = html.indexOf('const VISO_CRC_TABLE=');
const end = html.indexOf('function visoSafeFileName', start);
assert(start >= 0 && end > start, 'VISO ZIP/CRC functions must exist in the studio source');

const source = html.slice(start, end);
const sandbox = { Blob, DataView, TextDecoder, TextEncoder, Uint8Array, Uint32Array };
vm.runInNewContext(
  source + '\nglobalThis.__archive = { visoZip, visoUnzip, visoCRC32 };',
  sandbox,
  { timeout: 2000 }
);
const { visoZip, visoUnzip, visoCRC32 } = sandbox.__archive;

const projectBytes = new TextEncoder().encode('{"schema":"viso-project-v3","name":"QA"}');
const mediaBytes = new Uint8Array([0, 1, 2, 3, 127, 128, 254, 255]);
const files = [
  { name: 'project.json', data: projectBytes },
  { name: 'assets/01-audio-track.mp3', data: mediaBytes }
];

assert.equal(visoCRC32(new TextEncoder().encode('123456789')), 0xcbf43926, 'CRC32 standard vector');
const zip = visoZip(files);
const bytes = new Uint8Array(await zip.arrayBuffer());
const restored = visoUnzip(bytes);
assert.deepEqual([...restored['project.json']], [...projectBytes], 'project JSON round-trip');
assert.deepEqual([...restored['assets/01-audio-track.mp3']], [...mediaBytes], 'media round-trip');

function findBytes(haystack, needle) {
  outer: for (let i = 0; i <= haystack.length - needle.length; i++) {
    for (let j = 0; j < needle.length; j++) if (haystack[i + j] !== needle[j]) continue outer;
    return i;
  }
  return -1;
}

const corrupt = bytes.slice();
const mediaOffset = findBytes(corrupt, mediaBytes);
assert(mediaOffset >= 0, 'test media must be present in ZIP bytes');
corrupt[mediaOffset] ^= 0x01;
assert.throws(() => visoUnzip(corrupt), /checksum/i, 'corrupted media must fail CRC validation');

const unsafe = new Uint8Array(await visoZip([
  { name: '../outside.bin', data: new Uint8Array([1]) }
]).arrayBuffer());
assert.throws(() => visoUnzip(unsafe), /unsafe project archive path/i, 'unsafe paths must be rejected');

const duplicate = new Uint8Array(await visoZip([
  { name: 'same.bin', data: new Uint8Array([1]) },
  { name: 'same.bin', data: new Uint8Array([2]) }
]).arrayBuffer());
assert.throws(() => visoUnzip(duplicate), /duplicate project archive entry/i, 'duplicate names must be rejected');

const headerMismatch = bytes.slice();
new DataView(headerMismatch.buffer).setUint16(8, 8, true);
assert.throws(() => visoUnzip(headerMismatch), /header mismatch/i, 'local/central header mismatch must be rejected');


const validationStart = html.indexOf('function validateVISOProjectManifest(');
const validationEnd = html.indexOf('async function restoreVISOProjectAssets', validationStart);
assert(validationStart >= 0 && validationEnd > validationStart, 'project manifest validator must exist');
const validationSandbox = {};
vm.runInNewContext(
  html.slice(validationStart, validationEnd) + '\nglobalThis.__validate = validateVISOProjectManifest;',
  validationSandbox,
  { timeout: 2000 }
);
const validate = validationSandbox.__validate;
const validProject = {
  schema: 'viso-project-v3',
  settings: { state: {}, layers: { objects: [{}], lights: [] } },
  assets: [
    { name: 'assets/01-audio.mp3', kind: 'audio', size: 3 },
    { name: 'assets/02-overlay.png', kind: 'overlay-0', size: 2 }
  ]
};
const validEntries = {
  'assets/01-audio.mp3': new Uint8Array([1, 2, 3]),
  'assets/02-overlay.png': new Uint8Array([4, 5])
};
assert.equal(validate(validProject, validEntries), true, 'valid project manifest');
assert.throws(() => validate(validProject, {}), /asset is missing/i, 'missing manifest assets must fail');
assert.throws(() => validate(validProject, {
  ...validEntries,
  'assets/01-audio.mp3': new Uint8Array([1])
}), /size mismatch/i, 'asset size mismatch must fail');
assert.throws(() => validate({
  ...validProject,
  assets: [...validProject.assets, { name: 'assets/03-unknown.bin', kind: 'unknown', size: 1 }]
}, { ...validEntries, 'assets/03-unknown.bin': new Uint8Array([1]) }), /unsupported project asset type/i);
assert.throws(() => validate({
  ...validProject,
  assets: [...validProject.assets, { name: 'assets/03-audio.mp3', kind: 'audio', size: 1 }]
}, { ...validEntries, 'assets/03-audio.mp3': new Uint8Array([1]) }), /duplicate project asset type/i);
assert.throws(() => validate({
  ...validProject,
  assets: validProject.assets.map(asset => asset.kind === 'overlay-0' ? { ...asset, kind: 'overlay-1' } : asset)
}, validEntries), /overlay asset sequence/i);
assert.throws(() => validate({
  ...validProject,
  settings: { state: {}, layers: { objects: [], lights: [] } }
}, validEntries), /overlay asset count/i);
assert.throws(() => validate({
  ...validProject,
  assets: [{ name: 'assets/../outside.bin', kind: 'audio', size: 1 }]
}, { 'assets/../outside.bin': new Uint8Array([1]) }), /invalid project asset record/i);


const registryStart = html.indexOf('const VISO_RENDER_MODULES=');
const registryEnd = html.indexOf('function visoToolCategory', registryStart);
assert(registryStart >= 0 && registryEnd > registryStart, 'tool registry hooks must exist');
const registrySandbox = {
  localStorage: { getItem: () => null, setItem: () => {} },
  Event: class { constructor(type) { this.type = type; } }
};
vm.runInNewContext(
  html.slice(registryStart, registryEnd) +
    '\nglobalThis.__registry = { registerVISOEffectTool, collectVISORegistryState, restoreVISORegistryState, renderVISOStage, modules: VISO_RENDER_MODULES, cache: VISO_RENDER_CACHE };',
  registrySandbox,
  { timeout: 2000 }
);
const registry = registrySandbox.__registry;
assert.equal(registry.modules['Background'].stage, 'background');
assert.equal(registry.modules['Media'].stage, 'media');
assert.equal(registry.modules['Media'].order, 10);
assert.equal(registry.modules['Background'].order, 10);
assert.equal(registry.modules['Layers · Tools box'].stage, 'scene');
assert.equal(registry.modules['Layers · Tools box'].order, 10);
assert.equal(registry.modules['Text · Logo Studio'].stage, 'overlay');
assert.equal(registry.modules['Text · Logo Studio'].order, 80);
assert.equal(typeof registry.modules['Text · Logo Studio'].render, 'function');
assert.equal(registry.modules['Surface Reflection'].stage, 'post');
const dispatched = [];
const rangeControl = {
  id: 'rangeA', type: 'range', value: '22',
  dispatchEvent: event => dispatched.push(event.type)
};
const checkControl = {
  id: 'checkA', type: 'checkbox', checked: true,
  dispatchEvent: event => dispatched.push(event.type)
};
const fakePanel = { querySelectorAll: () => [rangeControl, checkControl] };
registry.registerVISOEffectTool({
  id: 'test-tool', name: 'Test Tool', category: 'overlay', panel: fakePanel, controls: []
});
const registryState = registry.collectVISORegistryState();
assert.deepEqual(JSON.parse(JSON.stringify(registryState['test-tool'])), {
  rangeA: '22', checkA: true
}, 'default tool serialization');
rangeControl.value = '91';
checkControl.checked = false;
registry.restoreVISORegistryState(registryState);
assert.equal(rangeControl.value, '22', 'default tool restore for range');
assert.equal(checkControl.checked, true, 'default tool restore for checkbox');
assert.deepEqual(dispatched, ['input', 'change', 'input', 'change'], 'restore dispatches control events');
const renderOrder = [];
registry.registerVISOEffectTool({
  id: 'late-render', name: 'Late Render', category: 'overlay', panel: fakePanel, controls: [],
  renderStage: 'overlay', renderOrder: 20, render: () => renderOrder.push('late')
});
registry.registerVISOEffectTool({
  id: 'early-render', name: 'Early Render', category: 'overlay', panel: fakePanel, controls: [],
  renderStage: 'overlay', renderOrder: 10, render: () => renderOrder.push('early')
});
registry.renderVISOStage('overlay', {}, new Float32Array(3), 16);
assert.deepEqual(renderOrder, ['early', 'late'], 'effect modules render in declared order');
assert.deepEqual(JSON.parse(JSON.stringify(registry.cache.overlay.map(tool => tool.id))), ['early-render', 'late-render'], 'render stage cache stays sorted after registration');

console.log('VISO tests passed: archive integrity, project manifests, and registry serialize/restore hooks');

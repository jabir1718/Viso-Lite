import assert from 'node:assert/strict';
import fs from 'node:fs';
import vm from 'node:vm';
import { TextDecoder, TextEncoder } from 'node:util';

const html = fs.readFileSync('app/src/main/assets/index.html', 'utf8');
for (const id of ['bgColor', 'bgTintColor', 'sBgTint', 'sBgBright', 'sBgContrast', 'sBgSat', 'sBgHue', 'sBgX', 'sBgY', 'selBgPattern', 'sBgPattern', 'sBgPatternScale']) {
  assert(html.includes('id="' + id + '"'), 'background control must exist: ' + id);
}
for (const id of ['bSpec', 'sSpecBars', 'sSpecRad', 'sSpecThick', 'sSpecGlow', 'sSpecLevel', 'sSpecAtk', 'sSpecRel', 'sSpecPeak', 'sSpecSpin']) {
  assert(html.includes('id="' + id + '"'), 'spectrum control must exist: ' + id);
}
for (const id of ['bBpm', 'bTap', 'sBeatSens', 'sBeatScale', 'bBeatMarkers', 'bMetronome', 'bDownbeat', 'beatColor']) {
  assert(html.includes('id="' + id + '"'), 'beat control must exist: ' + id);
}
assert(html.includes("const SPEC_MODES=[['radial','Radial'],['ring','Ring'],['sphere','Sphere 3D'],['tunnel','Tunnel'],['mirror','Mirror'],['dual','Dual'],['wave','Wave'],['led','LED']]"), 'all spectrum visual modes must be registered');
assert(html.includes("const SPEC_RANGES=[['full','Full'],['low','Low'],['balanced','Balanced'],['high','High'],['vocals','Vocals'],['changes','Changes']]"), 'all spectrum frequency focus modes must be registered');
const spectrumStart = html.indexOf('function drawSpectrum(ctx,scale)');
const spectrumEnd = html.indexOf('\nfunction ', spectrumStart + 20);
const spectrumSource = html.slice(spectrumStart, spectrumEnd);
assert(spectrumSource.includes("if(M==='ring')"), 'Ring spectrum must have its own renderer');
assert(spectrumSource.includes('outer=R0+v*amp'), 'Ring spectrum bars must respond to live audio amplitude');
assert(spectrumSource.includes("else if(M==='sphere')"), 'Sphere spectrum must have its own renderer');
assert(spectrumSource.includes("else if(M==='tunnel')"), 'Tunnel spectrum must have its own renderer');
assert(spectrumSource.includes('rings=3,'), 'Tunnel spectrum must render multiple depth rings');
assert(spectrumSource.includes('stride=Math.max(1,Math.ceil(n/32))'), 'Tunnel spectrum must cap per-ring segments for mobile performance');
assert(spectrumSource.includes('const rows=10'), 'LED spectrum must use a mobile-friendly dot grid');
assert(spectrumSource.includes('ctx.fillRect(cx-len,yl,len,bh*.86);ctx.fillRect(cx,yr,len,bh*.86)'), 'Dual spectrum bars must extend horizontally from the centre');
assert(html.includes('id="sLogoPulse"'), 'custom logo audio-reactive size control must exist');
assert(html.includes('autoOn,autoEvery,lockChip:{...lockChip}'), 'auto mode timing and lock toggles must be saved in project and undo state');
assert(html.includes('restoreVISOAutoState(d)'), 'auto mode and lock controls must restore from saved projects');
assert(html.includes('exportSettings:{aspect,baseRes,FPS,quality,formatChoice,loopSong,stopAtSongEnd}'), 'export profile, resolution, FPS, format and playback toggles must be saved');
assert(html.includes('stopAtSongEnd},bgVizMask,colors'), 'background visualizer mask toggle must be saved');
assert(html.includes('if(d.bgVizMask!==undefined)bgVizMask=!!d.bgVizMask;else bgVizMask=true'), 'background visualizer mask must restore with legacy default');
assert(html.includes("['tBvm',bgVizMask]"), 'background visualizer mask button must synchronize after restore');
assert(html.includes('restoreVISOExportState(d.exportSettings||{})'), 'saved export settings must restore with old-project defaults');
assert(html.includes('restoreVISOExportState({});};'), 'reset must restore default export settings');
assert(html.includes("bgVizMask=true;$('tBvm').classList.add('on')"), 'reset must restore background visualizer mask default');
assert(html.includes('autoEvery=3;Object.keys(lockChip).forEach(key=>lockChip[key]=false)'), 'reset must restore auto mode timing and unlocked defaults');
assert(html.includes('logoPulse,logoRotation'), 'logo pulse must be included in saved advanced state');
assert(html.includes('sync(\'sLogoPulse\',logoPulse)'), 'logo pulse must restore its UI value');
assert(html.includes('pulseScale=1+b*logoPulse/100*.18'), 'logo pulse must react to audio level in the renderer');
for (const kind of ['audio', 'media-image', 'media-video', 'background-image', 'background-video', 'logo', 'font']) {
  assert(html.includes("['" + kind + "',VISO_ASSET_FILES."), 'editable project must package asset kind: ' + kind);
}
assert(html.includes("function downloadVISOProject(project,fileName)"), 'editable project/template package writer must exist');
assert(html.includes("project.assets=manifest"), 'editable project must include its embedded asset manifest');
assert(html.includes("if(overlayAssetCount!==objects.length)"), 'every editable overlay layer must be packaged with its source image');
assert(html.includes("assets.filter(a=>a.kind.startsWith('overlay-')).sort((a,b)=>Number(a.kind.slice(8))-Number(b.kind.slice(8)))"), 'project import must restore overlay images in numeric layer order even if manifest records are shuffled');
assert(html.includes("restoreVISOProjectAssets(project,entries)"), 'project import must restore embedded media assets');
assert(html.includes("downloadVISOProject(project,'VISO-'+key+'.viso')"), 'built-in templates must download as complete editable project packages');
assert(html.includes("renderVISOStage('overlay'"), 'overlay render stage must remain in the preview frame');
const frameStart = html.indexOf('function frame(t){');
const frameEnd = html.indexOf('requestAnimationFrame(frame)', frameStart);
const frameSource = html.slice(frameStart, frameEnd);
const stageOrder = [...frameSource.matchAll(/renderVISOStage\('(background|media|scene|overlay|post)'/g)].map(match => match[1]);
assert.deepEqual(stageOrder, ['background', 'media', 'scene', 'overlay', 'post'], 'preview render stages must execute in deterministic compositing order');
assert(html.includes('const PREVIEW_MAX_W=360,PREVIEW_MAX_H=640'), 'live preview must use a bounded mobile render resolution');
assert(html.includes('function setPreviewCanvasSize()'), 'preview resolution must be independently managed');
assert(html.includes("maxW=heavy?540:720,maxH=heavy?960:1280"), 'video export must render at up to 720p on mobile');
assert(html.includes("if(rec&&rec.state==='recording')drawRecordFrame()"), 'video export canvas must be refreshed on every animation frame');
assert(html.includes("if(streamDest)streamDest.stream.getAudioTracks().forEach(t=>st.addTrack(t))"), 'video export must include available audio destination tracks');
assert(html.includes('function setExportCanvasSize(){setCanvasSize(targetW,targetH,Math.min(1080,targetW),Math.min(1920,targetH));}'), 'still exports must use the selected full output resolution');
assert(html.includes('BIT=Math.round(targetW*targetH*FPS*0.13*(quality/100))'), 'export bitrate must use the selected output resolution');
assert(html.includes("setPreviewCanvasSize();if(b)dl(b,exportFileName('png'))"), 'PNG export must restore the mobile preview resolution after capture');
assert(html.includes('function initRecordGL()'), 'video export must initialize the GPU recording scaler when WebGL is available');
assert(html.includes('function drawRecordFrame()'), 'video export must composite each preview frame to the selected output canvas');
assert(html.includes('record2d.drawImage(c,0,0,recordCanvas.width,recordCanvas.height)'), '2D video export fallback must scale the source frame to the bounded recording canvas');
assert(html.includes('setVideoRenderCanvasSize();drawRecordFrame();'), 'video export must preserve the mobile recording canvas dimensions');
assert(!html.includes('setVideoRenderCanvasSize();recordCanvas.width=targetW;recordCanvas.height=targetH;'), 'video export must not overwrite the mobile recording canvas cap with full output dimensions');
assert(html.includes('let exportFailed=false,exportFinalized=false'), 'video export must track failure and finalization state');
assert(html.includes('if(exportFinalized)return;exportFinalized=true;'), 'video export cleanup must run only once');
assert(html.includes("else finishRecorder('Export failed: recorder returned no video data')"), 'empty video recordings must not be reported as successful');
assert(html.includes("exportFailed=true;"), 'video recorder errors must mark the export failed');
assert(html.includes("setPreviewCanvasSize();"), 'video recorder completion and failure must restore preview resolution');
assert(html.includes('recordCanvas.captureStream(FPS)'), 'video export must capture the full-resolution output canvas');
assert(html.includes("if(rec&&rec.state==='recording')drawRecordFrame()"), 'recording canvas must receive each completed render frame');
assert(html.includes('streamDest.stream.getAudioTracks().forEach(t=>st.addTrack(t))'), 'video export must include the audio stream');
assert(html.includes('function setVideoRenderCanvasSize()'), 'video render resolution must be tuned for mobile performance');
assert(html.includes("render:(ctx,b)=>drawStudioText(ctx,b)"), 'text studio layers must render through the shared export canvas pipeline');
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
assert.equal(registry.modules['Beat · BPM Sync'].stage, 'overlay');
assert.equal(registry.modules['Beat · BPM Sync'].order, 75);
assert.equal(registry.modules['Text · Logo Studio'].stage, 'overlay');
assert.equal(registry.modules['Text · Logo Studio'].order, 80);
assert.equal(typeof registry.modules['Text · Logo Studio'].render, 'function');
assert.equal(registry.modules['Surface Reflection'].stage, 'post');
const dispatched = [];
const rangeControl = {
  id: 'rangeA', type: 'range', value: '22', defaultValue: '12',
  dispatchEvent: event => dispatched.push(event.type)
};
const checkControl = {
  id: 'checkA', type: 'checkbox', checked: true, defaultChecked: false,
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
rangeControl.value = '77';
checkControl.checked = true;
registry.restoreVISORegistryState({ 'test-tool': {} });
assert.equal(rangeControl.value, '12', 'missing tool values migrate to element defaults');
assert.equal(checkControl.checked, false, 'missing checkbox values migrate to defaultChecked');
rangeControl.value = '88';
checkControl.checked = true;
registry.restoreVISORegistryState({});
assert.equal(rangeControl.value, '12', 'tools added after an older project restore to their defaults');
assert.equal(checkControl.checked, false, 'new checkbox controls restore their defaultChecked state');
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


const autoStart = html.indexOf('function restoreVISOAutoState(');
const autoEnd = html.indexOf('function snap()', autoStart);
assert(autoStart >= 0 && autoEnd > autoStart, 'auto state restore helper must exist');
const autoControls = Object.fromEntries(['bAuto','sAuto','kCol','kVis','kShp','kSwy'].map(id => [id, { value: '', classList: { values: new Set(), toggle(name, on) { if (on) this.values.add(name); else this.values.delete(name); } } }]));
const autoSandbox = { autoOn:false, autoEvery:3, lockChip:{color:false,vis:false,shape:false,sway:false}, $: id => autoControls[id] };
vm.runInNewContext(html.slice(autoStart, autoEnd), autoSandbox, { timeout: 2000 });
autoSandbox.restoreVISOAutoState({ autoOn:true, autoEvery:7, lockChip:{color:true,vis:false,shape:true,sway:false} });
assert.equal(autoSandbox.autoOn, true, 'auto mode restores as enabled');
assert.equal(autoSandbox.autoEvery, 7, 'auto interval restores');
assert.deepEqual({ ...autoSandbox.lockChip }, { color:true, vis:false, shape:true, sway:false }, 'individual auto locks restore');
assert.equal(autoControls.sAuto.value, 7, 'auto interval slider synchronizes');
assert(autoControls.kCol.classList.values.has('on') && !autoControls.kCol.classList.values.has('lock'), 'locked control UI synchronizes');
autoSandbox.restoreVISOAutoState({});
assert.equal(autoSandbox.autoOn, false, 'older projects default auto mode off');
assert.equal(autoSandbox.autoEvery, 3, 'older projects default auto interval');
assert.deepEqual({ ...autoSandbox.lockChip }, { color:false, vis:false, shape:false, sway:false }, 'older projects default all auto locks off');


const exportStart = html.indexOf('function restoreVISOExportState(');
const exportEnd = html.indexOf('function captureProjectState', exportStart);
assert(exportStart >= 0 && exportEnd > exportStart, 'export state restore helper must exist');
const makeButton = text => ({ textContent:text, classList:{ values:new Set(), toggle(name,on){ if(on)this.values.add(name); else this.values.delete(name); } } });
const exportControls = {
  bLoop:makeButton('Loop song: ON'), bStopAtEnd:makeButton('Stop export at song end: OFF'), sQuality:{value:100}
};
const exportLists = {
  '#aspect button':['9:16 TikTok','16:9 YouTube','1:1','4:5'].map(makeButton),
  '#res button':['1080','1440','4K'].map(makeButton),
  '#fps button':['30 fps','60 fps'].map(makeButton)
};
const exportSandbox = {
  aspect:'9:16',baseRes:1080,FPS:30,quality:100,formatChoice:'auto',loopSong:true,stopAtSongEnd:false,
  audio:{loop:true},fmtButton:makeButton('Auto format'),$:(id)=>exportControls[id],
  document:{querySelectorAll:selector=>exportLists[selector]||[]},setRes(){}
};
vm.runInNewContext(html.slice(exportStart,exportEnd),exportSandbox,{timeout:2000});
exportSandbox.restoreVISOExportState({aspect:'16:9',baseRes:2160,FPS:60,quality:130,formatChoice:'webm',loopSong:false,stopAtSongEnd:true});
assert.equal(exportSandbox.aspect,'16:9');assert.equal(exportSandbox.baseRes,2160);assert.equal(exportSandbox.FPS,60);
assert.equal(exportSandbox.quality,130);assert.equal(exportSandbox.formatChoice,'webm');
assert.equal(exportSandbox.loopSong,false);assert.equal(exportSandbox.stopAtSongEnd,true);assert.equal(exportSandbox.audio.loop,false);
assert(exportLists['#aspect button'][1].classList.values.has('on'),'aspect button sync');
assert(exportLists['#res button'][2].classList.values.has('on'),'resolution button sync');
assert(exportLists['#fps button'][1].classList.values.has('on'),'FPS button sync');
assert.equal(exportControls.sQuality.value,130);assert.equal(exportSandbox.fmtButton.textContent,'WebM');
exportSandbox.restoreVISOExportState({});
assert.equal(exportSandbox.aspect,'9:16');assert.equal(exportSandbox.baseRes,1080);assert.equal(exportSandbox.FPS,30);
assert.equal(exportSandbox.quality,100);assert.equal(exportSandbox.formatChoice,'auto');
assert.equal(exportSandbox.loopSong,true);assert.equal(exportSandbox.stopAtSongEnd,false);assert.equal(exportSandbox.audio.loop,true);

console.log('VISO tests passed: archive integrity, project manifests, and registry serialize/restore hooks');

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
const paintStart=html.indexOf('function paintLayer('),paintEnd=html.indexOf('\nfunction ',paintStart+20);
const paintSource=html.slice(paintStart,paintEnd);
assert(!paintSource.includes('drawSpectrum('), 'spectrum must not be redundantly rendered inside reusable background/media paint layers');
const singlePassFrameStart=html.indexOf('function frame(t)'),singlePassFrameEnd=html.indexOf('\n// VISO advanced studio modules',singlePassFrameStart);
const singlePassFrameSource=html.slice(singlePassFrameStart,singlePassFrameEnd);
assert.equal((singlePassFrameSource.match(/drawSpectrum\(x,1\)/g)||[]).length,1,'spectrum must render exactly once in the unified frame pipeline');
assert.equal((singlePassFrameSource.match(/getByteFrequencyData\(buf\)/g)||[]).length,1,'beat and spectrum analysis must share one frequency read per animation frame');
const bandStart=html.indexOf('function bands(pw)'),bandEnd=html.indexOf('\nfunction ',bandStart+20);
const bandSource=html.slice(bandStart,bandEnd);
const specBandStart=html.indexOf('function specBands()'),specBandEnd=html.indexOf('\nconst SPEC_MODES',specBandStart);
const specBandSource=html.slice(specBandStart,specBandEnd);
assert(!bandSource.includes('getByteFrequencyData(')&&!specBandSource.includes('getByteFrequencyData('),'audio analysers must consume the shared frame snapshot instead of reading it again');
assert(html.includes('PARTICLE_COUNT=Math.min(1100,Math.max(600,Math.round((window.innerWidth||360)*2)))'), 'particle workload must scale to a mobile-safe budget');
assert(html.includes('if(mstate[0]?.on)stepP(b,dt);'), 'particle simulation must stop when the Dust visualizer is disabled');

assert(html.includes("dockNav.id='toolDockNav'"), 'editor must expose a horizontal tool dock');
assert(html.includes('function activateDockPanel(panel)'), 'tool dock must activate one focused control panel at a time');
assert(html.includes('VISO bright premium workspace refresh')&&html.includes('background:linear-gradient(145deg,#f8fbff'), 'editor must use the requested bright premium workspace theme');
assert(html.includes("const aspectItems=['9:16','16:9','1:1','4:5','3:4','4:3','3:2','2:3','21:9']"), 'canvas must support common social and custom crop ratios');
assert(html.includes("if(aspect==='custom')"), 'canvas must support arbitrary custom pixel dimensions');
assert(html.includes('id="customWidth"')&&html.includes('id="customHeight"')&&html.includes("aspect='custom'"), 'custom canvas width and height must apply immediately');
assert(html.includes('id="previewToggle"')&&html.includes('id="previewExit"')&&html.includes('preview-fullscreen'), 'editor must provide an immersive full-screen canvas mode');
assert(html.includes("className='controlGlyph'"), 'individual tool controls must receive descriptive icons');
assert(html.includes("document.querySelectorAll('#bar>.panel label')"), 'slider and input labels must also receive control icons');
assert(html.includes("||[null,'◈']"), 'unmatched control buttons must still receive a consistent fallback icon');
assert(html.includes('0 3px 0 #c3d3e7'), 'tool dock icons must have layered dimensional button styling');

assert(html.includes('data-template-filter="landscape"')&&html.includes('data-template-filter="portrait"'), 'template library must separate landscape and portrait layouts');
assert(!html.includes('data-template-filter="liked"')&&!html.includes('data-like='), 'template favourites are intentionally omitted from the clean library');
assert(html.includes('data-template-filter="downloaded"'), 'template library must retain downloaded collection');
assert(html.includes("VISO_TEMPLATE_DOWNLOADS_KEY='viso.templateDownloads.v1'"), 'downloaded templates must persist locally');
assert(html.includes("visoTemplateDownloads.add(key)"), 'successful editable template downloads must populate the downloaded collection');
assert(html.includes("aspect=layout==='portrait'?'9:16':'16:9'"), 'selecting a template must apply its portrait or landscape canvas orientation');
assert(html.indexOf('snap();',html.indexOf("#visoTemplates [data-template]"))<html.indexOf("aspect=layout==='portrait'?'9:16':'16:9'"), 'template selection must capture undo state before changing canvas orientation');
assert(html.includes('templateGrid.portraitGrid{grid-template-columns:repeat(3,minmax(0,1fr))}'), 'portrait templates must use a compact three-column gallery');
for (const key of ['aura','romance','vhs','club','minimal','phonk','neon','cosmic','city','fire','snow','pride','rose','storm']) assert(html.includes('data-template="'+key+'"'), 'template card must exist: '+key);

assert(html.includes('#bar>.panel.dock-active{display:flex!important}'), 'only the selected tool panel should occupy the editor dock');
assert(html.includes('canvas{aspect-ratio:auto!important;width:auto!important;height:auto!important;'), 'preview must preserve selected landscape, square or portrait aspect ratio');
assert(html.includes('VISO clean mobile editor chrome: top actions, lower tool dock'), 'editor must use clean mobile editor chrome');
assert(!html.includes('id="toolSearch"')&&!html.includes('id="toolFavorites"')&&!html.includes('id="toolFilters"'), 'unneeded top search and favorites controls must be removed');
assert(html.includes('id="topExport"')&&html.includes('id="bUndo"')&&html.includes('id="bRedo"'), 'top toolbar must include undo redo and export');
assert(html.includes("$('bar').appendChild(dockNav)"), 'tool navigation must appear after control panels at the bottom');
assert(html.includes("if(panel.id==='exportPanel')return;"), 'export panel must be opened from the top-right action instead of duplicated in bottom dock');
assert(html.includes("const dockOrder=['Media','Logo','Audio','Text','Visualizer','Elements','Overlay FX','Sticker','Turning Point','Tempo','Templates · Editable Projects','Background']"), 'tool dock must follow the separated Aspectrum tool order and keep Background last');
assert(html.includes("mergeGroup('Audio',['Audio Motion','Audio Frequency Response'],'audioPanel')"), 'audio settings must be consolidated into one Audio tool');
assert(html.includes("mergeGroup('Visualizer',['Look','Visuals','Spectrum Studio'"), 'visualizer settings must be consolidated into one Visualizer tool');
assert(html.includes("mergeGroup('Overlay FX',['Overlay','Overlay Effects · Motion FX'"), 'overlay effects must be consolidated into one Overlay FX tool');
assert(html.includes("logoPanel.id='logoPanel'")&&html.includes("stickerPanel.id='stickerPanel'"), 'Logo and Sticker must have independent tools');
assert(html.includes("tempo.id='tempoPanel'")&&html.includes("elements.id='elementsPanel'"), 'Tempo and Elements must have independent tools');
assert(html.includes("turningPanel.id='turningPointPanel'")&&html.includes('id="bAddTurningPoint"')&&html.includes('id="bRemoveTurningPoint"'), 'Turning Point must provide add and remove controls');
assert((html.match(/<option value="(?:fade|crossfade|flash|glitch|shake|rgb|blur|zoom|spin|wipe-left|wipe-right|wipe-up|wipe-down|circle|diamond|pixel|scanline|invert|light-leak|vhs)">/g)||[]).length>=20, 'Turning Point must provide twenty transition types');
assert(html.includes('function drawTurningPointTransitions(ctx)')&&html.includes('drawTurningPointTransitions(x)'), 'turning point transitions must render in the shared preview and export frame');
assert(html.includes('turningPoints:turningPoints.map')&&html.includes('turningPoints=Array.isArray(d.turningPoints)'), 'turning points must save and restore with the project');
assert(html.includes('id="bMetronomeSound"')&&html.includes('function playMetronomeTick'), 'Tempo must provide audible metronome control');
assert(html.includes('id="bTempoSyncBar"')&&html.includes("$('bTempoSyncBar').onchange"), 'Tempo must provide an independent sync-bar toggle');
assert(['bElementParticles','bElementSparkles','bElementLight','bElementReactiveFrame'].every(id=>html.includes('id="'+id+'"')), 'Elements tool must expose particles, sparkles, lights and reactive frame');
assert(['logoArtworkShape','bLogoVinyl','bLogoColorKey','bLogoInk'].every(id=>html.includes('id="'+id+'"')), 'Logo tool must expose shape, vinyl, colour key and ink controls');
assert(html.includes('function rebuildLogoColorKey()')&&html.includes('logoProcessedCtx.putImageData'), 'logo colour key must process the uploaded image rather than remain a decorative control');
assert(html.includes('logoArtworkShape,logoVinyl,logoVinylSpeed,logoColorKeyOn,logoColorKey,logoColorKeyTolerance,logoInkOn,logoInkAmount,logoInkColor'), 'logo effects must persist in project state');
assert(html.includes('id="topExport"')&&html.includes('id="bUndo"')&&html.includes('id="bRedo"'), 'undo, redo and export must be available in the top toolbar');
assert(html.includes("$('bar').appendChild(dockNav)"), 'tool dock must sit after the selected control panel at the bottom');
assert(html.includes("const dockOrder=['Media','Logo','Audio','Text','Visualizer','Elements','Overlay FX','Sticker','Turning Point','Tempo','Templates · Editable Projects','Background']"), 'background tools must be placed at the end of the bottom tool dock');
assert(html.includes("$('topExport').addEventListener('click',()=>activateDockPanel($('exportPanel')))"), 'top export action must open the export settings panel');
assert(!html.includes('<h4>Auto + Lock</h4>')&&!html.includes("$('bAuto').onclick"), 'legacy Auto + Lock panel and actions must be removed');
assert(html.includes('<h4>Audio Frequency Response</h4>\n    <div class="row"><button id="bLoop"'), 'song loop control must live inside the Audio tool');
assert(html.includes('#monitorDock>.subhead{display:none!important}'), 'song monitor must be compacted into the preview rather than consuming editor space');
assert(html.includes('#monitorDock>.subhead{display:none!important}'), 'song monitor must be compacted into the preview rather than consuming editor space');

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
assert(['specShape','specStyle','specSymmetry','specMaterial','specLayerMode','specLayerBlend','sSpecResolution'].every(id=>html.includes('id="'+id+'"')), 'Visualizer must expose shape, style, symmetry, material, layer mode, blend and resolution controls');
assert(html.includes('const SPEC_N=200;')&&html.includes('max="200" value="48"'), 'Visualizer resolution and bar count must support 1–200');
assert(html.includes("if(specMaterial==='fire')")&&html.includes("specLayerBlend==='additive'?'lighter':'source-over'"), 'Visualizer material and blend settings must affect rendering');
assert(html.includes("if(['flats','square','ncs'].includes(specShape))"), 'Flats, Square and NCS must use dedicated geometry renderers');
assert(!html.includes('return {autoOn,autoEvery,lockChip:'), 'removed Auto + Lock controls must not be saved into new projects');
assert(html.includes('function restoreVISOAutoState(_d){/* Removed legacy Auto + Lock controls are intentionally ignored when opening older projects. */}'), 'legacy Auto + Lock project data must be safely ignored');
assert(html.includes("$('bLoop').onclick=()=>{snap();loopSong=!loopSong"), 'song loop toggle must participate in undo history');
assert(html.includes('exportSettings:{aspect,baseRes,FPS,quality,formatChoice,loopSong,stopAtSongEnd,customW,customH}'), 'export profile, resolution, FPS, format and playback toggles must be saved');
assert(html.includes('customW,customH},bgVizMask,colors'), 'custom canvas dimensions and background visualizer mask toggle must be saved');
assert(html.includes('if(d.bgVizMask!==undefined)bgVizMask=!!d.bgVizMask;else bgVizMask=true'), 'background visualizer mask must restore with legacy default');
assert(html.includes("['tBvm',bgVizMask]"), 'background visualizer mask button must synchronize after restore');
assert(html.includes("restoreVISOExportState(d.exportSettings||{quality:d.tools?Number($('sQuality').value):100})"), 'legacy projects must retain their saved registry quality while new export settings default safely');
assert(html.includes('restoreVISOExportState({});};'), 'reset must restore default export settings');
assert(html.includes("bgVizMask=true;$('tBvm').classList.add('on')"), 'reset must restore background visualizer mask default');
assert(!html.includes('autoEvery=3;Object.keys(lockChip).forEach(key=>lockChip[key]=false)'), 'removed Auto + Lock UI must not be reset or re-enabled');
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
assert(html.includes('function visoFontFamily(fileName,checksum)'), 'custom font family must be stable across project reloads');
assert(html.includes('await prepareVISOFont(fontFile)'), 'embedded custom fonts must load before project settings are applied');
assert(html.includes('project.settings.state.studioTextFont=restoredAssets.fontFamily'), 'project font selection must remap to its restored font family');
assert(html.includes("toolState.sTextFont=fontValue"), 'saved font selection must also remap inside the effect registry');
assert(html.includes("if(d.studioTextFont!==undefined){studioTextFont=d.studioTextFont;$('sTextFont').value=studioTextFont;}"), 'font selector must reapply after registry controls restore');
assert(html.includes('studioTextFont=prepared.family'), 'manual custom font selection must update rendered text state');
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
assert(autoStart >= 0 && autoEnd > autoStart, 'legacy auto state migration helper must exist');
const autoSandbox = {};
vm.runInNewContext(html.slice(autoStart, autoEnd), autoSandbox, { timeout: 2000 });
assert.doesNotThrow(() => autoSandbox.restoreVISOAutoState({autoOn:true,autoEvery:99,lockChip:{color:true}}), 'legacy Auto + Lock state must be safely ignored');
assert(!html.includes('if(autoOn){autoT'), 'removed Auto + Lock feature must not run in the animation loop');


assert(html.includes('let restoringState=false;'), 'state restoration guard must exist');
assert(html.includes('function snap(){if(restoringState)return;'), 'undo snapshots must be suppressed during restore');
assert(html.includes('previousRestoringState=restoringState;restoringState=true;try{'), 'apply must guard registry change events from polluting undo');
assert(html.includes('finally{restoringState=previousRestoringState;}'), 'restore guard must always be released');
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
assert.equal(exportSandbox.customW,1080,'custom canvas width uses a safe default when older projects omit it');assert.equal(exportSandbox.customH,1920,'custom canvas height uses a safe default when older projects omit it');
exportSandbox.restoreVISOExportState({});
assert.equal(exportSandbox.aspect,'9:16');assert.equal(exportSandbox.baseRes,1080);assert.equal(exportSandbox.FPS,30);
assert.equal(exportSandbox.quality,100);assert.equal(exportSandbox.formatChoice,'auto');
assert.equal(exportSandbox.loopSong,true);assert.equal(exportSandbox.stopAtSongEnd,false);assert.equal(exportSandbox.audio.loop,true);


const snapStart=html.indexOf('function snap(){');
const snapEnd=html.indexOf('function apply(s)',snapStart);
assert(snapStart>=0&&snapEnd>snapStart,'guarded undo snapshot function must exist');
let snapshotCalls=0;
const snapSandbox={restoringState:true,captureProjectState:()=>{snapshotCalls++;return {state:1};},undo:[],redo:[],objects:[],objectSnapshotCache:{set(){},delete(){}}};
vm.runInNewContext(html.slice(snapStart,snapEnd),snapSandbox,{timeout:2000});
snapSandbox.snap();assert.equal(snapshotCalls,0,'restoration must not add undo snapshots');assert.equal(snapSandbox.undo.length,0);
snapSandbox.restoringState=false;snapSandbox.snap();assert.equal(snapshotCalls,1,'normal user changes still create undo snapshots');assert.equal(snapSandbox.undo.length,1);


const fontStart=html.indexOf('function visoFontFamily(');
const fontEnd=html.indexOf('async function prepareVISOFont',fontStart);
assert(fontStart>=0&&fontEnd>fontStart,'stable font family helper must exist');
const fontSandbox={};vm.runInNewContext(html.slice(fontStart,fontEnd),fontSandbox,{timeout:2000});
assert.equal(fontSandbox.visoFontFamily('My Custom Font.ttf',4660),'VISOFont_My_Custom_Font_1234','font family remains stable for same file and checksum');
assert.notEqual(fontSandbox.visoFontFamily('My Custom Font.ttf',4660),fontSandbox.visoFontFamily('My Custom Font.ttf',4661),'different font contents receive distinct families');

console.log('VISO tests passed: archive integrity, project manifests, and registry serialize/restore hooks');

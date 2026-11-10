# VISO Lite — Permanent Project Roadmap

> This file is the continuity record for every future VISO development chat. Read this file and inspect the current branch before making changes. Do not rely on chat history or memory as the project state.

## 1. Project identity and source of truth

- Repository: `jabir1718/Viso-Lite`
- Working branch: `viso/advanced-studio-ui`
- Android package: `com.viso.lite`
- Main web studio: `app/src/main/assets/index.html`
- Android shell: Kotlin / Jetpack Compose with the studio hosted in Android WebView.
- Current reference input: user-provided working HTML file named `Viso working Index.html` (uploaded 2026-11-10; 1,133 lines). Treat it as a reference/baseline to compare, not permission to overwrite newer repository work.
- Reference application package previously inspected: Aspectrum / MP3 Visualizer (Unity Android app, package `com.MDA.MP3Visualizer`, version 2.7.2). The supplied archive is the reference app's package/assets, not a font pack.
- Latest repository head at this checkpoint: `2d2d80728eb9c1226c59fdb5fd503544a2063031`. Latest HTML asset blob: `614a9ee7d83ca8f5bbaea9835661b7e661033230` (169,952 characters, 2,164 lines, 267 unique element IDs, no duplicate IDs). The uploaded 1,133-line baseline was compared earlier; all 129 original element IDs remain in the current branch.
- Main branch must be preserved. Develop on `viso/advanced-studio-ui` unless the owner explicitly directs otherwise.

## 2. Product goal

Build **one polished, installable VISO Android APK** that combines:

1. The reference visualizer's useful feature set, controls, and editing workflow as closely as technically and legally practical.
2. VISO's own original advanced features, modern usability, and social-media-oriented output.
3. A modular architecture that leaves room for future tools, effect packs, controls, and templates without rebuilding the app from scratch.

This is not a request to make a clone that copies branding/assets. Recreate functionality and interaction patterns in VISO's own identity.

The reference app must not be used as an excuse to reduce VISO to a basic visualizer. The aim is to make VISO more capable, polished, and useful for current social-video trends, while keeping it reliable and easy to operate on a phone.

## 3. Non-negotiable product rules

- Deliver one integrated APK, not separate apps for reference and VISO features.
- Preserve existing working features and the user's familiar layout unless a tested improvement is clearly better.
- No watermark system, watermark overlay, watermark export, or watermark remnants. User-created custom logo overlays are allowed and required.
- Keep the canvas/preview visible and controls usable on Android phones; support collapsible sections and sensible scrolling.
- Every visible control must work, affect the preview/export, and save/restore correctly. Do not add fake controls or cosmetic-only options.
- Keep controls grouped logically and make advanced sections expandable so future tools can be added without making the screen unusable.
- Support future additions through reusable effect/control modules, shared settings/state, versioned project files, and a stable export pipeline.
- Never claim a feature is complete merely because its name or UI control exists.
- Never claim APK/device testing succeeded unless the relevant build and device tests actually passed.
- Do not overwrite the known-good baseline or `main` branch.
- Work in coherent batches; avoid repeatedly stopping for tiny approvals. Ask only when a decision genuinely blocks safe progress.

## 4. Current implementation snapshot (known, not a claim of full completion)

The current working branch already contains a substantial HTML visualizer studio. The current repository asset at this checkpoint has blob SHA `c4516701bfbb404acc0cfab64197d6401159a8f3`, 159,708 characters and 2,050 lines. Always fetch the latest version and SHA before editing.

### Existing areas present in the current studio

- Media input: audio, photo, video, PNG overlays; remove/clear; play/pause; center; undo/redo/reset.
- Social export presets: TikTok, Reels, YouTube, square, portrait 4:5, Story 4K; custom aspect/resolution/FPS/quality/name; frame PNG and video export.
- Song/export monitor: song time, export time, loop option, stop-at-song-end option.
- Background: image/video/off/visualizer, split, dim, blur, visualizer mix/power.
- Auto + lock controls for colour, visuals, shape, sway.
- Look presets, auto mix, random mix.
- Visual modes include Dust, Aurora, Beams, Bars, Ripple, Grid, Sparkle, Vortex, Ribbon, Bokeh, Neon, Confetti, Comet, Hex, Orbitals, Mirage, Cube3D, Hyper, Depth, Warp3D, Orbit3D, Tunnel3D, Voxel, Parallax, Galaxy, Nebula, Prism3D, Fractal4D, Quantum5D, Holo6D, Pulse7D, Vector8D, Matrix9D, Orbit10D, Nebula11D, Omni12D.
- Colour palette editing.
- Mask shapes include Media, Circle, Ring, Blob, Frame, Text, Hexagon, Star, Diamond, Triangle, Heart.
- Lights/tools box and layer controls.
- Position, sway, shape, gradient, motion and video-grade controls.
- Text / Logo Studio: two editable text layers with separate position, size, opacity, colour, rotation, outline, glow, animation, alignment and plate; custom font selection/upload; saved preset slots.
- Professional workspace navigation: searchable tools, category filters aligned to the reference app's useful editing groups, favourite tools persisted locally.
- Versioned VISO tool registry: stable tool IDs, categories, schema version, control metadata, defaults and optional render/serialize/restore hooks.
- Advanced overlay effects: VHS scanlines/noise/jitter/colour bleed; Shockwave strength/speed/thickness/RGB split; Lens glow/focus/bokeh/blur; film grain/dust/scratches/flicker/sepia/weave; haze/smoke atmosphere; adjustable surface reflection with blur/fade/ripples/perspective.
- Progress Bar Studio: style, progress/remaining colours, thickness, roundness, glow, rotation and horizontal/vertical positioning.
- Logo Studio: glow, beat-reactive bounce/shake, rotation, reflection and particles in addition to size/position/opacity.
- Spectrum frequency focus: Full, Low, Balanced, High, Vocals and Changes, separate from visual display styles.
- Audio frequency response: bass, mids/vocals, treble.
- Custom logo-only rendering work has been made; continue to verify no watermark code/UI remains.

### Existing branch work reported in prior build record

- Background Drift UI/settings and drawing transform (including beat-sync-related controls) exist in some form; effect-specific controls are now grouped in a separate panel from overlay effects.
- Progress bar styles include Gradient, Thin, Segments, Circle, with colour, remaining colour, glow, rotation, roundness, thickness and position controls added.
- Motion FX include independently adjustable VHS, Shockwave and Lens effects; Film & Vintage, Atmosphere FX and Surface Reflection panels have also been added. CI checks require these controls/render functions.
- Templates named Aura Pulse, Soft Romance, Retro VHS, Club Energy, Minimal Wave were added as editable settings presets.
- Save/Open Project now writes a versioned `.viso` v3 ZIP/store package containing `project.json`, embedded imported audio/image/video/background/logo/font/overlay files, editable layer metadata, and a `preview.png` canvas thumbnail. The importer verifies CRC32 checksums, supports the v3 archive plus legacy v1/v2 JSON projects, and guards 180 MB save / 220 MB import size limits.
- Template downloads now use the same editable project-package pipeline, so current imported assets and editable settings travel together. Android's native save bridge routes `.viso` packages to `Downloads/Viso` rather than the image gallery.
- Undo/redo was corrected to capture state before control changes and retain image-object references for layer history; project switches clear old history.
- Latest verified passing build: workflow run `42714423022` (run #102), pinned to commit `2d2d80728eb9c1226c59fdb5fd503544a2063031`; HTML/JavaScript validation, ZIP/manifest/registry/render-stage/cache-order and background-control tests, Android SDK setup, debug APK build and artifact upload all succeeded. Artifact `VISO-Studio-advanced-debug-apk`, artifact ID `13717107759`, ZIP digest `sha256:9ac843cb0ece559d268ea093c4cc1de1f05b03ad6f76851765ad571bda2f8a79`. Extracted `app-debug.apk` size 84,461,475 bytes; SHA-256 `9c943ac1a5c1979df9dc9f2c3ea20dd0da710fe23637dd1618d50f8c0dd7b7e6`. Installation and real-device testing on the user's Infinix Android phone are not confirmed.

## 5. Reference feature audit — required work

The reference archive has been identified as an Android Unity/IL2CPP app. Metadata strings and identifiers have exposed likely feature families, but the list is **not yet a verified screen-by-screen UI specification**. Continue inspecting the actual assets, UI hierarchy, labels, controls, defaults, ranges, and behaviour. Mark a feature as verified only after evidence is found.

Feature families identified so far:

1. Logo: show/hide, colour, glow, bounce, shake, particles/smoke, reflection, size/scale, position, rotation, link/follow visualizer.
2. Background: image/video/colour, blur, brightness, contrast, hue, saturation, tint, X/Y position, motion, shake, drift, haze, smoke, ink, pattern.
3. Visualizer: shape/style/colour/sensitivity/glow/drift/shake/trail/layerspin/reflection/position/scale/rotation/follow.
4. Spectrum/wave: frequency modes (low/full/high/balanced/changes/vocals), wave gradients/rainbow, delay/reverse/mirror/trail/sensitivity/glow/beat wave.
5. Effects: VHS controls; shockwave controls; lens blur/focus/bokeh; film grain/dust/flicker/scratches/sepia/vignette/weave; vignette; saturation/brightness; haze/smoke; screen shake; surface reflection/ripples/perspective.
6. Progress bar: enable/style/colour/remaining colour/glow/rotation/roundness/size/thickness/X/Y.
7. Text/fonts: add text, multiple text layers, colour/size/shadow, custom font upload and selection.
8. Audio/beat: audio analysis, BPM, beat markers/scaling/sync, metronome indicator, beat colours/downbeat, snap-to-beat, sensitivity.
9. Projects/templates/export: project settings, save/load, save/rename/delete template, template download/import and background media, video export, resolution/FPS, fast export, audio capture.

### Known reference identifiers (use as search anchors, not as proof of user-facing UI)

- Background drift: `BackgroundDriftEnabled`, `BackgroundDriftFollowMusic`, `BackgroundDriftSpeed`, `BackgroundDriftRotation`, `BackgroundDriftTravel`, `BackgroundDriftTiming`, `BackgroundDriftTempoSync`.
- Shockwave: `OverlayShockwaveAmount`, `OverlayShockwaveColor`, `OverlayShockwaveDistance`, `OverlayShockwaveEffect`, `OverlayShockwaveEnabled`, `OverlayShockwaveFocusX`, `OverlayShockwaveFocusY`, `OverlayShockwaveFollowMusic`, `OverlayShockwaveGlow`, `OverlayShockwaveRgbSplit`, `OverlayShockwaveSensitivity`, `OverlayShockwaveSpeed`, `OverlayShockwaveStrength`, `OverlayShockwaveThickness`.
- VHS: `OverlayVhsColorBleed`, `OverlayVhsCurvature`, `OverlayVhsEnabled`, `OverlayVhsFade`, `OverlayVhsGlitch`, `OverlayVhsJitter`, `OverlayVhsNoise`, `OverlayVhsOnScreenText`, `OverlayVhsScanlines`, `OverlayVhsSoftness`, `OverlayVhsTracking`.
- Lens blur: `OverlayLensBlurAmount`, `OverlayLensBlurAngle`, `OverlayLensBlurBokeh`, `OverlayLensBlurEnabled`, `OverlayLensBlurFocusSize`, `OverlayLensBlurFocusX`, `OverlayLensBlurFocusY`, `OverlayLensBlurFollowMusic`, `OverlayLensBlurMode`.
- Film: `OverlayFilmColorLoss`, `OverlayFilmDust`, `OverlayFilmEnabled`, `OverlayFilmFlicker`, `OverlayFilmFollow`, `OverlayFilmFrame`, `OverlayFilmFrameSpeed`, `OverlayFilmGrain`, `OverlayFilmScratches`, `OverlayFilmSepia`, `OverlayFilmVignette`, `OverlayFilmWeave`.
- Vignette: `VignetteColor`, `VignetteEnabled`, `VignetteFollowMusic`, `VignettePulse`, `VignetteRoundness`, `VignetteShape`, `VignetteSize`, `VignetteSoftness`, `VignetteStrength`, `VignetteStyle`.
- Reflection: `SurfaceReflectionEnabled`, `SurfaceReflectionStrength`, `SurfaceReflectionDistance`, `SurfaceReflectionBlur`, `SurfaceReflectionFade`, `SurfaceReflectionRipples`, `SurfaceReflectionPerspective`, `SurfaceReflectionFullWave`, `SurfaceReflectionBassRipples`.
- Progress bar: `ProgressBarEnabled`, `ProgressBarStyle`, `ProgressBarColor`, `ProgressBarGlow`, `ProgressBarRotation`, `ProgressBarRoundness`, `ProgressBarSize`, `ProgressBarThickness`, `ProgressBarX`, `ProgressBarY`, `ProgressBarRemainingColor`.

## 6. Architecture target — leave room for future tools

As the studio grows, move toward a modular tool registry rather than a single giant block of hard-coded controls:

- **Tool registry:** each tool/effect declares stable ID, display name, category, version, defaults, supported controls, render/update hook, and serialization.
- **Shared parameter schema:** each control defines type, min/max/step/default, label, grouping, and whether it is animatable or beat-reactive.
- **Effect pipeline:** predictable ordered stages for background, media, mask, visualizer, logo/text, overlays, finishing grade, and export.
- **Project format:** versioned `.viso` document; migrations for older projects; stable IDs; references to imported assets; graceful handling of missing assets.
- **Template package:** template metadata + editable settings + thumbnail + any required background/media assets. Download/import must open a fully editable project; not just show a preview or apply a few settings.
- **Asset manager:** imported songs, images, videos, fonts, overlays and template resources, with clear missing-file handling.
- **Export backend:** isolate encoder/format capabilities; show actual supported formats and limitations; preserve audio/video sync; avoid claiming 4K/60 FPS if the device/WebView encoder cannot deliver it.
- **UI layout:** collapsible tool groups, search/filter for tools, favourites/recent tools, and a future-proof tools drawer. Keep phone-first interaction.
- **Compatibility:** every new feature must degrade safely on weaker devices and avoid blocking the preview/UI thread.

Do not attempt a risky full rewrite just to reach this architecture. Refactor incrementally while preserving a working APK at each milestone.

## 7. Delivery and quality gates

A feature is complete only when all applicable checks pass:

1. UI control exists and is understandable.
2. Control changes the actual rendered output or documented project behaviour.
3. Settings are included in undo/redo where appropriate.
4. Save/load and template/project serialization preserve the setting.
5. Export output matches the preview as closely as the platform allows.
6. No JavaScript console/runtime errors or broken controls.
7. Layout works on a narrow Android screen, including scrolling and expanded/collapsed panels.
8. Build workflow succeeds and APK artifact is produced.
9. Device testing is recorded separately from CI success.

For every milestone, update the status table below and record commit SHA, build run ID, artifact name, known issues, and device test result.

## 8. Roadmap status

| Workstream | Status | Next action |
|---|---|---|
| Preserve known-good VISO baseline | Verified | All 129 original IDs remain; latest branch asset has 255 unique IDs and no duplicates (2,050 lines) |
| Reference app reverse audit | In progress | Metadata/localized strings indicate navigation groups for Media, Visualizer, Audio, Elements, Background, Logo, Text, Overlay Effects, Templates and Export; continue verifying exact ranges/defaults/behaviour |
| Reference feature parity | Not complete | Implement and verify each audited feature family |
| Existing VISO feature regression | Not fully verified | Test all current media, visualizer, text/logo, layer, undo/redo, save/load and export workflows |
| Logo-only / no-watermark requirement | Enforced in current HTML/CI | Current HTML contains no watermark references; CI fails if watermark text/code returns |
| Background Drift and beat sync | Partial | Verify actual beat response, settings persistence, and controls |
| Progress bar styles and positioning | Expanded in source | Four styles plus editable colour, remaining colour, thickness, roundness, glow, rotation and position; export/device QA still required |
| VHS / Shockwave / Lens effects | Expanded in source | Individual parameters and rendering added; Film, Atmosphere and Surface Reflection panels added; export/device QA still required |
| Complete editable template packages | Implemented in source; QA pending | `.viso` v3 ZIP package with settings, imported assets, layers, preview thumbnail and CRC checks; verify full round-trip on device |
| Modular future-tools architecture | Partial implementation | Searchable/favourite tool registry with stable IDs, categories, schema version, defaults and lifecycle hooks; refactor rendering pipeline incrementally |
| Single integrated Android APK | Build verified | One debug APK produced successfully (`app-debug.apk`, 84,461,475 bytes); install and device runtime still need owner-side QA |
| CI build | Latest run passed | Run `42714423022` / commit `2d2d80728eb9c1226c59fdb5fd503544a2063031`; exact-commit checkout, HTML/JS checks, ZIP/manifest/registry/render-stage/cache-order/background-control tests, Android build and artifact upload all passed |
| Real-device QA | Not confirmed | Install and test on user's Android device before final release |

## 9. Required continuity procedure for every new chat

Before changing code:

1. Open this `PROJECT_ROADMAP.md` on `viso/advanced-studio-ui`.
2. Fetch the latest branch head and latest `app/src/main/assets/index.html`; never rely on old SHAs.
3. Inspect recent commits and latest GitHub Actions build/artifact status.
4. Check this roadmap's status table and update it if repository evidence shows a newer state.
5. Read the relevant source sections before editing.
6. Make a small, coherent commit on the working branch.
7. Run validation/build; record exact results here.
8. Update this file in the same milestone or immediately after it, including what changed, what remains, and the new commit/build identifiers.

If a future chat starts with no context, the first instruction is: **“Read PROJECT_ROADMAP.md in jabir1718/Viso-Lite on branch viso/advanced-studio-ui, verify the current branch head and build, then continue from the first incomplete item. Do not ask me to repeat the project brief.”**

## 10. Owner's product direction

The owner wants a modern, trend-aware visualizer that can create distinctive social-media content, not an outdated/basic visualizer. Add original advanced capabilities where they improve real results, but keep the workflow practical on a phone. The owner expects the assistant to handle technical problems, maintain the project record, preserve working code, and deliver one reliable APK with room for future expansion.


## 11. Latest verified checkpoint — 2026-11-10

- Branch head: `5f1e3e21f8ff4afa0987b126401cb39da40d5fbf`.
- Latest source asset: `app/src/main/assets/index.html`, blob `daa9aff78bcb1b8da15882fe0d9e39eb79bfecb5`, 159,784 characters / 2,050 lines / 255 unique IDs / no duplicate IDs.
- Latest CI run: `42709656380` (run #56), conclusion `success`; HTML/JS validation, Android SDK setup, debug APK build and artifact upload all passed.
- Artifact ID: `13715516468`; artifact name `VISO-Studio-advanced-debug-apk`; archive digest `sha256:accc5cd9f306d8220093aecb1ed6e7797c386c87b62d940401c603e2e752669`.
- Extracted APK: `app-debug.apk`, 84,458,291 bytes; SHA-256 `eacdf3e1db497b8a27ace8a4f48b22f7baf6c5d6aef339c4db9e36fc6005375f`.
- Recent source commits include project archive validation/CRC and size guards, modular tool workspace and effects work, restoration of overlay image URLs, and safe audio-context shutdown. Current HTML contains the v3 project-package path, two text layers, Film/Haze/Smoke and Spectrum features; CI success verifies buildability, not complete runtime correctness.
- Device QA remains unconfirmed. Next priority: test project/template round-trip and imported media restoration, then regressions across audio, preview, undo/redo and export; continue the reference UI audit and record verified ranges/defaults rather than relying on metadata strings alone.


## 12. Reliability and build checkpoint — 2026-11-10

- Source checkpoint: branch head `e6d007ba00caa149b233a8c534d9b01321188039`; HTML blob `5f0306f792733ae0b8cd3351279da85ee523932c`.
- Fixed a serious animation-loop lifecycle issue: tool registry/search/favourite setup had been running inside `frame(t)`. It now initializes once before the frame function. The render loop explicitly schedules its next frame at the end, with one initial requestAnimationFrame call at script end.
- Hardened `.viso` v3 ZIP import: reject unsafe paths, duplicate entries, local/central header mismatches, bad CRCs, missing manifest assets, and declared-size mismatches before applying project state.
- Fixed Kotlin video export filename interpolation; fixed cancellation state so a second export cannot start before the prior encoder has stopped.
- CI workflow now checks out `${{ github.sha }}` so each build is pinned to its triggering commit. Run `42710588670` (run #63) passed all validation and Android build/upload steps.
- Verified artifact: ID `13716235748`; archive digest `sha256:e3155450f2197013b629857c9bff0446286c3392f3d29483e3feaa3231cf27b9`; extracted APK 84,458,859 bytes; SHA-256 `6de88152b64b63251d060a2f49aa0724816b026c8ec31c1dbc810c5c5b810888`.
- Current HTML: 161,596 characters, 2,067 lines, 255 unique IDs, no duplicate IDs, no watermark references. Tool registry is before `frame(t)`; two `requestAnimationFrame(frame)` calls are expected (one to start, one inside the frame loop).
- Device testing remains unconfirmed. Next: real phone QA for startup/performance, imported audio/image/video, save/open `.viso` with multiple media assets, template round-trip, undo/redo and export. Continue the reference app's screen-by-screen control/range audit and implement remaining verified parity items.


## 13. Project-package QA checkpoint — 2026-11-10

- Latest source: `5ec3beb56a8cff33ebff41c3e4c6ef0fa22bc5e1`; HTML blob `45a9666c37fd8e254ec6b1305e9f3bd7486b37f2` (163,085 characters, 2,078 lines, 255 unique IDs, no duplicates).
- Project import validates manifest structure, supported asset kinds, unique asset types, exact file sizes, 180 MB asset ceiling, overlay index sequence, and layer counts. Overlay images are decoded before the current editor is cleared; only then are embedded assets restored and project settings applied.
- Project save refuses to create a package if any overlay layer lacks its source image file.
- Added `scripts/test-project-archive.mjs` and a CI step. Tests cover ZIP/CRC32 round-trip, corrupted data, unsafe paths, duplicate entries, local/central header mismatch, missing/incorrect-size assets, unsupported/duplicate asset kinds, overlay ordering/count, and invalid paths. Latest CI test step passed.
- Latest passing build: run `42711674533` (run #76), exact commit `5ec3beb56a8cff33ebff41c3e4c6ef0fa22bc5e1`. Artifact ID `13715449375`; archive digest `sha256:23a1080cd8252bb71c418b9915748bc737145c2c57f0ef03755cf6cb7d4e7918`; extracted APK 84,459,363 bytes; SHA-256 `fbe25d2f7391817f729a9e9741cd48b7f1cded110abbcaf279aa36809d32c61d`.
- Device QA remains unconfirmed. Next priority: install and exercise on the user's phone; test media load, project save/open with audio/background/logo/overlays, template download/import, undo/redo and video export. Continue reference-app screen/control parity audit.


## 14. Registry serialization checkpoint — 2026-11-10

- Source commit `32daed2c4375a74b88ea70e3bf0e5d81a8c7f1e5`; HTML blob `571bd915f6627fcbd860a8414d99e17a7ba90928` (164,255 characters, 2,097 lines, 255 unique IDs, no duplicates).
- Tool Registry now supplies default per-panel `serialize` and `restore` hooks for non-file inputs/selects, dispatches input/change events on restore, and exposes `collectVISORegistryState` / `restoreVISORegistryState`.
- `captureProjectState` includes registry state; `apply` restores registry values after core and advanced state, so undo/redo and `.viso` project state also preserve future registered tool controls.
- CI archive test script now verifies default registry range/checkbox serialization and restoration event dispatch, in addition to ZIP/CRC and project manifest cases. Latest test step passed.
- Latest build: run `42712104606` (run #79), artifact ID `13716716023`, archive digest `sha256:ab459cb9093eb98df7618c583dac4e84e3caf06332dd65c0dd279374bdf4883b`; APK 84,459,747 bytes; SHA-256 `f577cdea54b72f1aebcbb9d90ed2efde17661ebe8f0e05f5dabb7a2737acbc50`.
- Device QA remains unconfirmed. Next architecture step: move selected effect rendering into a shared ordered pipeline without disturbing the current preview; continue reference-app parity audit and phone QA.


## 15. Ordered effect-render pipeline checkpoint — 2026-11-10

- Latest source commit `e2baa6cedbc588a2158317261ecbcb28515c115b`; HTML blob `21f713640f0025ea0379e82043eec586a0f83ec4` (165,088 characters, 2,109 lines, 255 unique IDs, no duplicates).
- Added `VISO_RENDER_MODULES` and `renderVISOStage(stage, ctx, bands, dt)`. Current rendering is routed through ordered stages: Background (image/video, drift, split and background visualizer), Media (media/visualizer compositing and `mediaFront` ordering), Scene (media layers and lights), Overlay (vignette/progress/custom logo, Motion FX, Film, Atmosphere and Text/Logo Studio), then post-composite Surface Reflection. Existing draw order is preserved.
- Tool registry supports per-module render stage/order plus default serialize/restore hooks. New effects can register a render function and stage/order without editing the main frame loop.
- CI tests validate default serialize/restore behaviour, registered stage assignments, deterministic render ordering, and a cached per-stage render list that is re-sorted only when a tool is registered; archive/manifest tests and JS validation also pass.
- Latest passing build: run `42714423022` (run #102), exact commit `2d2d80728eb9c1226c59fdb5fd503544a2063031`; artifact ID `13717107759`, archive digest `sha256:9ac843cb0ece559d268ea093c4cc1de1f05b03ad6f76851765ad571bda2f8a79`; APK 84,461,475 bytes; SHA-256 `9c943ac1a5c1979df9dc9f2c3ea20dd0da710fe23637dd1618d50f8c0dd7b7e6`.
- Device QA remains unconfirmed. Next: add remaining effect modules through registry where safe, review effect order visually on device, continue reference app control/range parity audit, and verify `.viso` media/template round-trip.


## 16. Background and scene render stages — 2026-11-10

- Extracted `drawVISOBackground(ctx,b,dt)` from the animation frame. It preserves image/video cover, background drift/beat sync, split clip, dimming and background visualizer. The frame now calls the registered `background` stage at the original draw position.
- Moved `drawObjects` and `drawLights` into the registered `scene` stage, preserving their original order before bloom.
- Current ordered pipeline: `background` → `scene` → `overlay` → `post`. Overlay stage includes vignette/progress/custom logo, Motion FX, Film, Atmosphere and Text/Logo Studio; post stage contains Surface Reflection.
- Added CI assertions for the Background and Layers/Tools Box stage assignments. Latest test, HTML validation, Android build and artifact upload passed in run `42713268370`.
- Latest artifact: ID `13716357839`, archive digest `sha256:75e909b3567fc81327c1fb35d3fc34957c0b96b3bab039279353e59cc0414df6`; APK 84,460,235 bytes; SHA-256 `9c11c2202ae91b9fcef3c3351001a2c7d1871322595332b989f6dd6866f41eb2`.
- Device visual QA remains pending. Next: validate the render-stage order on an actual phone with media, overlays, lights and reflection enabled; continue modularizing only where current draw order can be preserved.


## 17. Cached render pipeline and media stage — 2026-11-10

- Added the `media` render stage and moved the media/visualizer compositing block into `drawVISOMedia(ctx,b,dt)`, preserving `mediaFront` ordering. Current frame pipeline order is `background` → `media` → `scene` → `overlay` → `post`.
- The render cache now stores each stage's ordered tool list and updates it only when a module registers. The frame loop no longer creates/filter/sorts tool arrays on every frame, reducing per-frame allocation work on mobile.
- CI tests validate media/background/scene/overlay/post assignments, render order, and cache reordering after dynamic tool registration. Latest test and Android build passed.
- Latest verified APK: run `42713814662` (run #97), artifact ID `13717211608`; ZIP digest `sha256:44bdf727cddf1a9c9df6cab20f08be9a3201be582da229c7ebbab617833185ae`; APK 84,460,343 bytes; SHA-256 `91855b936f1e761cf3ba722f56b92c651633c32e4458264bf968e73bc4aee72e`.
- Device QA remains pending. Next: profile actual frame time on device with several stages enabled, test mediaFront + background video + lights + overlays together, and continue the reference-app feature/range parity audit.


## 18. Background grade and pattern controls — 2026-11-10

- Added background base color and tint color/amount, brightness, contrast, saturation, hue, X/Y offset, and editable Grid/Dots/Diagonal pattern overlays.
- Background media now uses combined Canvas filters for brightness/contrast/saturation/hue/blur; position offsets apply before optional music-synced drift. Tint and pattern render within the split clip and preserve existing background visualizer/dim order.
- New controls are included in Tool Registry serialization, undo/redo and `.viso` project settings. Default restore fills newly added controls from their HTML defaults when opening older registry snapshots; projects without registry state reset the new grade controls to safe defaults.
- CI asserts the background control IDs; HTML/JS, ZIP, manifest, registry, render-stage/cache tests passed. Android build #102 succeeded.
- Latest APK: run `42714423022` (run #102), artifact ID `13717107759`, archive digest `sha256:9ac843cb0ece559d268ea093c4cc1de1f05b03ad6f76851765ad571bda2f8a79`; APK 84,461,475 bytes; SHA-256 `9c943ac1a5c1979df9dc9f2c3ea20dd0da710fe23637dd1618d50f8c0dd7b7e6`.
- Device visual QA remains pending, especially color-filter support, X/Y crop behaviour, pattern readability, and project save/open migration. Continue parity audit for remaining logo, spectrum, beat and visualizer controls.


## 19. Beat analysis and metronome controls — 2026-11-10

- Latest branch head: `cb83c8b420717f9614db507e1833f2fe940186df`; HTML asset blob: `1e9ca18a7be0f1d4496c2d15f38485ca7d0059a6` (173,135 characters, 2,212 lines, 273 unique element IDs, no duplicate IDs).
- Added beat-analysis controls for beat sensitivity and scale, beat markers, metronome indicator, downbeat option and beat-reactive background drift; BPM information is surfaced in the control panel. These are in addition to the existing bass/mids/treble response and audio-reactive visualizer controls.
- CI now asserts the beat/metronome module controls and settings. Latest workflow run `42714993773` completed successfully on the exact branch head; HTML/JavaScript validation, project archive/registry tests, Android SDK setup, debug APK build and artifact upload all passed.
- Latest verified APK artifact ID `13716499790`, archive ZIP integrity passed; extracted APK size 84,462,407 bytes; SHA-256 `9d4fae02889e00ca93382d4d15280b42c032c7262b09c7cf78a8e2f68f1cf503`.
- Device QA remains pending. Next priorities: complete spectrum mode and vocal/bass separation audit, logo animation/effect parity, verify every newly added background/beat control changes both preview and export, then test `.viso` project and template media round-trip on a real Android device.


## 20. Spectrum and beat CI parity checks — 2026-11-10

- Expanded the project QA script to assert all Spectrum Studio controls (bars, radius, thickness, glow, level, attack/release, peak hold and spin), all eight visual modes (Radial, Ring, Sphere 3D, Tunnel, Mirror, Dual, Wave, LED), and all six frequency focus modes (Full, Low, Balanced, High, Vocals, Changes).
- Added separate assertions for BPM detection/tap tempo and beat sensitivity, scale, markers, metronome and downbeat controls. The existing background colour-grade/pattern assertions remain.
- Latest workflow run `42715648508` (run #107), exact commit `7f1a4b62a42aa7402b1dd51969fa2046ea2add44`, completed successfully. HTML/JS validation, project archive/manifest/registry/render-stage tests, Android SDK setup, APK build and artifact upload all passed.
- Latest artifact ID `13718016338`; ZIP integrity passed. APK size 84,462,407 bytes; SHA-256 `785e57616f4deabe7d66215db9cd90b3911f96b0dbbedbb335a93034d39fc088`.
- This confirms build/automated checks only, not visual correctness or actual playback on the user's Android phone. Next: verify Spectrum modes with real audio in preview and export, confirm frequency focus is perceptually distinct, then continue logo animation and project/template asset round-trip QA.


## 21. Project migration defaults and audio-reactive logo — 2026-11-10

- Fixed VISO tool-registry restore so a missing or older project tool-state object is normalized to an empty state, and each registered tool restores missing controls to their declared HTML defaults. This prevents newly introduced controls from retaining stale values when a saved project predates that tool's settings.
- Added CI coverage for both missing individual tool values and a completely absent tool state, including range and checkbox default restoration.
- Added `Audio-reactive size` to the Custom Logo panel. The uploaded logo now scales subtly with the live bass/pulse level, in addition to its existing glow, bounce, shake, rotation, reflection and orbiting particles. The new setting is included in advanced project state, undo/redo snapshots, restore UI synchronization and reset defaults.
- CI asserts the new logo control, saved-state wiring, restore sync and actual render expression. Latest workflow run `42716373393` (run #114), exact commit `beeaa776b8428b769d7de0d521171894e2b2d16f`, completed successfully: HTML/JavaScript validation, project archive/manifest/registry tests, Android SDK setup, debug APK build and artifact upload all passed.
- Latest source HTML blob: `5541c3cb532d9368c141b79a4e06a4f74b2021b0` (173,467 characters, 2,213 lines, 272 unique element IDs, no duplicate IDs). The original uploaded baseline's 129 IDs were previously confirmed preserved.
- Latest APK artifact ID `13718116880`; ZIP digest `sha256:61ccf6ede647c9af309aa4ed29ba563342bf0a9d2e58789624367895ea94f257`. Extracted APK size 84,462,535 bytes; SHA-256 `de24f093b9377ff9a81c4a76cdd4557e888ddfc751e720bea3e4b69681b46c2f`.
- Device QA is still pending. CI success is not a claim of installation or physical-device testing. Next: test the new logo response and spectrum/background effects with actual audio, verify export capture and `.viso` asset round-trip on Android, and continue the reference-app parity audit.


## 22. Render/export verification build — 2026-11-10

- Latest branch head: `2613ff8e360b220ca552ea5e4829b3573435e916`; app HTML blob remains `5541c3cb532d9368c141b79a4e06a4f74b2021b0` (173,467 characters, 2,213 lines). The latest commit changes CI tests only; the app source is unchanged from the previous feature build.
- CI workflow run `42716739744` (run #116) completed successfully on the exact branch head. The single build job passed; the workflow's HTML/JavaScript validation, archive/manifest/registry tests, Android debug APK build and artifact upload all completed.
- Added CI assertions that the preview frame executes render stages in deterministic order `background → media → scene → overlay → post`, video export captures the composited canvas, and Text Studio draws through the shared export canvas.
- Artifact ID `13718576660`; archive digest `sha256:4111daa4ce6853f401493827f8a7f1ed87325e94d1e89bdd13b0355fd08d8d7b`. Downloaded archive passed ZIP integrity check. Extracted APK size: 84,462,535 bytes; APK SHA-256: `2f67be5c0761b162faaa2dc5d3eec3be3b26f243a59d7c69aaa86a77ac1168ad`.
- Static source review confirms the Android WebView file picker, project/media save bridge, and MediaStore output path are present. This is code review, not on-device runtime verification.
- Physical-device QA remains unconfirmed. Outstanding: verify audio/media selection, project/template asset round-trip, audio-reactive logo and spectrum/background effects with real audio, preview/export parity, saved video/audio output, startup/performance and stability on the target Android phone. Continue the Aspectrum reference-app screen-by-screen parity audit and add CI coverage for any uncovered behaviour.


## 23. Spectrum visual-mode behaviour fix — 2026-11-10

- Code audit found that the Ring visualizer used a fixed bar length instead of live spectrum amplitude, while Sphere and Tunnel shared the same renderer as Radial and therefore were not distinct modes.
- Split Radial, Ring, Sphere and Tunnel into separate rendering branches. Ring bars now expand/contracts with live audio amplitude around a base ring; Sphere has an elliptical spherical shell with latitude guides and depth-scaled bars; Tunnel draws six perspective depth rings with audio-reactive spikes. Existing Radial, Mirror, Dual, Wave and LED branches remain.
- Added CI assertions for the dedicated Ring/Sphere/Tunnel renderers, live-amplitude expression in Ring, and multi-ring Tunnel geometry.
- The first two intermediate commits correctly failed CI syntax validation because the branch boundary was incomplete; the issue was corrected before proceeding. Final workflow run `42718700849` (run #120), exact commit `57e2d97c647062a981d096bb45710f11c4c91136`, passed HTML/JavaScript validation, archive/manifest/registry/spectrum tests, Android debug APK build and artifact upload.
- Verified artifact ID `13719043384`; archive digest `sha256:35b1b110f4429daf29443706a2136174efb0299e9ed93ceab43856dd68b6d36e`. ZIP integrity passed. Extracted APK size: 84,463,147 bytes; APK SHA-256: `6475febfab8312a1feef4dcce673560c0b33f2490f4cbe805265c33c17eab374`.
- This fixes the identified rendering logic at source/build level. Visual tuning, actual audio response and performance still require runtime testing on an Android device.


## 24. Dual spectrum geometry and APK package verification — 2026-11-10

- Further spectrum review found the Dual mode passed its horizontal bar length as rectangle height and used the narrow band width as rectangle width. Corrected both mirrored rectangles so they extend horizontally from the centre, and added a CI assertion for the corrected geometry.
- Strengthened the Android workflow with a post-build APK verification step: checks APK ZIP integrity, extracts the packaged `assets/index.html`, byte-compares it with the checked-out source HTML, and verifies the application package name is exactly `com.viso.lite` using Android `aapt`.
- Latest workflow run `42719273173` (run #124), exact commit `48294451ede9f69ff278caa68f616a64291f14b7`, passed HTML/JavaScript validation, archive/manifest/registry/spectrum tests, Android debug APK build, APK package/source verification, and artifact upload.
- Verified artifact ID `13719596657`; archive digest `sha256:6e49d021863042910fac11abc52f8160f82652f8b86bb937d11145edc570b8f0`. ZIP integrity passed. Extracted APK size: 84,463,147 bytes; APK SHA-256: `7cfb6258be5b765c10714d9fa5f420ba80b90626f09a0324ecc4683fec5cca8b`. The extracted APK contains both the audio-reactive Ring renderer and corrected Dual geometry.
- Device runtime QA remains unconfirmed; the workflow verifies source/build packaging but cannot substitute for playback, rendering and export tests on the target phone.


## 25. Mobile preview performance and verified APK — 2026-11-10

- Reduced live preview canvas to a maximum of 360×640 while preserving the selected output dimensions separately. PNG and video export switch to the selected full resolution and restore the low-cost preview resolution afterward. Export bitrate is calculated from the selected output dimensions, not the preview canvas.
- Optimized heavy spectrum modes for mobile preview: Tunnel uses three depth rings and samples at most about 32 angular segments per ring; LED grid reduced to 10 rows with reduced glow blur. Added CI assertions for the mobile preview bounds, full-resolution export path, export bitrate and spectrum workload limits.
- Latest successful workflow run #132, ID `42721348307`, exact commit `07e0900639075b611ca668abdffed56011d5b487`. HTML/JavaScript validation, project archive tests, Android debug build, APK ZIP/package/source verification and artifact upload all passed.
- Artifact ID `13720601471`, archive SHA-256 `31e7c45138c26998f6d887b1c1a14e0533af2daacd51edf51d099734bd4fd4d1`. Downloaded ZIP integrity passed. Extracted APK: 84,463,427 bytes, SHA-256 `a8f37e2d6ec9cfdf4d6253f50f236e1c968424757a3c48189548f113cdbd3fb5`. Packaged `assets/index.html` byte-matches the source HTML (SHA-256 `65cafe0e931a87ff7f7d8ed102c80f99dc8693fb1eb9973faa6b0af5f911ad00`).
- Video export source review confirms it adds the Web Audio `MediaStreamDestination` audio tracks to the captured canvas stream when available. Real playback/export still needs an Android device test.
- Remaining: full reference-app parity audit, verify actual audio response and smoothness on Android, verify PNG/video export and audio track, project/template asset round-trip, and general startup/stability. No claim of physical-device testing.


## 26. Mobile video-export canvas correction — 2026-11-10

- Source review found the video export setup correctly selected a bounded mobile recording canvas (up to 720×1280 standard / 540×960 heavy modes), but the recorder start handler immediately overwrote its dimensions with the full target resolution. This defeated the mobile cap and could allocate an unnecessarily large recording surface.
- Removed the redundant full-target resize so the selected mobile recording dimensions are retained.
- Corrected the 2D recording fallback to scale the preview frame to the actual recording canvas dimensions. Previously it drew at full target dimensions, which could crop the frame when the fallback canvas was smaller.
- Added regression assertions for both behaviours to `scripts/test-project-archive.mjs`. Android build/CI for these latest commits is pending.
- This is source-level correction and automated regression coverage; actual recording output, quality and audio playback still require runtime verification.


## 27. Reference-style editor workspace and tool dock — 2026-11-10

- Reworked the editor controls from a long vertical stack into a horizontally scrollable tool dock. Each tool opens one focused panel, leaving the live preview more room and allowing new tools to be added without extending a giant settings list.
- Removed the competing top category strip while retaining tool search and favorites; the dock itself is the primary tool navigation.
- Moved the song monitor into the preview as a compact overlay instead of a separate full-width layout block.
- Matched the reference editor's dark workspace treatment and made the preview preserve the selected canvas aspect ratio rather than forcing portrait dimensions.
- Added automated checks for the dock, compact monitor, dark theme and adaptive aspect ratio.

## 28. Unified spectrum/audio frame processing — 2026-11-10

- Removed spectrum drawing from reusable background/media paint layers and now render it once in the main frame pipeline. This prevents the same spectrum from being redrawn every time those layers are composited.
- Read the Web Audio frequency analyser once per animation frame and share that buffer with both beat-band and spectrum-band calculations instead of refreshing it repeatedly.
- Reduced the particle pool to a responsive mobile-safe budget and skip particle simulation when the Dust visualizer is disabled.
- Added regression assertions for one spectrum render, one analyser read per frame and the mobile particle budget.
- Source-level and CI validation only. Real spectrum smoothness, heat/battery behaviour, touch interaction and playback/export still require runtime testing on the target Android device.

## 29. Template library browser — 2026-11-10

- Replaced the five plain template buttons with a card-based template library.
- Added separate Landscape and Portrait views, three-column portrait cards, template search, locally saved Likes and a Downloaded collection.
- Expanded editable template presets to 14 styles. Selecting a card still applies the preset to the editor and downloads a complete editable .viso project package with its assets.
- Added automated coverage for the gallery categories, local collections, successful-download tracking and portrait layout.
- Thumbnail artwork is currently lightweight in-app artwork rather than downloaded reference-app media. The gallery interactions and editable project packaging are implemented; exact visual parity with every original Aspectrum template still needs further reference asset analysis.


## 30. Bright editor refresh and flexible canvas sizing — 2026-11-10

- Replaced the dark editor cascade with a bright blue/white/mint premium theme, including layered gradient controls and dimensional tool-dock icons.
- Added descriptive glyphs to individual control buttons and kept the horizontal tool dock as the primary lower navigation.
- Added instant common aspect presets: 9:16, 16:9, 1:1, 4:5, 3:4, 4:3, 3:2, 2:3 and 21:9.
- Added arbitrary custom canvas width/height inputs, bounded to 64–4320 pixels and normalized to even dimensions.
- Added an immersive full-screen preview mode with an exit control; preview uses the currently selected canvas dimensions.
- Added automated assertions for theme, icon treatment, custom dimensions, aspect presets and full-screen mode.
- Device-level visual and performance validation remains pending on the target Android handset.


## 31. Continuity checkpoint and grouped tool dock — 2026-11-10

**This section supersedes older “latest build” statements above for the current branch state.** Older entries are historical records of earlier milestones, not the current status.

- Current working branch: `viso/advanced-studio-ui`; latest confirmed workflow run #249, ID `42845199558`, completed with `success` on exact commit `d3552dc0ba39e2629a83359ba7160303275d08e4`.
- The immediately preceding workflow #248 (ID `42845165456`) also succeeded. #247 (ID `42844514050`) succeeded. Earlier #244–#246 failures were superseded by the later successful runs; do not treat those old failures as current.
- Latest source file fetched from the branch: `app/src/main/assets/index.html`, blob SHA `4196a4907d32714de41bd65d08827d812876fee5`, 262,405 characters / 2,872 lines. Always refetch before editing.
- Recent changes created 12 bottom-dock tool groups in this order: Media, Logo, Audio, Text, Visualizer, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video. Background Video is last. Each group opens its own panel above the dock; top Export remains separate.
- A critical visibility fix removes the legacy `collapsed` class from child panels before nesting them as `toolSubpanel`, preventing inherited hidden state. Regression tests now assert grouped controls are exposed. Run #249 passed.
- Current run #249 is a successful CI/build checkpoint only. Fetch its artifact record before claiming or sharing an APK. Physical-device installation, touch/layout, audio playback, export, heat and battery QA are not confirmed.
- Next: fetch run #249 artifact metadata and verify APK ZIP/package/source integrity; inspect the actual grouped dock source and panel visibility/activation; then continue Aspectrum parity and render/save/undo/export behaviour audits. Do not stop at UI grouping.
- The Aspectrum reference APK supplied earlier is malformed/truncated: metadata file was recovered, but the Unity data archive was incomplete. Metadata identifiers are search anchors, not proof of exact UI labels or complete feature behaviour.
- Latest known Aspectrum metadata feature identifiers include glow blur/core colour/intensity sync/mode/scale/toggle; layer spin force/momentum/speed/toggle; multiplier; point count/width; rainbow gradient; shadow colour/expand/toggle; shake; trail; UI; wave delay reverse/time/toggle/gradient; drift follow/rotation/speed/timing/toggle/travel; invert; layer blend/spin; reflection; reactive frame; spectrum mode; visualizer shape/style/material/layers and X/Y.
- Never mark a control complete until it changes rendered output or documented project behaviour and survives save/restore/undo/export checks as applicable.


## 32. Advanced Spectrum Studio response and colour controls — 2026-11-10

- Added a dedicated Advanced Glow & Response section inside Spectrum Studio (therefore inside the Visualizer tool group): Glow enable, glow blur, glow scale, glow core colour, point count, point width, audio multiplier, rainbow gradient, shadow enable, shadow colour and shadow expansion.
- These settings now affect the spectrum renderer: point count caps rendered samples; point width scales spectrum stroke thickness; multiplier scales live spectrum amplitude after the existing Spectrum Level stage (a duplicate level multiplication was identified and corrected); rainbow changes per-point hue; glow controls enable and scale bloom; core colour changes the radial glow centre; shadow controls change the actual rendered shadow colour/blur.
- Added all new settings to `collectAdvancedState()`, `restoreAdvancedState()`, and restore-time UI synchronization so older project files safely use HTML defaults and new project files restore the selected values.
- Added automated regression assertions for each control, state/render wiring, point-count/width/multiplier effects, rainbow colour, shadow colour, and restore-time slider/colour synchronization.
- Workflow runs #255 (ID `42848231743`), #256 (`42848323724`), #257 (`42848384875`) and #258 (`42848515717`) all completed successfully. Latest exact-commit run #258 tested commit `0f6e56c6c432208554d351e66efac5d367c8be24` and passed HTML/JavaScript validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload.
- This is a code/build verification milestone, not physical-device visual/audio QA. Actual glow appearance, audio response, mobile performance, undo/redo and preview/export parity still require further audit and final device test.
- Next: extend remaining Aspectrum-derived visualizer controls (layer spin force/momentum/speed/toggle, shake, trail, wave delay/reverse/gradient, glow mode/intensity sync and remaining material/layer behaviour), then verify Logo, Elements, Sticker, Text, Turning Point, Tempo and templates end-to-end.


## 33. Independent Visualizer Layer Spin — 2026-11-10

- Added independent per-layer spin toggle, force, momentum and speed controls in the Visualizer Layers panel.
- Layer state stores `spinEnabled`, `spinForce`, `spinMomentum`, and `spinSpeed`; new layers inherit safe defaults and old projects without these fields fall back to defaults.
- The spectrum renderer now applies each layer's own animated rotation independently from its static rotation and transform.
- Added CI assertions for all four controls, serialized layer fields, toggle wiring and actual renderer rotation.
- Feature source commit `e916b511e5dae3c9cea83cf62c78bd7d6eb3a726` passed workflow #263 (ID `42849520675`). Regression-test commit `bb37ba27411562d38d52061132ab17161ec396f1` passed workflow #264 (ID `42849553313`).
- This confirms automated source/build checks only. Actual perceived spin motion, touch control, undo/redo and performance remain pending device QA.
- Next: implement remaining Spectrum controls (shake, trail, wave delay/reverse/gradient, glow mode/intensity sync), then complete tool-by-tool behaviour and export/save QA.


## 34. Spectrum motion, glow and trail completion checkpoint — 2026-11-10

- Added Spectrum shake enable/amount and wired the canvas transform to audio-responsive motion.
- Added wave delay enable/time, reverse direction and selectable spectrum/rainbow/solid gradient. Fixed a renderer scope error in the gradient path by drawing each segment with a locally scoped gradient position.
- Added glow modes (Soft, Neon, Core) and audio intensity sync; both affect actual renderer glow strength.
- Added a bounded four-frame reusable spectrum history for visualizer trails, including adjustable trail strength. History buffers are cleared when the feature is toggled or restored disabled.
- Reused a fixed 200-point wave buffer instead of allocating a new typed array on each frame to reduce garbage collection on mobile.
- Added project state capture/restore and UI synchronization for new settings; added regression checks for controls, rendering, state persistence, bounded history and reusable buffers.
- Regression test initially exposed a stale test assumption about the renderer function signature and ring branch; the test was updated to match the actual implementation without removing the feature checks.
- Latest confirmed build: workflow #277, ID `42851394517`, commit `6cdd37944da0670adebc1976d4bcf832c80d9e4b`, success. Run #276 (ID `42851369268`) also succeeded.
- Latest HTML blob SHA: `56b89329258e620d40d13d101631297f45dce853`.
- APK artifact: `VISO-Studio-advanced-debug-apk`, artifact ID `13780247825`, ZIP digest `sha256:6ee4867373601208b83cd319566f3b1308c10b2dd94980f6832079e2b5dfc4de`. Extracted APK: 84,492,335 bytes; SHA-256 `4ef719d615ea4c49e25224749d73e5bad635929c2447f8f1137cd03c2639cf61`. The workflow's APK verification step confirmed package `com.viso.lite` and byte-identical packaged `assets/index.html` against the branch source; the downloaded artifact ZIP and APK ZIP both passed integrity tests.
- This is automated build/package verification only; physical-device playback, visual appearance, heat, battery and touch QA are still pending.
- Next: audit shape/style/symmetry/material/layer-mode behaviour and per-layer controls, then proceed through each dock group, project/template asset round-trip, undo/redo and export/audio sync. Do not release yet.


## 34. Spectrum shake, wave controls, glow sync and bounded trails — 2026-11-10

- Added spectrum shake enable/amount with deterministic per-layer movement.
- Added wave delay enable/time, reverse direction and three wave colour modes (Spectrum colours, Rainbow, Solid core colour).
- Added Glow mode (Soft, Neon, Core) and optional live-audio intensity sync.
- Added a bounded four-frame spectrum trail history (200 samples per frame), with adjustable trail strength. Trail recursion is guarded by an explicit trail-render flag; history is only sampled while enabled.
- Wave drawing now reuses a fixed `Float32Array(200)` instead of allocating a new array for each pass/frame, avoiding repeated garbage on mobile.
- Added regression assertions for shake/wave/glow controls and state restore. CI #278 (ID `42851839470`) passed on exact commit `0f8ad3028a5d5ff09d1f3cb94940cacdca347fd3`, including HTML/JS validation, archive tests, Android APK build, packaged APK verification and artifact upload.
- Artifact ID: `13779639787`, `VISO-Studio-advanced-debug-apk` (download/ZIP and APK checks still need to be repeated on this latest artifact before final release).
- Earlier runs #272–#275 had stale regression assertions around the Ring renderer/signature; the checks were corrected and superseded by passing #276–#278. Do not treat those earlier failures as current.
- Remaining Spectrum items: validate trail visual layering and performance, ensure every new control has undo support, and audit any missing material-specific parameters. Physical device audio/visual QA remains pending.


## 35. Spectrum undo integration and latest artifact verification — 2026-11-10

- Spectrum range controls now call `snap()` before user interaction (pointer/focus), so undo captures the previous state rather than the already-changed value.
- Spectrum toggles, colour pickers and select controls also capture a pre-change snapshot. Per-layer controls already used the same pattern.
- Added regression assertions for slider and non-slider Spectrum undo hooks. CI run #288 (ID `42853169247`) passed the source change; run #289 (ID `42853217150`) passed the test commit `af0164f48ffd0a34ad91d015c893262920531454`.
- Downloaded run #289 artifact ID `13781557416` (`VISO-Studio-advanced-debug-apk`). Outer artifact ZIP and inner APK ZIP integrity both passed. APK size: 84,489,xxx bytes (see local verification); APK SHA-256: `8cafe76ae1ecdb0fb97e45232583d35ff6f51094aadcc81e3bdc044af508287e`. Packaged `assets/index.html` is present (273,301 bytes). Workflow's packaged APK verification step passed.
- This confirms automated build/package integrity only, not physical-device installation or visual/audio behaviour.
- Next: complete the remaining visualizer material/layer parity audit, then perform systematic end-to-end QA of each dock group and the project/template/export pipelines.


## 36. Shared Spectrum response across all render shapes — 2026-11-10

- Audited the Spectrum renderer and found that several shape branches read raw `specSmooth` values directly, bypassing the common response function. This meant Invert, audio Multiplier and Uniform response could work in the Flats/Square/NCS branch but be ignored by radial, ring, sphere, tunnel, mirror, dual, wave and dot-style rendering.
- Updated each renderer branch to use the shared `val(index)` response function, applying multiplier, uniform response floor and layer inversion consistently.
- Added regression assertions that all renderer branches use the processed response and no raw `specSmooth[index]/255` reads remain in the shape-rendering section.
- Source commit: `d7d5a83e6bcdbc79f4d02cf3de49ede4001f173d`.
- Test commit: `a813bf5ae012261384f7dcaaa77e41d7ff3de02c`.
- CI run #542 (ID `42854308879`) passed: HTML/JS validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13781912155`; outer ZIP size 32,188,351 bytes; digest `sha256:c9a1a50ceb4aa3abd42bbda555b49334bbb98d94b2a90c9052c5a29360106615`.
- This is automated code/build/package verification, not physical-device testing.
- Next: audit and implement missing Shape/Style/Symmetry/Material/Layers Mode behaviour across all shapes, then proceed through each dock group and project/template/export workflows.


## 37. Spectrum layer modes across shape families — 2026-11-10

- Audited Layers Mode and found Web, Combo and Scale effects were only active in the Flats/Square/NCS branch. Other spectrum shapes therefore appeared unchanged when those modes were selected.
- Added a shared mode-finish renderer for Web (connecting spectrum line) and Combo (overlay baseline), called by both the flat-family branch and the general shape renderer.
- Moved Scale behaviour into the shared response function so it affects all shapes consistently, while preserving the existing Stack per-layer offset and Uniform response-floor behaviour.
- Added regression assertions for cross-shape Scale/Web/Combo behaviour.
- Source commit: `fc7f221d41040f5367d1148f8130058a2b043d93`.
- Test commit: `5ffac539c464bda38b28c13e36c6c301cf18a040`.
- CI run #547 (ID `42854739363`) passed: HTML/JS validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13782935549`; outer ZIP size 32,188,511 bytes; digest `sha256:bcfd522e869a59fa25c2c5aeb0bba53dafc616333f60e1884a388e50993309a8`.
- This is automated code/build/package verification, not physical-device testing.
- Next: audit shape-specific Style and Symmetry behaviour and implement verified material-specific visual differences; then continue all dock groups and project/template/export workflows.


## 38. Spectrum material-specific rendering finishes — 2026-11-10

- Extended the six Spectrum material choices beyond colour palettes. Fire now has a stronger warm glow and ember shadow; Chrome uses a distinct square edge treatment and cool metallic shadow; Plastic uses a fuller stroke and blue shadow; Diamond uses a finer stroke and bright icy glow; Liquid Glass uses translucent colour, a slightly fuller stroke and pale cyan glow.
- Material finish factors are applied through the shared Spectrum renderer so the material choice affects all supported shapes.
- Added regression assertions for material width, glow, shadow colour and Chrome edge treatment.
- Source commit: `b353c3e657f2ddc91f65de9e713c43bb28c46a62`.
- Test commit: `6f25783427b4a06dda0918e550be7fa2e0c9ed27`.
- CI workflow run #318 (ID `42855288123`) passed: HTML/JS validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13781604205`; outer ZIP size 32,188,688 bytes; digest `sha256:c56e2abbd0f7536f7493abd7a4b7d821727c01f30b4500b7be76bbf7d4804c3c`.
- This is automated code/build/package verification, not physical-device testing.
- Next: finish Shape/Style/Symmetry parity, then continue every dock group and the full project/template/export workflow audit.


## 39. Spectrum Solid and Point styles across shape families — 2026-11-10

- Audited Spectrum Style and found Solid/Point variants were only used by the Flats/NCS renderer; Square, radial, ring, sphere, tunnel, mirror, dual, wave and dot-style shapes did not respond to the Style selector.
- Added a shared style-finish renderer for general shapes: Point draws audio-responsive markers; Solid draws a filled spectrum region. Flats and NCS keep their native style handling.
- Added regression checks for Point markers, Solid fill and use of the shared style finish in both renderer paths.
- Source commit: `dac5fe26a4f8007e3e08ff0caf00ba6266fcbeba`.
- Test commit: `a9adc746f37f1a9829c0b9794faf16dd9d06d428`.
- CI workflow run #323 (ID `42855684313`) passed: HTML/JS validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13782896586`; outer ZIP size 32,188,860 bytes; digest `sha256:91110f0b9487c1b47108e181df42e11f16bdeb15ebdd4e45d4f654abcdee29e0`.
- This is automated code/build/package verification, not physical-device testing.
- Next: complete Symmetry semantics and continue systematic end-to-end audit of all dock groups, project/template and export workflows.


## 40. Shared Spectrum symmetry for every shape family — 2026-11-10

- Added a shared symmetry wrapper around the Spectrum geometry renderer so all shape families receive the selected symmetry transform.
- Modes: None (single render), X (left/right mirror), X and Y (four-way reflection), Across (top/bottom reflection), and Triple X (three reduced vertically stacked copies).
- Removed the old partial Flats/NCS-only mirror duplication so symmetry is applied consistently once through the wrapper.
- Trail afterimages now recurse through the geometry function inside the active symmetry transform, preventing nested symmetry multiplication.
- Updated the CI source assertion to follow the renamed geometry helper and added regression checks for all symmetry modes.
- Source commit: `f8418100dbb73d68dd54ee82f4ef287f604afdf3`.
- Workflow regression update: `b2feb9d2d2d87b891ce497aaa6809b706d2dfe73`.
- Test commit: `75ec34f71b4c3ce30bc7019483945eb9c6305f47`.
- CI workflow run #330 (ID `42856294617`) passed: HTML/JS validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13782762494`; outer ZIP size 32,189,059 bytes; digest `sha256:a4e773be83cbb0db4f46bd2a2645abd5bfa56099550ae375480625742917189b`.
- This is automated code/build/package verification, not physical-device testing. Exact Aspectrum semantics for the named symmetry modes remain to be confirmed if more reference evidence becomes recoverable.
- Next: systematically audit all 12 dock groups, then project/template asset round-trip, export/audio sync and remaining Aspectrum feature parity.


## 41. Twelve-group dock category routing — 2026-11-10

- Runtime audit of `visoToolCategory(title)` found that the dynamically renamed headings `Elements` and `Overlay FX` did not match the category rules. Both fell through to the default Visualizer category, so their dedicated dock group could be empty and their controls misfiled.
- Added explicit `Elements` and `Overlay FX` category matching.
- Added a regression test that executes the category mapper against all twelve requested group labels and checks the exact dock order, including Background Video last.
- Source commit: `bbef50bbed9d7cb82bc8a928a2cc9e34c96c8595`.
- Test commit: `aea52162d0df55c3b42b8ae2a5452485afef1ca3`.
- CI workflow run #337 (ID `42856977500`) passed: HTML/JS validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13783047449`; outer ZIP size 32,189,078 bytes; digest `sha256:abbe696915c9f42809ca02ebf17f14580891bd75542d0c0f52839e8cdf881fe6`.
- This confirms group category routing and order. It does not yet certify every control's event wiring or device behaviour.
- Next: audit all tool-group controls for state/render/undo/project/export integration, starting with Logo/Photo and Sticker.


## 42. Audio upload grouping and manual Tempo BPM — 2026-11-10

- Moved the Audio upload button out of Media and into the Audio tool group, with the explicit label “Upload / Replace Audio”. Loop Song and Bass/Mids/Treble frequency response controls remain grouped in Audio.
- Added a Manual BPM slider to the separate Tempo group. Value 0 means auto/detected BPM; manual values update the same BPM clock used by beat sync/metronome. Tap tempo and detected BPM update the slider display.
- Project restore reapplies the saved advanced BPM after registry control restoration so older project state is not overwritten by the new slider's default.
- Added regression checks for Audio upload/replace, loop/EQ grouping, manual BPM, Sync Bar and metronome sound/volume.
- Source commits: `948723c340aea33dbe2c5e0840658dee95330466`, `5e65f03c87a13a3dd59b28d93a147398978ffdaf`, `66b8f11d7123ae6ea3a0b5e1b11382491557acd1`.
- Test commits: `3961f296f121a871bf92c12035f4c64e94ec901d`, `ad290ac5a396428cc9640524dd389a531169b49e`.
- CI workflow run #346 (ID `42858119530`) passed: HTML/JS validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13782504681`; outer ZIP size 32,189,381 bytes; digest `sha256:9df4ea5cc76444510355582f656fe97cbac0b04628647c27336871950896f146`.
- This is automated code/build/package verification, not physical-device testing.
- Next: audit Overlay FX, Turning Point, Templates, Background Video and Elements, then end-to-end export/project QA.


## 43. Turning Point timeline, clean Templates panel and video export canvas — 2026-11-10

- Turning Point: each point now has an editable time slider bounded by the audio duration (60 seconds fallback); edits update the point time, reorder points chronologically and select the moved point. Transition changes now snapshot before modifying the selected point.
- Audited the 20 transition choices and added a regression test proving each UI option has a corresponding renderer case.
- Templates: removed the template search bar to honor the clean-editor/no-search requirement; layout and downloaded filters remain.
- Export: found the recording canvas was never resized from its browser default 300×150. `setVideoRenderCanvasSize()` now resizes `recordCanvas` to W×H before `captureStream()`. Normal visualizer export now renders up to 1080p; heavy Tunnel/Mirror/Dual modes use a 720p mobile-safe cap. Bitrate and export-info dimensions now use the actual recording size. Preview sizing restores selected-target info.
- Source commits: Turning Point `61d16ce2d13b9d0cd4afc23135229dcbad87ed20`; template search removal `35ce4896b61d7b32e68dce0c95ca007d8fb1d6be`; export canvas `30cd181e8fc716e464eb91820354c912d5788687`.
- Test commit: `3cafe7b29f034a3e65d39ca097ab1d97f3a3a1e4`.
- CI workflow run #358 (ID `42859376527`) passed: HTML/JS validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13783603518`; outer ZIP size 32,189,625 bytes; digest `sha256:202d6fc26f76630ec0960902fd5cc3327b25969d3bdde6be9912cbcc7d3ab8be`.
- This confirms static/code/build/package gates. Actual exported video dimensions, audio sync, background-video capture and playback must still be checked on the target Android device.
- Next: audit Background Video, Elements, Overlay FX, Templates and end-to-end export behaviour.


## 44. Background Fit / Fill / Crop and Elements controls — 2026-11-10

- Added Background media fit modes: Fit (preserve full source with contained dimensions), Fill (stretch to canvas), Crop (aspect-preserving cover). Both background image and video use the shared background renderer.
- `bgFitMode` is captured in advanced project state, restored into the selector, and defaults to Crop to preserve the previous visual result.
- Elements tool now has independent Particles amount, Sparkles amount and Reactive Frame intensity sliders. Light keeps its own size/intensity controls. The two mode sliders are linked bidirectionally with the legacy Visuals mixer sliders.
- Reactive Frame intensity now changes the actual rendered alpha and is included in project state. All four element toggles and their values are undoable.
- Fixed Dust/Sparkle mode lookups to use `findIndex(mode=>mode.name===...)` in binding, sync, restore and reset; `MODES` is an array of objects, so string `indexOf` silently failed.
- Regression tests cover fit modes and persistence, Elements render controls, state sync and reset.
- CI workflow run #367 verified the Background fit implementation; run #393 verified the combined latest branch including Elements/undo/UI cleanup and built the APK successfully.
- Latest artifact from run #393: `13785702728`, size 32,189,302 bytes; digest `sha256:948cdf0988f12a7fb7f550b17768eb6ebafb8f3e298dc0ecf9befec6e87d8364`.

## 45. Undo snapshot correctness and clean editor controls — 2026-11-10

- Added delegated pre-edit undo snapshots for all editor inputs, selects, textareas and dynamically generated controls. Pointer/focus captures before editing; keyboard changes to range/colour/select controls also capture before changing.
- Removed the generic slider's post-change snapshot, which could make Undo restore the already-changed value.
- Removed blanket click snapshots for every panel button; no-op navigation clicks were creating redundant history steps. State-mutating controls now snapshot before their action.
- Corrected pre-action snapshot order for Visuals mode toggles, Elements toggles, Overlay FX toggles, media transforms, background controls, uploads/removals, Audio Motion, BPM detection/tap, canvas custom size and Spectrum enable.
- Removed dormant search and favourite CSS/JS remnants, including template-like/favourite icon injection. The editor has no search bar, favourites or Auto+Lock controls; legacy Auto/Lock project data remains safely ignored.
- Added CI assertions for pre-edit snapshot ordering and the absence of search/favourite/Auto+Lock UI.
- Latest CI run #393 (ID `42863166776`) passed and uploaded APK artifact `13785702728`.
- Remaining: real device verification of undo feel, exported video dimensions/audio and Android save path.


## 46. Project import preflight safety — 2026-11-10

- Audited editable project asset packaging and import: .viso v3 contains project.json, asset manifest, imported audio/image/video/background/logo/font/overlay files, preview PNG and CRC-protected ZIP entries.
- Import validates manifest paths, duplicate kinds, sizes, supported kinds, 180 MB asset cap, overlay sequence/count, settings and layer manifest before applying.
- Found a destructive ordering risk: the existing project was cleared before a custom font had been prepared. A corrupt/unsupported font could therefore leave the user without the currently open project.
- Moved custom-font preparation before clearVISOAssets(). Overlay images were already preloaded/decoded before clearing; regression tests now assert both font preparation and overlay decode happen before destructive replacement.
- Source commit d7244b358705fd9db414c5d1b3175e5d94932de1; test commit a87dbcb23fcfa7b684f7c8e8fb424404cdc0758e.
- CI run #397 (ID 42869821850), exact head d7244b358705fd9db414c5d1b3175e5d94932de1, completed successfully: HTML/JS validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload all passed.
- Run #397 artifact 13788447789, size 32,189,303 bytes; outer ZIP digest sha256:130fbd1455804f1c81cd965f25bb58425215c1d88c3a4e50f7f8f5865666256c. Device install/runtime QA remains unverified.
- Remaining: project round-trip with actual large media/custom fonts, device save/open/export tests, and remaining Logo/Text/Background/Overlay/Template end-to-end QA.


## 47. Logo/Text render-path audit and CI guard — 2026-11-10

- Read the current branch source rather than relying on older chat snapshots. Latest HTML after the source change has blob SHA `b2b4b9e8b950bafa43f677c0ca0bd6e2994b03cd`, 2,949 lines.
- Confirmed the dedicated Logo tool is the active owner of `drawCustomLogo`; the Text tool renders the two primary text layers and dynamic additional text layers. `captureProjectState()` includes logo advanced settings, primary/artist text settings, and extra text layers; v3 project packaging includes logo/font assets.
- Removed the obsolete custom-logo call from the legacy `Overlay` render-module mapping. The panel organizer currently renames that panel to `Overlay FX`, so this was a stale fallback path rather than a confirmed visible double-render in the current layout.
- Added CI assertions in `.github/workflows/build-advanced.yml` that require exactly one dedicated custom-logo render call and reject reintroduction of the legacy Overlay call. Workflow edit commit: `e2b19e3d1fd620a4485a69c11393419def240329`; source edit commit: `c2e9b197e66dc438275dae133bf6071379c4128a`.
- Static source assertions passed after the edit. The new commits have not yet had a verified CI result recorded; do not treat run #397 as testing these later commits.
- Remaining in this workstream: complete media/photo/logo/font load and replacement audit, Text/Logo undo and project round-trip checks, preview/export parity, and device runtime verification.


## 48. Latest successful final-candidate APK — 2026-11-10

- Confirmed branch `viso/advanced-studio-ui` latest head: `01e4ea6d98d7d7cc67193678244acb1132eec2c2`.
- Latest CI run #406, ID `42877677231`, event `push`, completed with conclusion **success** on that exact branch head.
- All build job steps passed: HTML/JavaScript validation, VISO project archive regression tests, Android 36 setup, Gradle debug build, packaged APK verification and artifact upload.
- Artifact `VISO-Studio-advanced-debug-apk`, ID `13791414610`, size 32,189,300 bytes; outer artifact ZIP digest `sha256:bb914613b31e45026163c5e574bcce37262fba87728c63048946bd40961965e7`.
- Downloaded artifact locally, verified outer ZIP and inner APK archives, extracted packaged `assets/index.html`, and ran Node syntax check on both inline script blocks successfully. The packaged source contains the dedicated Logo renderer exactly once (one function definition plus one render call) and no legacy Overlay logo render call.
- APK SHA-256: `01652419833a1c99031bbd6fabb5cec1bf639dfb51ba9abf2701f8a731c7b50b`. Package ID `com.viso.lite` and byte-identical packaged/source HTML were also verified by the successful CI packaged-APK step.
- Handoff artifact is a **debug APK**. Physical Android install, device UI/touch, real media import, playback, export, MediaStore save, audio/video sync, performance/thermal behaviour and crash-free runtime have not been independently tested in this environment. Keep these explicitly open; do not label device QA complete.


## 49. Full original VISO editor recovery — 2026-11-10

- Located the exact original working source in Git history: commit `5b6fa6c269df5332a02bbc463ab8a7067d2fa28f` (`Viso working Index.html` baseline, 74,095 bytes / 1,133 lines). The baseline was not deleted.
- Recovered the original editor's independent scrollable tool panels rather than replacing them with the newer one-panel-at-a-time bottom dock. Original Media, Export, Background, Auto + Lock, Look, Visuals, Colour, Mask, Lights, Layers, Position, Text/Logo, Audio Motion and Audio Frequency Response panels remain accessible.
- Restored the full Song & Export Monitor beneath the canvas: song timer, export status, Loop Song and Stop Export at Song End. Restored the missing AUTO control, Every slider and Lock Colour / Visuals / Shape / Sway controls with working mutable state and handlers.
- Kept Undo, Redo and Export actions, original Media PNG add/remove controls, and independent Logo and Sticker editing controls. Audio upload remains in Media; Audio Motion and Audio Frequency Response remain separate original panels.
- Retained the newer additions already in the source: Spectrum Studio/layers, 8D audio and pitch lock, BPM/tempo and metronome, Text/Artist/extra text layers, Logo effects, Sticker transforms/motion, Background Fit/Fill/Crop/grade/drift, Motion FX, Progress, Reflection, Film, Atmosphere, Turning Points, Templates and editable .viso project save/open.
- No watermark code or UI. Original baseline IDs are all present in the recovered source; static audit reports 366 unique IDs, zero duplicates, all 130 original IDs present, and the logo renderer appears exactly once in the render path.
- Recovery source commit: `4c55979321d3bcfda18d3b08461a8ac15a53978b`; regression-test updates: `ab34064153318e5d62334a9fe44dcee1e71f7224`, `fe33659f13d85d5a0e5950a3eb8274d1f319c3da`, `c23b09334fb1991a4aa1e9a18b5eb2185fce1778`, `c98195099541294d11e1fd606472463f00b6ecb0`.
- CI run #414 (ID `42887881938`) on branch `viso/recover-original-full-editor`, exact head `c98195099541294d11e1fd606472463f00b6ecb0`, completed **successfully**: HTML/JavaScript validation, project archive regression tests, Android debug APK build, packaged APK verification and artifact upload.
- Artifact `VISO-Studio-advanced-debug-apk`, ID `13797372054`, 32,187,504 bytes; artifact ZIP digest `sha256:288fd328c9c8442865197f286441186722ccd4d9fed9f636383604d50118dad1`.
- Downloaded and independently checked the APK: 84,491,555 bytes; SHA-256 `7e881514a7ee7ae7008ec3583d455524d5d4b0a11c0ed6abd27d7444ae56bdf2`; APK archive integrity passes, 499 entries, 8 DEX files and 8 native libraries. Packaged HTML has 366 unique IDs, no duplicates, all restored original controls and no watermark. CI also verifies package ID `com.viso.lite` and byte-identical packaged/source HTML.
- This is still a **debug APK**. Physical Android install, real media import/playback, .viso round-trip, export/audio sync, MediaStore save, touch/layout and long-run stability remain device QA items; do not claim them tested.


## 50. Android media picker repair and compact Audio playback controls — 2026-11-10

- Owner reported that Media buttons did not open the Android file picker. Root causes addressed in the app: media buttons were serializing a full undo snapshot before opening the picker, file inputs were fully `display:none`, and native WebView chooser used a manually assembled ACTION_OPEN_DOCUMENT intent rather than WebView's own chooser contract.
- Added `openVISOFilePicker(id)`: clears the previous value to permit selecting the same file again, calls `showPicker()` directly in the tap gesture when supported, and falls back to `.click()`. File inputs are visually moved off-screen rather than removed with `display:none`.
- Photo, Video, Audio, PNG overlay, Background Image/Video, Logo, Font and .viso Project Open buttons now launch the picker immediately; undo snapshots are taken after an actual selection, not before opening the picker.
- Android `MainActivity.kt` now uses `WebChromeClient.FileChooserParams.createIntent()`, retries with ACTION_OPEN_DOCUMENT when an OEM provider rejects the first intent, and parses single/multiple selection results including ClipData fallback.
- Removed the entire Song & Export Monitor card from below the canvas. Song status, export status, Loop Song and Stop Export at Song End now live in a compact row inside Audio Motion, keeping the canvas screen clear and placing playback controls with Audio.
- Source commits: HTML `8aff8b8c54dc09ff2ca18f0403be7386e59d9a2a`; native chooser `cb9ee1fb3d28bfea04bed7756066ce3defe3432c`, Kotlin nullability fix `f3afe29a2761f8a717c53e8839ef137d0973958d`; regression suite `10f7245f470745dba1cd489b6a1b0fcdac0e3747`; CI checks `06def6aa52120e4e4d382e9366e2cc0ab5d720df`, corrected matcher `4724f9be579475c1852859836d43f8ff2b933e7e`.
- CI run #425 (ID `42894937402`) completed **successfully** on exact source head `4724f9be579475c1852859836d43f8ff2b933e7e`: HTML/JS validation, project archive regression tests, Android debug build, packaged APK verification and artifact upload all passed. CI additionally verifies `MainActivity.kt` uses WebView chooser intent and result parsing.
- Artifact `VISO-Studio-advanced-debug-apk`, ID `13799752934`, outer ZIP size 32,186,294 bytes, digest `sha256:1b65ad0d0e49ffec558e804840a2dd3a1b3fe954c2774fc1e4db2e06862a59e6`.
- Downloaded APK size 84,491,095 bytes; APK archive integrity passed; SHA-256 `b48764a939a6dee37f03cd81df2131f330d582a80f5a635eb432b2ee3725ac38`. Packaged HTML contains the picker helper and relocated Audio controls; no bottom monitor element; 365 unique IDs, no duplicates; no watermark. CI verifies package ID `com.viso.lite` and packaged/source HTML equality.
- Physical-device file picker testing on the owner's Android phone is still required. CI proves compilation and static integration, not OEM picker runtime.

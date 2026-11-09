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

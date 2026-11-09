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
- Latest known completed source commit before this roadmap: `4a67f30b6a99d84406486e8393ee3e756528d8f3` — “Finalize custom logo-only rendering and remove watermark remnants”.
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

The current working branch already contains a substantial HTML visualizer studio. The current repository asset has a known blob SHA recorded when this roadmap was created: `3d0bb7f714afd368e28420d920cdc42092bccbbe`. Always fetch the latest version and SHA before editing.

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
- Text / Logo Studio: text, size, position, opacity, colour, rotation, outline, glow, animation, font, alignment, plate, saved preset slots.
- Audio frequency response: bass, mids/vocals, treble.
- Custom logo-only rendering work has been made; continue to verify no watermark code/UI remains.

### Existing branch work reported in prior build record

- Background Drift UI/settings and drawing transform (including beat-sync-related controls) exist in some form.
- Progress bar styles include Gradient, Thin, Segments, Circle in the current implementation history.
- Motion FX include VHS, Shockwave, Lens Glow/Blur-related work in the current implementation history.
- Templates named Aura Pulse, Soft Romance, Retro VHS, Club Energy, Minimal Wave were added as editable settings presets.
- Save/Open Project using `.viso` JSON settings exists in some form.
- Important limitation: those templates were previously only settings presets, not yet proven to be complete downloadable editable projects with bundled media/assets.
- The last known CI build (run `42700249393`) succeeded and produced artifact `VISO-Studio-advanced-debug-apk` for commit `4a67f30b6a99d84406486e8393ee3e756528d8f3`. This is a CI build result only; installation and real-device testing on the user's Infinix Android phone were not confirmed.

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
| Preserve known-good VISO baseline | In progress | Compare uploaded 1,133-line working HTML with current branch; retain useful changes without rolling back newer work |
| Reference app reverse audit | In progress | Inspect UI/assets and confirm actual controls, ranges, defaults, and interactions |
| Reference feature parity | Not complete | Implement and verify each audited feature family |
| Existing VISO feature regression | Not fully verified | Test all current media, visualizer, text/logo, layer, undo/redo, save/load and export workflows |
| Logo-only / no-watermark requirement | Implemented in source history; regression check required | Search all source/UI/export paths for watermark remnants |
| Background Drift and beat sync | Partial | Verify actual beat response, settings persistence, and controls |
| Progress bar styles and positioning | Partial | Verify all styles, controls, export rendering and persistence |
| VHS / Shockwave / Lens effects | Partial | Verify effect rendering and every exposed control |
| Complete editable template packages | Not complete | Add import/download packages with editable settings and media assets |
| Modular future-tools architecture | Planned | Introduce registry/schema incrementally |
| Single integrated Android APK | In progress | Keep one package and one installable app |
| CI build | Previous build passed | Re-run after each meaningful milestone |
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

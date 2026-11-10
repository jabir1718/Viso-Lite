# VISO — Current Status

Updated: 2026-11-10
Repository: `jabir1718/Viso-Lite`
Working branch: `viso/advanced-studio-ui`
Main source: `app/src/main/assets/index.html`
Permanent roadmap: `PROJECT_ROADMAP.md`

## Latest verified Spectrum implementation
- Glow enable/blur/scale/core colour, Glow mode (Soft/Neon/Core), live-audio intensity sync.
- Point count/width, audio multiplier, rainbow gradient, shadow enable/colour/expansion.
- Per-layer spin enable/force/momentum/speed; layer transforms, colour source, outline, thickness, offset and invert.
- Spectrum shake, wave delay/time, reverse wave, spectrum/rainbow/solid wave gradient.
- Bounded 4-frame trail history with adjustable strength; fixed-size reusable wave buffer.
- Spectrum settings are included in advanced project state capture/restore; slider, toggle, colour, select and per-layer controls capture undo snapshots.
- **Shared response fix:** radial, ring, sphere, tunnel, mirror, dual, wave and dot-style renderers now all apply multiplier, uniform response and layer inversion through the same processed response function.
- **Layer mode fix:** Web and Combo render finishes are now applied to both flat-family and other shapes; Scale changes processed amplitude consistently across shapes. Stack and Uniform behaviour preserved.
- Regression tests cover shared response and cross-shape layer modes.

## Latest verified CI and APK
- Source commit: `fc7f221d41040f5367d1148f8130058a2b043d93`.
- Regression-test commit: `5ffac539c464bda38b28c13e36c6c301cf18a040`.
- CI run #547, ID `42854739363`, exact head commit `5ffac539c464bda38b28c13e36c6c301cf18a040`, conclusion **success**.
- HTML/JavaScript validation, project archive regression tests, Android debug APK build, packaged APK verification and artifact upload all passed.
- Artifact: `VISO-Studio-advanced-debug-apk`, artifact ID `13782935549`, size 32,188,511 bytes, outer artifact ZIP digest `sha256:bcfd522e869a59fa25c2c5aeb0bba53dafc616333f60e1884a388e50993309a8`.
- Workflow verified package `com.viso.lite` and packaged HTML/source integrity. Physical-device install/runtime QA is not performed.

## Dock architecture
12 bottom dock groups: Media, Logo, Audio, Text, Visualizer, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video. Background Video last; panels above dock; Export top-right.

## Not complete
- Audit remaining material-specific visualizer controls and exact Aspectrum parity.
- Verify spectrum trail visual quality/performance on device.
- Full end-to-end audit of Audio, Logo, Text, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video and original VISO features.
- Project/template asset round-trip, preview/export parity, video/audio sync and full export verification.
- Physical Android QA: install, media selection, playback, export, touch/layout, heat/battery, startup/stability.

## Safety rules
- Preserve `main` and uploaded baseline.
- Fetch latest source + SHA before every edit.
- No interim APK testing request; owner tests once at final handoff.
- CI success is not device QA.
- Update roadmap/status/next step after milestones.

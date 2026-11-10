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
- **Shared response fix:** radial, ring, sphere, tunnel, mirror, dual, wave and dot-style renderers now apply multiplier, uniform response and layer inversion through one processed response function.
- **Layer mode fix:** Web and Combo render finishes apply to both flat-family and other shapes; Scale changes processed amplitude across shapes. Stack and Uniform behaviour preserved.
- **Material finish improvement:** Fire, Chrome, Plastic, Diamond and Liquid Glass now have distinct stroke-width, glow, shadow-colour and/or edge treatments in addition to their existing material colour palettes.
- Regression tests cover shared response, cross-shape layer modes and material-specific rendering.

## Latest verified CI and APK
- Source commit: `b353c3e657f2ddc91f65de9e713c43bb28c46a62`.
- Regression-test commit: `6f25783427b4a06dda0918e550be7fa2e0c9ed27`.
- CI workflow run #318, ID `42855288123`, exact head commit `b353c3e657f2ddc91f65de9e713c43bb28c46a62`, conclusion **success**.
- HTML/JavaScript validation, project archive regression tests, Android debug APK build, packaged APK verification and artifact upload all passed.
- Artifact: `VISO-Studio-advanced-debug-apk`, artifact ID `13781604205`, size 32,188,688 bytes, outer artifact ZIP digest `sha256:c56e2abbd0f7536f7493abd7a4b7d821727c01f30b4500b7be76bbf7d4804c3c`.
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

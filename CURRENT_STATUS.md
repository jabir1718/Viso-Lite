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
- Shared response fix: radial, ring, sphere, tunnel, mirror, dual, wave and dot-style renderers apply multiplier, uniform response and layer inversion consistently.
- Layer mode fix: Web and Combo render finishes apply to flat-family and other shapes; Scale changes processed amplitude across shapes. Stack and Uniform behaviour preserved.
- Material finish improvement: Fire, Chrome, Plastic, Diamond and Liquid Glass have distinct stroke-width, glow, shadow-colour and/or edge treatments beyond their colour palettes.
- **Style improvement:** Solid and Point styles now add their corresponding filled-spectrum / point-marker rendering across shape families that previously ignored those styles; Flats and NCS retain their native style rendering.
- Regression tests cover shared response, cross-shape layer modes, material-specific rendering and Solid/Point style behaviour.

## Latest verified CI and APK
- Spectrum style source commit: `dac5fe26a4f8007e3e08ff0caf00ba6266fcbeba`.
- Regression-test commit: `a9adc746f37f1a9829c0b9794faf16dd9d06d428`.
- CI workflow run #323, ID `42855684313`, exact head commit `a9adc746f37f1a9829c0b9794faf16dd9d06d428`, conclusion **success**.
- HTML/JavaScript validation, project archive regression tests, Android debug APK build, packaged APK verification and artifact upload all passed.
- Artifact: `VISO-Studio-advanced-debug-apk`, artifact ID `13782896586`, size 32,188,860 bytes, outer artifact ZIP digest `sha256:91110f0b9487c1b47108e181df42e11f16bdeb15ebdd4e45d4f654abcdee29e0`.
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

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
- **New fix:** radial, ring, sphere, tunnel, mirror, dual, wave and dot-style renderer branches now use the shared processed spectrum response, so invert, multiplier and uniform response are applied consistently rather than being ignored by several shapes.
- Regression test asserts all shape branches use the shared response path.

## Latest verified CI and APK
- Source commit: `d7d5a83e6bcdbc79f4d02cf3de49ede4001f173d`.
- Regression-test commit: `a813bf5ae012261384f7dcaaa77e41d7ff3de02c`.
- CI run #542, ID `42854308879`, exact head commit `a813bf5ae012261384f7dcaaa77e41d7ff3de02c`, conclusion **success**.
- HTML/JavaScript validation, project archive regression tests, Android debug APK build, packaged APK verification and artifact upload all passed.
- Artifact: `VISO-Studio-advanced-debug-apk`, artifact ID `13781912155`, size 32,188,351 bytes, SHA-256 digest of outer artifact ZIP `c9a1a50ceb4aa3abd42bbda555b49334bbb98d94b2a90c9052c5a29360106615`.
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

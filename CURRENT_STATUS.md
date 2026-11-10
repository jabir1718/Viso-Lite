# VISO — Current Status

Updated: 2026-11-10
Repository: `jabir1718/Viso-Lite`
Working branch: `viso/advanced-studio-ui`
Main source: `app/src/main/assets/index.html`
Permanent roadmap: `PROJECT_ROADMAP.md`

## Latest verified Spectrum implementation
- Glow enable/blur/scale/core colour, Glow mode (Soft/Neon/Core), live-audio intensity sync.
- Point count/width, audio multiplier, rainbow gradient, shadow enable/colour/expansion.
- Per-layer spin enable/force/momentum/speed.
- Spectrum shake, wave delay/time, reverse wave, spectrum/rainbow/solid wave gradient.
- Bounded 4-frame trail history with adjustable strength; fixed-size reusable wave buffer.
- All new Spectrum settings are in advanced project state capture/restore. Spectrum sliders, toggles, colour/select controls and per-layer controls capture undo snapshots before edits.
- Regression tests cover control presence, renderer/state wiring, restore and undo hooks.

## Latest verified CI and APK
- Latest test commit: `af0164f48ffd0a34ad91d015c893262920531454`.
- CI run #289, ID `42853217150`, conclusion **success**. Run #288 (feature code) also succeeded.
- APK artifact ID: `13781557416`, `VISO-Studio-advanced-debug-apk`.
- Downloaded outer ZIP and inner APK ZIP both passed integrity checks.
- APK size: 84,492,415 bytes. APK SHA-256: `8cafe76ae1ecdb0fb97e45232583d35ff6f51094aadcc81e3bdc044af508287e`.
- APK contains `assets/index.html` (273,301 bytes); workflow packaged APK verification passed.
- Physical-device install/runtime QA is not performed.

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

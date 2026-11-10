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
- New settings are wired to renderer and advanced state capture/restore; regression tests cover control/state/render integration.
- Latest verified CI: run #278, ID `42851839470`, exact commit `0f8ad3028a5d5ff09d1f3cb94940cacdca347fd3`, success.
- Latest APK artifact ID: `13779639787`; must still be downloaded and checked before final release.
- Older failures #272–#275 were stale Ring-renderer test assertions; corrected checks and later runs #276–#278 passed.

## Dock architecture
12 bottom dock groups: Media, Logo, Audio, Text, Visualizer, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video. Background Video last; panels above dock; Export top-right.

## Not complete
- Validate spectrum trail visually/performance; add undo/redo coverage for all newly added Spectrum settings.
- Audit remaining material-specific visualizer controls and exact Aspectrum parity.
- Full end-to-end audit of Audio, Logo, Text, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video and original VISO features.
- Project/template asset round-trip, preview/export parity, video/audio sync and full export verification.
- Physical Android QA: install, media selection, playback, export, touch/layout, heat/battery, startup/stability.

## Safety rules
- Preserve `main` and uploaded baseline.
- Fetch latest source + SHA before every edit.
- No interim APK testing request; owner tests once at final handoff.
- CI success is not device QA.
- Update roadmap/status/next step after milestones.

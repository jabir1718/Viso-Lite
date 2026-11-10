# VISO — Current Status

Updated: 2026-11-10
Repository: `jabir1718/Viso-Lite`
Working branch: `viso/advanced-studio-ui`
Main source: `app/src/main/assets/index.html`
Permanent roadmap: `PROJECT_ROADMAP.md`

## Latest verified implementation
- Advanced Spectrum Studio controls: glow enable/blur/scale/core colour, point count/width, audio multiplier, rainbow gradient, shadow enable/colour/expansion.
- Visualizer Layers: independent spin enable, force, momentum and speed. Each layer has its own saved spin state and animated rotation.
- All above settings are in project state capture/restore; regression assertions cover control presence and renderer wiring.
- Feature commit for layer spin: `e916b511e5dae3c9cea83cf62c78bd7d6eb3a726`, CI run #263 passed.
- Regression tests: commit `bb37ba27411562d38d52061132ab17161ec396f1`, CI run #264 passed.
- Latest continuity-doc update commit: `9c980fa13c51d09c59826b6c29b3d1cd816e8399`; its CI result must be checked before claiming current latest success.

## Dock architecture
12 bottom dock groups: Media, Logo, Audio, Text, Visualizer, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video. Background Video last; panels above dock; Export top-right.

## Not complete
- Remaining Aspectrum Spectrum controls: shake, trail, wave delay/reverse/gradient, glow mode/intensity sync and remaining layer/material options.
- Full behaviour audit for all 12 groups and original VISO features.
- Undo/redo, project/template asset round-trip, preview/export parity and output/audio verification.
- Physical Android device QA: install, media selection, playback, export, layout/touch, heat/battery, startup and stability.

## Safety rules
- Preserve `main` and uploaded baseline.
- Fetch latest source + SHA before every edit.
- No interim APK testing request; owner tests once at final handoff.
- CI success is not device QA.
- Update roadmap/status/next step after milestones.

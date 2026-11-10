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
- Shared response: radial, ring, sphere, tunnel, mirror, dual, wave and dot-style renderers apply multiplier, uniform response and layer inversion consistently.
- Layer modes: Web and Combo render finishes apply across shape families; Scale affects processed amplitude across shapes; Stack and Uniform remain functional.
- Materials: Fire, Chrome, Plastic, Diamond and Liquid Glass have distinct stroke-width, glow, shadow-colour and/or edge treatments.
- Styles: Solid and Point now add filled-spectrum / point-marker rendering across shape families that previously ignored those styles; Flats and NCS retain native style rendering.
- **Symmetry:** all shape families now pass through a shared symmetry wrapper: X (left/right mirror), X and Y (four-way), Across (top/bottom mirror), Triple X (three reduced vertical copies), and None (single render). Exact parity with the reference app's naming/semantics remains subject to further reference verification.
- Regression tests cover shared response, cross-shape layer modes, material-specific rendering, styles and symmetry wrapper.

## Latest verified Audio/Tempo grouping and APK
- Dock source commits: Audio upload grouping `948723c340aea33dbe2c5e0840658dee95330466`; manual BPM `5e65f03c87a13a3dd59b28d93a147398978ffdaf`; BPM source line-break correction `66b8f11d7123ae6ea3a0b5e1b11382491557acd1`.
- Regression-test commits: Audio grouping `3961f296f121a871bf92c12035f4c64e94ec901d`; Tempo BPM `ad290ac5a396428cc9640524dd389a531169b49e`.
- Audio: moved the song upload button from Media into Audio and renamed it Upload / Replace Audio. Loop Song and Bass/Mids/Treble frequency controls remain inside Audio.
- Tempo: added a manual BPM slider (0 = auto) to the separate Tempo group. Tap/detected BPM updates the slider; manual BPM updates the same beat clock. Project restore reapplies the saved advanced BPM after registry restore to preserve older projects.
- Regression tests verify Audio upload/loop/EQ grouping and Tempo manual BPM, Sync Bar and metronome sound/volume controls.
- CI workflow run #346, ID `42858119530`, exact head commit `66b8f11d7123ae6ea3a0b5e1b11382491557acd1`, conclusion **success**.
- HTML/JavaScript validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload all passed.
- Artifact: `VISO-Studio-advanced-debug-apk`, artifact ID `13782504681`, size 32,189,381 bytes, outer artifact ZIP digest `sha256:9df4ea5cc76444510355582f656fe97cbac0b04628647c27336871950896f146`.
- Automated code/build verification only; physical-device install/runtime QA is not performed.

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

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

## Latest verified dock routing and APK
- Dock source commit: `bbef50bbed9d7cb82bc8a928a2cc9e34c96c8595`.
- Regression-test commit: `aea52162d0df55c3b42b8ae2a5452485afef1ca3`.
- Fixed category routing for the explicit `Elements` group and `Overlay FX` group. Previously both titles fell through to the default Visualizer category, leaving their own dock groups empty/hidden.
- Regression test executes the category mapper against all twelve requested labels and verifies the exact bottom-dock order: Media, Logo, Audio, Text, Visualizer, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video.
- CI workflow run #337, ID `42856977500`, exact head commit `aea52162d0df55c3b42b8ae2a5452485afef1ca3`, conclusion **success**.
- HTML/JavaScript validation, project archive tests, Android debug APK build, packaged APK verification and artifact upload all passed.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13783047449`, size 32,189,078 bytes, outer artifact ZIP digest `sha256:abbe696915c9f42809ca02ebf17f14580891bd75542d0c0f52839e8cdf881fe6`.
- Automated checks verify dock routing/order, not full physical interaction of every control. Physical-device QA remains pending.

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

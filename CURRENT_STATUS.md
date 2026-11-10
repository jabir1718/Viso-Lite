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

## Latest verified Turning Point, Templates and Export APK
- Audio/Tempo source: `66b8f11d7123ae6ea3a0b5e1b11382491557acd1`. Audio upload/replace is in Audio; Loop Song and EQ remain grouped. Tempo has manual BPM (0 = auto), Sync Bar and sound metronome; BPM survives project restore.
- Turning Point source: `61d16ce2d13b9d0cd4afc23135229dcbad87ed20`. Every transition point now has an editable timeline-position slider; edits sort points chronologically and snapshot undo before changes. Transition selection snapshots before updating the selected point.
- Transition audit: all 20 UI options have matching renderer cases.
- Templates: removed the template search bar to respect the clean-editor requirement; Landscape/Portrait/Downloaded filters remain.
- Export source: `30cd181e8fc716e464eb91820354c912d5788687`. Fixed the recording canvas staying at its default 300×150: video export now resizes the recording canvas to the actual render dimensions before capture. Normal modes render up to 1080p; heavy Tunnel/Mirror/Dual modes use a mobile-safe 720p cap. Export info and bitrate use actual recording dimensions, not only the requested target.
- Regression-test commit: `3cafe7b29f034a3e65d39ca097ab1d97f3a3a1e4`.
- CI workflow run #358, ID `42859376527`, exact head commit `3cafe7b29f034a3e65d39ca097ab1d97f3a3a1e4`, conclusion **success**.
- HTML/JavaScript validation, project archive regression tests, Android debug APK build, packaged APK verification and artifact upload all passed.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13783603518`, size 32,189,625 bytes, outer artifact ZIP digest `sha256:202d6fc26f76630ec0960902fd5cc3327b25969d3bdde6be9912cbcc7d3ab8be`.
- Automated verification only. Physical-device install/runtime QA and real export playback/dimensions still require final owner-side device QA.

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

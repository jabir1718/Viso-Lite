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

## Latest verified Elements, Background, Undo and APK
- Background Video: Fit / Fill / Crop modes added; Fit contains the full source, Fill stretches to canvas, Crop uses aspect-preserving cover. The chosen mode is part of advanced project state and restores to the selector. Background drift/rotation/travel/beat-sync remain in the same group.
- Elements: Particles and Sparkles have independent amount sliders; Light retains its size/intensity controls; Reactive Frame has its own intensity slider. The controls update the actual mode/render state and are serialized/restored.
- Fixed mode-object lookup to use `MODES.findIndex(mode=>mode.name===...)` in bind, sync, restore and reset paths. Earlier string `indexOf` lookups silently returned -1 because MODES is an array of objects.
- Undo: delegated pre-edit snapshots now cover dynamically added inputs, selects, textareas, ranges and colour controls. Generic sliders no longer snapshot after the value has changed. Removed the blanket click snapshot on every button because tool navigation/no-op clicks created redundant undo steps.
- Explicit pre-edit undo snapshots added/fixed for Elements/Visuals, Overlay FX toggles, media/mask transforms, media and background uploads/removal, Logo/font uploads/removal, custom canvas size, Audio 8D/pitch/loop, BPM detection/tap, Stop-at-song-end and Spectrum enable.
- Hidden search/favourites remnants removed from CSS and icon injection. No search bar, favourites or Auto+Lock controls are exposed; legacy Auto/Lock project fields are ignored for backwards compatibility.
- Export fix from source commit `30cd181e8fc716e464eb91820354c912d5788687`: recording canvas is resized to actual render dimensions before capture; normal visualizer export up to 1080p, heavy Tunnel/Mirror/Dual up to 720p; bitrate and displayed dimensions reflect actual output.
- Android save bridge source audit: WebView exposes chunked `AndroidSave` methods; Kotlin writes videos/images/other files to MediaStore Movies/Pictures/Downloads under `Viso` on Android 10+, and to app-specific external folders on older versions. File chooser supports document picking. Static source audit only; device save test remains pending.
- CI workflow run #393, ID `42863166776`, exact head commit `d46e18474e08cfa33feb0e71806168145a84d851`, conclusion **success**.
- HTML/JavaScript validation, project archive regression tests, Android debug APK build, packaged APK verification and artifact upload all passed.
- Artifact: `VISO-Studio-advanced-debug-apk`, ID `13785702728`, size 32,189,302 bytes, outer artifact ZIP digest `sha256:948cdf0988f12a7fb7f550b17768eb6ebafb8f3e298dc0ecf9befec6e87d8364`.
- Automated verification only. Physical-device install/runtime QA, actual saved file playback, export dimensions/audio sync and background-video capture remain unverified.

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

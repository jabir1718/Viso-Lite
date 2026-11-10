## Latest final-candidate APK — verified 2026-11-10
- Branch: viso/advanced-studio-ui; exact head: 01e4ea6d98d7d7cc67193678244acb1132eec2c2.
- Latest CI run #406 / 42877677231: **success**. HTML/JS validation, VISO project archive tests, Gradle debug build, packaged APK verification and upload all passed.
- Artifact ID 13791414610; artifact name VISO-Studio-advanced-debug-apk; size 32,189,300 bytes; outer ZIP SHA-256 bb914613b31e45026163c5e574bcce37262fba87728c63048946bd40961965e7.
- Downloaded and independently checked outer ZIP and inner APK integrity. Extracted packaged HTML: 2,949 lines, 2 inline script blocks; Node syntax check passed. Logo renderer single-path assertion passed.
- APK SHA-256 01652419833a1c99031bbd6fabb5cec1bf639dfb51ba9abf2701f8a731c7b50b. CI verified package ID com.viso.lite and byte-identical source/packaged HTML.
- APK is a debug build. Physical Android device QA remains open: install/launch, mobile layout/touch, actual media import/replacement, .viso save/open with real media and custom fonts, playback, export dimensions/audio sync, MediaStore save/playback, long-run performance and crash checks.

## Latest continuation checkpoint — 2026-11-10
- Confirmed run #397 / ID 42869821850 completed successfully on commit d7244b358705fd9db414c5d1b3175e5d94932de1. HTML/JS validation, project archive checks, Android debug APK build, packaged APK verification and artifact upload passed.
- Artifact: VISO-Studio-advanced-debug-apk, ID 13788447789, 32,189,303 bytes; outer ZIP digest sha256:130fbd1455804f1c81cd965f25bb58425215c1d88c3a4e50f7f8f5865666256c.
- Post-run source cleanup commit c2e9b197e66dc438275dae133bf6071379c4128a removes the stale legacy Overlay logo render hook. CI guard commit e2b19e3d1fd620a4485a69c11393419def240329 checks that only the dedicated Logo render module calls drawCustomLogo.
- Source HTML blob after the cleanup: b2b4b9e8b950bafa43f677c0ca0bd6e2994b03cd (2,949 lines). Static source assertions pass; CI for the post-run commits is not yet verified.
- Initial Logo/Text audit confirms project-state coverage for custom logo settings, primary/artist text, and dynamic text layers; .viso v3 embeds logo and font assets. Remaining: actual image/font load failures, project round-trip, undo behavior and preview/export parity.

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

## Latest verified CI and project-import hardening
- Latest successful CI before current change: run #396, ID 42863571143, head 7e7fc9ffb4a5d1fbfd540834dc25c3da605a6caf; HTML/JS, archive tests, Android debug build, packaged APK verification and upload all passed.
- Run #396 APK artifact: 13784749862, VISO-Studio-advanced-debug-apk, size 32,189,301 bytes, outer ZIP digest sha256:6907611444aeaf7926f0141f21be6ff0b34a8cf6b154243e0f26264497bda78e.
- Project import hardening source commit: d7244b358705fd9db414c5d1b3175e5d94932de1. Overlay images are decoded and the optional custom font is prepared before clearing the currently open project. A broken overlay/font therefore fails during preflight rather than first deleting the existing working project.
- Regression test commit: a87dbcb23fcfa7b684f7c8e8fb424404cdc0758e; asserts both image decode and font preparation occur before clearVISOAssets().
- CI run #397, ID 42869821850, exact head d7244b358705fd9db414c5d1b3175e5d94932de1: HTML/JS validation and project archive tests passed; Android Gradle/APK build is currently in progress. Do not call this build successful until the run completes.
- Template audit: layout filters are Landscape, Portrait and Downloaded; no template search input/identifier remains. Existing CI asserts search absence, filter logic, downloaded persistence, portrait gallery layout and editable project package selection. This is source/automated-test verification, not hands-on UI QA.

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

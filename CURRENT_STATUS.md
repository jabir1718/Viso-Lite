# VISO — Current Status

Updated: 2026-11-10
Repository: `jabir1718/Viso-Lite`
Working branch: `viso/advanced-studio-ui`
Main source: `app/src/main/assets/index.html`
Permanent roadmap: `PROJECT_ROADMAP.md`

## Latest verified implementation
- Advanced Spectrum response: glow enable/blur/scale/core colour, Soft/Neon/Core glow modes, audio intensity sync, point count/width, multiplier, rainbow gradient, shadow enable/colour/expansion.
- Spectrum motion: audio-responsive shake, wave delay/time, reverse direction and spectrum/rainbow/solid wave gradient.
- Spectrum trail: adjustable afterimages using a bounded four-frame reusable history buffer; clears stale history when disabled/restored.
- Wave rendering uses a reusable 200-point typed array rather than allocating a new array per frame.
- Visualizer Layers: independent spin enable, force, momentum and speed; per-layer state is preserved and animated rotation is applied independently.
- New spectrum controls are connected to project state capture/restore and restore-time UI synchronization; regression tests cover renderer wiring and persistence.

## Latest verified CI and APK
- Run #277 / ID `42851394517`: success on commit `6cdd37944da0670adebc1976d4bcf832c80d9e4b`.
- Run #276 / ID `42851369268`: success.
- Latest source blob SHA: `56b89329258e620d40d13d101631297f45dce853`.
- APK artifact ID: `13780247825`; artifact ZIP digest: `sha256:6ee4867373601208b83cd319566f3b1308c10b2dd94980f6832079e2b5dfc4de`.
- Extracted APK: 84,492,335 bytes; SHA-256 `4ef719d615ea4c49e25224749d73e5bad635929c2447f8f1137cd03c2639cf61`.
- Workflow verified package ID `com.viso.lite` and exact equality between packaged `assets/index.html` and branch source. Downloaded artifact ZIP and APK ZIP integrity tests passed.

## Dock architecture
12 bottom dock groups: Media, Logo, Audio, Text, Visualizer, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video. Background Video last; panels above dock; Export top-right.

## Not complete
- Audit all spectrum shapes, styles, symmetry, materials, layer modes/blends and per-layer behaviour beyond static assertions.
- Full behaviour audit for all 12 groups and original VISO features.
- Undo/redo, project/template asset round-trip, preview/export parity and output/audio verification.
- Physical Android device QA: install, media selection, playback, export, layout/touch, heat/battery, startup and stability.
- Aspectrum reference APK was malformed/truncated; only recoverable metadata is available, so exact UI parity is not yet proven.

## Safety rules
- Preserve `main` and uploaded baseline.
- Fetch latest source + SHA before every edit.
- No interim APK testing request; owner tests once at final handoff.
- CI success is not device QA.
- Update roadmap/status/next step after milestones.

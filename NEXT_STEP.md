# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest confirmed build: run #277 / `42851394517`
Latest feature commit: `6cdd37944da0670adebc1976d4bcf832c80d9e4b`
Latest HTML blob SHA: `56b89329258e620d40d13d101631297f45dce853`

## Next implementation batch
1. Fetch current branch head, source, tests and latest Actions results before editing.
2. Audit spectrum shapes (Circle, Flats, Square, NCS, Ring, Sphere, Tunnel, Mirror), styles (Solid/Bar/Point), symmetry modes, materials, layer modes/blends and per-layer controls. Verify actual geometry and output, not just option labels.
3. Add targeted regression coverage for each distinct renderer/material/layer mode and ensure settings persist through save/restore, undo/redo and export.
4. Audit all 12 dock groups for working controls, correct grouping, hidden panels, touch/scroll and no handler regressions.
5. Verify Text/Logo/Sticker independent layers, imported asset packaging, project reopen and missing-media handling.
6. Verify Turning Point transitions/timing, Tempo/metronome/sync, all editable templates and export audio/video sync.
7. Audit canvas presets/custom dimensions, full-screen preview, preview/export dimensions and output format/FPS capability.
8. Fetch final APK artifact and verify ZIP, package ID, packaged HTML/source equality and checksum after all implementation work.
9. Provide final APK only after all code/build gates; owner tests once at final handoff. Device QA remains a separate release gate.

## Resume instruction
“Read the VISO continuity files on branch `viso/advanced-studio-ui`. Verify current GitHub state and latest CI first. Continue from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements.”

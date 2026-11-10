# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest verified Spectrum CI: run #278 / `42851839470`
Tested commit: `0f8ad3028a5d5ff09d1f3cb94940cacdca347fd3`
Latest APK artifact ID: `13779639787`

## Next implementation batch
1. Fetch current branch head/source/tests and latest CI; confirm the continuity-document commits after #278.
2. Audit Spectrum undo/redo: every global and per-layer setting should create a pre-change snapshot and restore correctly; add automated state-level tests.
3. Inspect the trail renderer for layer ordering, alpha accumulation and CPU cost; ensure trail history stays bounded and is not copied unnecessarily.
4. Complete material-specific and layer-specific settings, including any remaining Aspectrum metadata identifiers not yet implemented.
5. Audit every dock group for real controls, correct placement, visible panels and no lost event handlers.
6. Verify independent Text/Logo/Sticker layers, fonts, custom image assets, transforms, animation and package round-trip.
7. Verify Turning Point gallery/timing, Tempo/metronome/sync, editable templates and project import/export.
8. Verify preview/export parity, selected dimensions/FPS/quality, audio capture/sync and Android save bridge.
9. Download latest APK artifact; check archive and APK ZIP integrity, package ID and packaged HTML/source equality. Record APK checksum and artifact ID.
10. Provide final APK only after all code/build gates. Owner performs one device test at final handoff; device QA remains a separate release gate.

## Resume instruction
“Read the VISO continuity files on branch `viso/advanced-studio-ui`. Verify current GitHub state and latest CI first. Continue from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements.”

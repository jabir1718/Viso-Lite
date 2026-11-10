# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest feature/test commits: `bb37ba27411562d38d52061132ab17161ec396f1`
Latest confirmed feature CI: run #264 / `42849553313`
Continuity-doc commit after that: `9c980fa13c51d09c59826b6c29b3d1cd816e8399` (verify its workflow before proceeding).

## Next implementation batch
1. Fetch current branch head, source, tests and latest Actions results.
2. Implement remaining Spectrum settings with actual renderer behaviour and persistence: shake, trail (use a bounded reusable buffer only if needed), wave delay/reverse/gradient, glow mode and intensity sync.
3. Add per-setting regression tests, including save/restore and no excessive per-frame allocations.
4. Audit remaining spectrum shapes, styles, symmetry, material and layer blend/mode behaviour.
5. Audit each dock group for functional controls, correct grouping, hidden panels, touch/scroll and no handler regressions.
6. Verify Text/Logo/Sticker independent layers, imported asset packaging, undo/redo and project reopen.
7. Verify Turning Point transitions/timing, Tempo/metronome/sync, all editable templates and export audio/video sync.
8. Fetch latest APK artifact and verify ZIP, APK package ID and packaged HTML/source equality; record exact artifact and checksum.
9. Provide final APK only after all code/build gates; owner tests once at final handoff. Device QA remains a separate release gate.

## Resume instruction
“Read the VISO continuity files on branch `viso/advanced-studio-ui`. Verify current GitHub state and latest CI first. Continue from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements.”

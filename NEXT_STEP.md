# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest feature commit: `0f6e56c6c432208554d351e66efac5d367c8be24`
Latest successful CI: run #258 / `42848515717`

## Next implementation batch
1. Fetch latest branch head, source, tests and CI status. Do not assume this checkpoint is still the latest.
2. Continue Spectrum Studio parity with real rendering/state wiring for layer spin force/momentum/speed/toggle, shake, trail, wave delay/reverse/gradient, glow mode/intensity sync. Check CPU/memory cost on mobile and avoid unbounded offscreen buffers.
3. Add CI tests that prove each setting changes rendering and survives state capture/restore and project package round-trip.
4. Verify each of the 12 dock groups has working controls and no hidden nested panels or broken handlers.
5. Audit Text/Logo/Sticker layers, asset embedding, independent layer transforms, undo/redo and project reopen.
6. Audit Turning Point gallery/timing and Tempo/metronome/sync behaviour.
7. Audit all templates and media round-trip; verify output dimensions/FPS/quality, audio capture and preview/export parity.
8. Fetch latest APK artifact, verify ZIP integrity, package ID and packaged HTML/source equality. Record artifact ID and APK checksum.
9. Only when all source/build gates are complete, provide the final APK for the owner's single device test. Device test is a separate release gate.

## Resume instruction
“Read the VISO continuity files on branch `viso/advanced-studio-ui`. Verify current GitHub state and latest CI first. Continue from this file's first incomplete action. Do not restart the roadmap, re-create completed work, overwrite the baseline, or ask me to repeat requirements.”

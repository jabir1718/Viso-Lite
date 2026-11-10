# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest successful CI: run #330 / `42856294617`
Successful head commit: `75ec34f71b4c3ce30bc7019483945eb9c6305f47`
Spectrum symmetry source commit: `f8418100dbb73d68dd54ee82f4ef287f604afdf3`
Workflow regression update: `b2feb9d2d2d87b891ce497aaa6809b706d2dfe73`
APK artifact: `13782762494` (`VISO-Studio-advanced-debug-apk`)
Artifact ZIP digest: `sha256:a4e773be83cbb0db4f46bd2a2645abd5bfa56099550ae375480625742917189b`

## Next implementation and QA batch
1. Confirm symmetry semantics against any recoverable Aspectrum reference evidence; current implementation provides consistent standard interpretations for all shapes.
2. Audit all 12 dock groups for real controls, grouping, event handlers, state persistence, undo/redo and preview/export behaviour.
3. Verify Logo/Photo and Sticker independent layers, upload, colour key, transforms, effects, animation and embedded assets.
4. Verify Text layers, artist/track-name, font upload/gallery, timer, styling, alignment and per-layer transforms.
5. Verify Audio upload/replace, frequency response, playback/loop inside Audio and beat analysis.
6. Verify Turning Point transitions/timing and Tempo/metronome/sync.
7. Verify templates save/import/download full editable projects with assets; test project round-trip and undo/redo.
8. Verify preview/export parity, selected dimensions/FPS/quality, audio capture/sync and Android save bridge.
9. Download and inspect the newest APK artifact after final code gates; verify outer ZIP, inner APK, package ID and packaged HTML/source equality.
10. Provide the final APK only after all code/build gates. Owner tests once at final handoff; device QA remains a separate release gate.

## Resume instruction
“Continue VISO on `viso/advanced-studio-ui` from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements. Do not request interim APK testing.”

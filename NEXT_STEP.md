# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest successful CI: run #323 / `42855684313`
Successful head commit: `a9adc746f37f1a9829c0b9794faf16dd9d06d428`
Source commit: `dac5fe26a4f8007e3e08ff0caf00ba6266fcbeba`
Regression-test commit: `a9adc746f37f1a9829c0b9794faf16dd9d06d428`
APK artifact: `13782896586` (`VISO-Studio-advanced-debug-apk`)
Artifact ZIP digest: `sha256:91110f0b9487c1b47108e181df42e11f16bdeb15ebdd4e45d4f654abcdee29e0`

## Next implementation and QA batch
1. Finish Spectrum Symmetry behaviour across all shapes (preserve existing meanings; verify exact reference semantics where possible).
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

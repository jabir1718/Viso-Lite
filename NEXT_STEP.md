# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest successful CI: run #547 / `42854739363`
Successful head commit: `5ffac539c464bda38b28c13e36c6c301cf18a040`
Source commit: `fc7f221d41040f5367d1148f8130058a2b043d93`
APK artifact: `13782935549` (`VISO-Studio-advanced-debug-apk`)
Artifact ZIP digest: `sha256:bcfd522e869a59fa25c2c5aeb0bba53dafc616333f60e1884a388e50993309a8`

## Next implementation and QA batch
1. Continue Aspectrum visualizer parity audit: make Shape, Style, Symmetry, Material, Layers Mode and Layers Blend behaviour real for every supported shape; do not infer unknown reference defaults.
2. Verify every Spectrum control's undo/redo, state restore, template/project serialization and export behaviour. Trail layering/performance still needs device QA.
3. Verify all 12 dock groups have working controls, correct grouping and no missing event handlers.
4. Verify Logo/Photo and Sticker independent layers, upload, colour key, transforms, effects, animation and embedded assets.
5. Verify Text layers, artist/track-name, font upload/gallery, timer, styling, alignment and per-layer transforms.
6. Verify Audio upload/replace, frequency response, playback/loop inside Audio and beat analysis.
7. Verify Turning Point transitions/timing and Tempo/metronome/sync.
8. Verify templates save/import/download full editable projects with assets; test project round-trip and undo/redo.
9. Verify preview/export parity, selected dimensions/FPS/quality, audio capture/sync and Android save bridge.
10. Download and inspect the newest APK artifact after final code gates; verify outer ZIP, inner APK, package ID and packaged HTML/source equality.
11. Provide the final APK only after all code/build gates. Owner tests once at final handoff; device QA remains a separate release gate.

## Resume instruction
“Continue VISO on `viso/advanced-studio-ui` from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements. Do not request interim APK testing.”

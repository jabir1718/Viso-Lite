# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest successful CI: run #542 / `42854308879`
Successful head commit: `a813bf5ae012261384f7dcaaa77e41d7ff3de02c`
Source commit: `d7d5a83e6bcdbc79f4d02cf3de49ede4001f173d`
APK artifact: `13781912155` (`VISO-Studio-advanced-debug-apk`)
Artifact ZIP digest: `sha256:c9a1a50ceb4aa3abd42bbda555b49334bbb98d94b2a90c9052c5a29360106615`

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

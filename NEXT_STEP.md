# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest confirmed test CI: run #289 / `42853217150`
Tested commit: `af0164f48ffd0a34ad91d015c893262920531454`
Verified APK artifact: `13781557416`
APK SHA-256: `8cafe76ae1ecdb0fb97e45232583d35ff6f51094aadcc81e3bdc044af508287e`

## Next implementation and QA batch
1. Fetch current branch head/source/tests and latest Actions results. Confirm all later continuity-document runs.
2. Finish Aspectrum visualizer material/layer audit: verify each material has distinct visible behaviour; check outline, offset, invert, layer mode/blend, reflection, reactive frame, and shape-specific options.
3. Audit all Spectrum controls for undo/redo, state restore, template/project serialization and export. Trail visual layering/performance needs device testing.
4. Verify each of the 12 dock groups has real controls, correct grouping, no hidden panels and no lost event handlers.
5. Verify Logo/Photo and Sticker independent layers, upload, colour key, position, size, rotation, opacity, effects, animation and project asset embedding.
6. Verify Text layers, artist/track-name, font upload/gallery, timer, styling, alignment and per-layer transforms.
7. Verify Audio upload/replace, frequency response, playback/loop inside Audio and beat analysis.
8. Verify Turning Point transitions/timing and Tempo/metronome/sync.
9. Verify templates save/import/download full editable projects with assets; project round-trip and undo/redo.
10. Verify preview/export parity, selected dimensions/FPS/quality, audio capture/sync and Android save bridge.
11. Download the newest APK artifact after all code gates; check outer ZIP, inner APK, package ID and packaged HTML/source equality.
12. Provide final APK only after all code/build gates. Owner tests once at final handoff; device QA is a separate release gate.

## Resume instruction
“Read the VISO continuity files on branch `viso/advanced-studio-ui`. Verify current GitHub state and latest CI first. Continue from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements.”

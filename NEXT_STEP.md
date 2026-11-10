# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest successful CI: run #346 / `42858119530`
Successful head commit: `66b8f11d7123ae6ea3a0b5e1b11382491557acd1`
APK artifact: `13782504681` (`VISO-Studio-advanced-debug-apk`)
Artifact ZIP digest: `sha256:9df4ea5cc76444510355582f656fe97cbac0b04628647c27336871950896f146`

## Next implementation and QA batch
1. Audit Overlay FX and Turning Point: all gallery choices, controls, timing, timeline editing, state persistence, undo and export parity.
2. Audit Templates: built-in templates, save/import/download, embedded media, editability, round-trip and missing-asset warnings.
3. Audit Background Video: upload/replace, drift, rotation, travel distance, beat sync, fit/fill/crop, dimensions and export.
4. Audit all Elements: particles, sparkles, light elements and reactive frame, each toggle and per-element controls.
5. Audit all Visualizer settings after the recent cross-shape renderer changes; check layer ordering, blend, spin, trail and mobile performance.
6. Audit Export: resolution/FPS/quality/format, progress, audio capture, exact canvas dimensions, preview/export parity and Android save bridge.
7. Audit project persistence/undo across all tool groups, including Logo/Photo, Sticker, Text, Audio, Tempo, Turning Point, Templates and Background.
8. Download and inspect the newest APK artifact after final code gates; verify outer ZIP, inner APK, package ID and packaged HTML/source equality.
9. Provide the final APK only after all code/build gates. Owner tests once at final handoff; device QA remains a separate release gate.

## Resume instruction
“Continue VISO on `viso/advanced-studio-ui` from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements. Do not request interim APK testing.”

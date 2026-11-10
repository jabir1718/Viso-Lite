# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest successful CI: run #393 / `42863166776`
Successful head commit: `d46e18474e08cfa33feb0e71806168145a84d851`
Latest UI source commit: `fedb6eb583552ae2b4e5053016b768418466486a`
APK artifact: `13785702728` (`VISO-Studio-advanced-debug-apk`)
Artifact ZIP digest: `sha256:948cdf0988f12a7fb7f550b17768eb6ebafb8f3e298dc0ecf9befec6e87d8364`

## Next implementation and QA batch
1. Verify Background Video upload/replace, Fit/Fill/Crop, drift/beat sync, asset packaging, import restore and export capture.
2. Verify Elements and Overlay FX render output for every toggle/slider, Undo/Redo, project persistence and preview/export parity.
3. Verify Templates: every built-in template applies as editable state; project save/open embeds all assets; download/import round-trip; no missing media.
4. Verify all 12 tool groups have the correct controls, no duplicates, correct bottom-dock order and mobile layout.
5. Verify Visualizer render modes after shared renderer changes: all shape families, style/material/symmetry, layer order/blend/spin/trail, performance and memory.
6. Verify export with actual files: normal 1080p, heavy 720p cap, FPS/quality/format, audio sync, background video and Android MediaStore save path. Confirm playback and actual file dimensions.
7. Verify undo/redo and persistence across all controls; close any remaining state/handler gaps.
8. Download and inspect newest APK artifact: outer ZIP, inner APK, package ID, APK signature and packaged HTML/source equality.
9. Only after all code/build gates and artifact inspection, prepare final APK for owner-side device test. Do not ask for interim APK testing.

## Resume instruction
“Continue VISO on `viso/advanced-studio-ui` from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements. Do not request interim APK testing.”

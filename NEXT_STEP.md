# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest successful CI: run #358 / `42859376527`
Successful head commit: `3cafe7b29f034a3e65d39ca097ab1d97f3a3a1e4`
Export source commit: `30cd181e8fc716e464eb91820354c912d5788687`
Turning Point source commit: `61d16ce2d13b9d0cd4afc23135229dcbad87ed20`
APK artifact: `13783603518` (`VISO-Studio-advanced-debug-apk`)
Artifact ZIP digest: `sha256:202d6fc26f76630ec0960902fd5cc3327b25969d3bdde6be9912cbcc7d3ab8be`

## Next implementation and QA batch
1. Audit Background Video: upload/replace, drift, rotation, travel distance, beat sync, fit/fill/crop, dimensions, asset persistence and export.
2. Audit Elements: particles, sparkles, light elements and reactive frame; each toggle, independent controls, undo, project persistence and export.
3. Audit Overlay FX: gallery options, all effect controls, render pipeline, timing, persistence and preview/export parity.
4. Audit Templates: verify every template applies as editable state, project save/open packages all media, and template download/import round-trip.
5. Audit all Visualizer settings after renderer changes: shape/style/symmetry/material/layer modes, layer ordering, blend, spin, trail and mobile performance.
6. Audit Export: selected resolution vs actual render cap, FPS/quality/format, progress, audio capture/sync, background video capture and Android save bridge.
7. Verify project persistence/undo across all groups and add missing regression tests.
8. Download and inspect newest APK artifact after final code gates; verify outer ZIP, inner APK, package ID and packaged HTML/source equality.
9. Provide final APK only after all code/build gates. Owner tests once at final handoff; device QA remains a separate release gate.

## Resume instruction
“Continue VISO on `viso/advanced-studio-ui` from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements. Do not request interim APK testing.”

# VISO — Next Work Item

Read PROJECT_ROADMAP.md, CURRENT_STATUS.md, and DO_NOT_BREAK.md first.

## Resume point
Branch: viso/advanced-studio-ui
Latest completed successful CI: run #396 / 42863571143
Run #396 head: 7e7fc9ffb4a5d1fbfd540834dc25c3da605a6caf
APK artifact: 13784749862 (VISO-Studio-advanced-debug-apk)
Artifact ZIP digest: sha256:6907611444aeaf7926f0141f21be6ff0b34a8cf6b154243e0f26264497bda78e
Current project-import hardening commit: d7244b358705fd9db414c5d1b3175e5d94932de1
Current CI run #397 / 42869821850 is in progress; HTML/JS and archive tests passed, APK build not yet confirmed.

## Next implementation and QA batch
1. Complete CI run #397 and fix any failure before moving on.
2. Audit Logo/Photo and Text: every control changes live output, pre-edit Undo/Redo, project save/restore, and export parity.
3. Audit Background Video upload/replace, Fit/Fill/Crop, drift/beat sync, asset packaging, import restore and export capture.
4. Verify Elements and Overlay FX render output for every toggle/slider, Undo/Redo, project persistence and preview/export parity.
5. Verify Templates: all built-ins apply as editable state; project save/open embeds assets; download/import round-trip; no missing media. Keep Landscape/Portrait/Downloaded filters, no search/favourites.
6. Verify all 12 tool groups have correct controls, no duplicates, dock order and mobile layout.
7. Verify Visualizer modes, materials, symmetry, layer order/blend/spin/trail and mobile performance.
8. Verify export with actual files: normal 1080p, heavy 720p cap, FPS/quality/format, audio sync, background video and Android MediaStore save path.
9. Verify undo/redo and persistence across all controls; close remaining state/handler gaps.
10. Download and inspect newest APK artifact: outer ZIP, inner APK, package ID, signature and packaged HTML/source equality.
11. Final handoff only after code/build gates and artifact inspection. Do not request interim APK testing.

## Resume instruction
Continue VISO on viso/advanced-studio-ui from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements. Do not request interim APK testing.

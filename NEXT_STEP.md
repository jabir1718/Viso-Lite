# VISO — Next Work Item

Read PROJECT_ROADMAP.md, CURRENT_STATUS.md, and DO_NOT_BREAK.md first.

## Resume point
- Branch: viso/advanced-studio-ui
- Last verified successful CI: run #397 / 42869821850, head d7244b358705fd9db414c5d1b3175e5d94932de1.
- Run #397 artifact: 13788447789 (VISO-Studio-advanced-debug-apk), 32,189,303 bytes; outer ZIP digest sha256:130fbd1455804f1c81cd965f25bb58425215c1d88c3a4e50f7f8f5865666256c.
- Post-run source cleanup: c2e9b197e66dc438275dae133bf6071379c4128a.
- Post-run CI guard: e2b19e3d1fd620a4485a69c11393419def240329.
- Latest HTML blob after source cleanup: b2b4b9e8b950bafa43f677c0ca0bd6e2994b03cd.
- CI for the post-run commits is not yet verified. First confirm the build triggered by the latest branch push; do not assume run #397 covers those commits.

## Next implementation and QA batch
1. Verify the latest CI run for commits c2e9b197e66dc438275dae133bf6071379c4128a / e2b19e3d1fd620a4485a69c11393419def240329. Fix failures before proceeding.
2. Finish Logo/Photo/Text audit: successful/failed image and font loading, replacement/removal, pre-edit Undo/Redo, save/open project state, and preview/export parity.
3. Audit Background Video upload/replace, Fit/Fill/Crop, drift/beat sync, asset packaging, import restore and export capture.
4. Verify Elements and Overlay FX render output for every toggle/slider, Undo/Redo, project persistence and preview/export parity.
5. Verify Templates: built-ins apply as editable state; save/open embeds assets; download/import round-trip; no missing media. Keep Landscape/Portrait/Downloaded filters, no search/favourites.
6. Verify all 12 tool groups have correct controls, no duplicates, dock order and mobile layout.
7. Verify Visualizer modes, materials, symmetry, layer order/blend/spin/trail and mobile performance.
8. Verify export with actual files: normal 1080p, heavy 720p cap, FPS/quality/format, audio sync, background video and Android MediaStore save path.
9. Verify undo/redo and persistence across all controls; close remaining state/handler gaps.
10. Download and inspect newest APK artifact: outer ZIP, inner APK, package ID, signature and packaged HTML/source equality.
11. Final handoff only after code/build gates and artifact inspection. Do not request interim APK testing.

## Resume instruction
Continue VISO on viso/advanced-studio-ui from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask the owner to repeat requirements. Do not request interim APK testing.

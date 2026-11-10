# VISO — Next Work Item

Read PROJECT_ROADMAP.md, CURRENT_STATUS.md and DO_NOT_BREAK.md first.

## Latest verified build handoff
- Repository: jabir1718/Viso-Lite
- Branch: viso/advanced-studio-ui
- Exact built head: 01e4ea6d98d7d7cc67193678244acb1132eec2c2
- CI run: #406 / 42877677231 — success.
- Artifact ID: 13791414610 (VISO-Studio-advanced-debug-apk), 32,189,300 bytes.
- Artifact ZIP SHA-256: bb914613b31e45026163c5e574bcce37262fba87728c63048946bd40961965e7.
- APK SHA-256: 01652419833a1c99031bbd6fabb5cec1bf639dfb51ba9abf2701f8a731c7b50b.
- Automated HTML/JS, project archive, Gradle build and packaged APK checks all passed. Packaged HTML Node syntax and logo single-render check independently passed.

## Remaining final QA (device-only)
1. Install and launch on a real Android device; verify startup, screen sizing, scrolling, dock/panel touch targets, rotation/keyboard and crash stability.
2. Import/replace/remove audio, image, video, background, logo, overlays and custom font; verify errors do not destroy the open project.
3. Save/open .viso projects with real imported assets, custom fonts and extra text/layers; confirm round-trip fidelity.
4. Check playback, all visualizer modes/materials/layers, Background Fit/Fill/Crop and drift, Elements and Overlay FX.
5. Export normal 1080p and heavy 720p projects; verify actual dimensions, frame rate, audio sync, background-video capture and completion/cancellation.
6. Verify Android MediaStore save locations, file visibility and playback; check long-export memory, heat and battery behaviour.
7. Record device/Android version, pass/fail and any fixes; rebuild and re-run CI after any source changes.

## Resume rules
- Do not restart the roadmap or alter main/baseline.
- No interim APK testing requests. The delivered run #406 debug APK is the first owner-facing test build; only request device QA now as final handoff, not during implementation.
- CI success is not proof of real-device runtime. Do not call device QA complete until recorded.

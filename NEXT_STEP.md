# VISO — Next Work Item

Read PROJECT_ROADMAP.md, CURRENT_STATUS.md and DO_NOT_BREAK.md first.

## Latest recovered build
- Repository: jabir1718/Viso-Lite
- Recovery branch: viso/recover-original-full-editor
- Exact built head: c98195099541294d11e1fd606472463f00b6ecb0
- Original baseline recovered from commit: 5b6fa6c269df5332a02bbc463ab8a7067d2fa28f
- CI run: #414 / 42887881938 — success.
- Artifact: VISO-Studio-advanced-debug-apk, ID 13797372054, 32,187,504 bytes.
- APK size: 84,491,555 bytes.
- APK SHA-256: 7e881514a7ee7ae7008ec3583d455524d5d4b0a11c0ed6abd27d7444ae56bdf2.
- Artifact ZIP SHA-256: 288fd328c9c8442865197f286441186722ccd4d9fed9f636383604d50118dad1.
- HTML/JavaScript validation, project archive regression tests, Android debug build, packaged APK verification and artifact upload all passed.
- Original 130 HTML IDs are present; AUTO/Lock and full Song & Export Monitor restored; 366 unique IDs, no duplicates; no watermark.
- APK is a debug build, not a production-signed release.

## Remaining final QA (device-only)
1. Install and launch on a real Android device; verify startup, screen sizing, original panel scrolling, touch targets, rotation/keyboard and crash stability.
2. Confirm the recovered original Media, Export, Background, AUTO + Lock, Look, Visuals, Colour, Mask, Lights, Layers, Position, Text and Audio panels appear and work.
3. Import/replace/remove audio, image, video, background, logo, overlays and custom font; verify errors do not destroy the open project.
4. Save/open .viso projects with real imported assets, custom fonts, text layers and overlays; confirm round-trip fidelity.
5. Check playback, all visualizer modes/materials/layers, Spectrum, 8D audio, BPM, Background Fit/Fill/Crop and drift, Elements and Overlay FX.
6. Export normal 1080p and heavy 720p projects; verify actual dimensions, frame rate, audio sync, background-video capture and completion/cancellation.
7. Verify Android MediaStore save locations and playback; check long-export memory, heat and battery behaviour.
8. Record device/Android version, pass/fail and any fixes; rebuild and re-run CI after source changes.

## Resume rules
- Do not restart the roadmap or alter main/baseline.
- Do not ask for interim APK testing; the recovered APK is the owner-facing test build.
- CI success is not proof of real-device runtime. Do not call device QA complete until recorded.

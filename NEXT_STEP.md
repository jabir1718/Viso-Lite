# VISO — Next Work Item

Read PROJECT_ROADMAP.md, CURRENT_STATUS.md and DO_NOT_BREAK.md first.

## Latest media-picker fixed build
- Repository: jabir1718/Viso-Lite
- Working branch: viso/advanced-studio-ui
- Built source head: 4724f9be579475c1852859836d43f8ff2b933e7e
- CI run: #425 / 42894937402 — success.
- Artifact: VISO-Studio-advanced-debug-apk, ID 13799752934, outer ZIP size 32,186,294 bytes.
- APK size: 84,491,095 bytes.
- APK SHA-256: b48764a939a6dee37f03cd81df2131f330d582a80f5a635eb432b2ee3725ac38.
- Artifact ZIP SHA-256: 1b65ad0d0e49ffec558e804840a2dd3a1b3fe954c2774fc1e4db2e06862a59e6.
- HTML/JavaScript, project archive tests, Android debug build, packaged APK verification and upload all passed.
- Native file chooser now uses WebView's chooser intent and ACTION_OPEN_DOCUMENT fallback; media buttons use a direct user-gesture picker helper.
- Bottom Song & Export Monitor strip removed; playback controls/status moved into Audio Motion.
- APK is a debug build, not a production-signed release.

## Remaining device QA
1. Install and launch on the owner's Android phone; verify the original recovered panel layout and mobile touch targets.
2. Test Photo, Video, Audio, PNG overlay, Background image/video, Logo, Font and .viso Project Open. Confirm each button opens Android's picker, selected URI loads, cancellation is harmless, and choosing the same file again works.
3. Verify media replacement/removal, audio playback, song timer, Loop Song and Stop-at-end controls inside Audio Motion.
4. Save/open .viso projects with real imported assets and custom fonts; confirm round-trip fidelity.
5. Check playback, Spectrum, 8D audio, BPM, Background Fit/Fill/Crop and drift, Elements and Overlay FX.
6. Export 1080p and heavy 720p projects; verify dimensions, frame rate, audio sync, background-video capture and MediaStore save.
7. Record device model/Android version, picker pass/fail and any remaining OEM-specific issue; rebuild and rerun CI after changes.

## Resume rules
- Continue from this branch and latest source; do not restart the roadmap or alter main/baseline.
- Do not ask for interim APK testing; owner wants one consolidated device test.
- CI success is not proof of real-device runtime. Keep physical-device QA open until recorded.

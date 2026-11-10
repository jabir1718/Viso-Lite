# VISO — Non-Negotiable Rules

## Project safety
- Repository: `jabir1718/Viso-Lite`
- Work only on `viso/advanced-studio-ui`; preserve `main`.
- Main app source: `app/src/main/assets/index.html`.
- Fetch current source + blob SHA before editing. Never edit from an old chat copy or stale SHA.
- Keep the uploaded “Viso working Index.html” as a reference/baseline; do not blindly overwrite newer work with it.

## Product requirements
- One integrated Android APK, package `com.viso.lite`.
- Preserve existing VISO features and handlers; no accidental deletion or duplicate controls.
- No watermark feature or watermark remnants. User-created logo overlays are allowed.
- Preserve the recovered original independently scrollable tool panels; do not replace them with a one-panel bottom dock unless the owner explicitly asks.
- Tool groups stay separate: Media, Logo, Audio, Text, Visualizer, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video (last).
- Export button stays top-right.
- Do not add search or favourites. Preserve the restored original Auto + Lock controls; the owner explicitly requested the complete old editor recovery.
- Loop Song and Stop-at-end must stay inside Audio Motion, never as a separate strip beneath the canvas.
- Bright, clean, spacious, mobile-first UI; preview/canvas must remain useful, not swallowed by empty space.
- Canvas presets/custom dimensions and full-screen preview must work.
- Every visible control must have real behaviour; no decorative/fake controls.
- New settings must be wired to rendering/behaviour, state capture/restore, undo/redo, project/template serialization and export where applicable.
- Templates must remain editable and carry required assets/settings.
- Preview and export should match as closely as the platform allows.
- Keep mobile performance and memory/battery use in mind; do not allocate huge canvases unnecessarily during preview/recording.

## Working discipline
- Do coherent, tested changes; never claim complete based only on labels or CI.
- Run validation/build after changes; fix failures before proceeding.
- Record exact commit, run number, artifact identity and remaining issues.
- Do not ask the owner to test interim APKs. Owner wants one test at final handoff.
- State clearly that CI/build success is not physical-device testing.


## Android media import
- Every media import button must open the native Android picker from the direct user tap; do not run expensive snapshot/serialization work before opening it.
- Use a WebView-compatible off-screen file input (not `display:none`), `showPicker()` when available, and a synchronous `.click()` fallback.
- Android WebChromeClient must implement `onShowFileChooser` with `FileChooserParams.createIntent()`, Activity Result handling and ACTION_OPEN_DOCUMENT fallback.
- Support image, video, audio, PNG overlays, background assets, Logo, custom font and .viso project selection. Allow selecting the same file again by clearing the input value before opening.
- Take undo snapshots after a file is selected and before applying it; never snapshot before opening the picker.
- Keep song/export status and playback toggles in Audio Motion; do not reintroduce a bottom monitor card.

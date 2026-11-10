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
- Bottom horizontal scrollable tool dock; panels open above the dock.
- Tool groups stay separate: Media, Logo, Audio, Text, Visualizer, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background Video (last).
- Export button stays top-right.
- Do not add search, favourites, Auto/Lock controls back to the requested clean dock unless owner changes the requirement.
- Loop Song must not appear in the main UI; if retained, keep it inside Audio.
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

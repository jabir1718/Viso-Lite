# VISO Lite

VISO Lite is a phone-first Android music visualizer and social-video studio. It combines an original visualizer/editor with advanced effects, editable text/logo layers, project packages and social export presets.

## Project source

- Android package: `com.viso.lite`
- Main studio: `app/src/main/assets/index.html`
- Android shell: Kotlin + Jetpack Compose + WebView
- Active development branch: `viso/advanced-studio-ui`
- Continuity record: [PROJECT_ROADMAP.md](PROJECT_ROADMAP.md)

## Studio areas

- Media: audio, image, video, PNG overlays and background media
- Visualizer: multiple visual modes, palette editing, shape/mask and motion controls
- Audio: frequency response, BPM/beat controls, spectrum modes and 8D/pitch controls
- Background: image/video/visualizer modes, grading and music-synced drift
- Logo & Text: custom logo controls, two independent editable text layers, custom fonts and presets
- Elements: masks, lights and editable overlay layers
- Overlay FX: VHS, Shockwave, Lens, Film & Vintage, Atmosphere, Surface Reflection, Vignette and Progress Bar Studio
- Templates: editable presets and complete project-package downloads
- Export: social presets and configurable image/video output

## Project files

New projects use the `.viso` v3 package format. A package is a ZIP/store archive containing:

- `project.json` — editable studio settings and layer metadata
- `assets/` — imported media, background images/videos, overlays, custom logo and font files
- `preview.png` — a canvas thumbnail

The importer verifies CRC32 checksums. Legacy v1/v2 JSON project files remain supported. Current package limits are 180 MB for save and 220 MB for import. On Android, project packages are saved under **Downloads/Viso**.

## Watermark policy

VISO has no watermark overlay or watermark export system. User-imported custom logos are supported.

## Build

GitHub Actions workflow: [Build VISO Advanced APK](.github/workflows/build-advanced.yml)

The workflow validates HTML/JavaScript, builds the Android debug APK, and uploads the APK artifact. A successful CI build is not the same as installation/device QA; device testing must be recorded separately in [PROJECT_ROADMAP.md](PROJECT_ROADMAP.md).

## Development rules

- Work on `viso/advanced-studio-ui`; preserve `main`.
- Fetch current branch/source SHAs before editing.
- Preserve the user's original working HTML controls and familiar layout.
- Keep controls functional and settings serializable; do not add cosmetic-only options.
- Update [PROJECT_ROADMAP.md](PROJECT_ROADMAP.md) after coherent milestones.

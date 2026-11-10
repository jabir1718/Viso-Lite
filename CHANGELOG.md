# VISO — Milestone Changelog

Detailed history and status are in `PROJECT_ROADMAP.md`.

## 2026-11-10 — Continuity system
- Added permanent roadmap, current status, next step, non-negotiable rules and changelog.

## 2026-11-10 — Advanced Spectrum Studio
- Added glow enable/blur/scale/core colour, point count/width, audio multiplier, rainbow gradient and shadow enable/colour/expansion.
- Wired settings into spectrum rendering and advanced project state capture/restore; added regression checks.
- CI runs #255–#258 passed.

## 2026-11-10 — Independent Visualizer Layer Spin
- Added per-layer spin toggle, force, momentum and speed; animated rotation is applied independently per layer.
- Layer spin state is persisted with the existing visualizer layer objects.
- CI run #263 passed on feature commit `e916b511e5dae3c9cea83cf62c78bd7d6eb3a726`; run #264 passed on test commit `bb37ba27411562d38d52061132ab17161ec396f1`.
- Device visual/audio/performance QA remains pending.
- Next: remaining Spectrum shake/trail/wave-delay/glow-mode controls.


## 2026-11-10 — Spectrum motion, glow and trail
- Added audio-responsive Spectrum shake, wave delay/time, reverse direction and spectrum/rainbow/solid gradient.
- Added Soft/Neon/Core glow modes and audio intensity sync.
- Added adjustable visualizer trail with a bounded four-frame history buffer; wave points use a reusable fixed buffer to avoid per-frame allocations.
- Connected new settings to project save/restore and UI restoration; added CI regression checks.
- CI #276 and #277 passed. Run #277 produced verified APK artifact ID `13780247825`; extracted APK SHA-256 `4ef719d615ea4c49e25224749d73e5bad635929c2447f8f1137cd03c2639cf61`.
- Device-level visual/audio/performance QA remains pending.

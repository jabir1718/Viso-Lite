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

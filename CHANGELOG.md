# VISO — Milestone Changelog

Detailed history and status are in `PROJECT_ROADMAP.md`.

## 2026-11-10 — Continuity system
- Added permanent roadmap, current status, next step, non-negotiable rules and changelog.

## 2026-11-10 — Advanced Spectrum Studio
- Added glow enable/blur/scale/core colour, Glow mode and live audio intensity sync.
- Added point count/width, multiplier, rainbow gradient and custom shadow.
- Added per-layer spin enable/force/momentum/speed.
- Added shake, wave delay/time, reverse wave, wave colour modes and bounded trail history.
- Reused fixed-size wave buffer to avoid per-frame allocations.
- CI #278 passed on `0f8ad3028a5d5ff09d1f3cb94940cacdca347fd3`; artifact ID `13779639787`.
- Earlier Ring renderer assertion failures were corrected and superseded by successful later CI.
- Device QA and full feature parity remain pending.

# VISO — Milestone Changelog

Detailed history and full status are in `PROJECT_ROADMAP.md`.

## 2026-11-10 — Continuity system created
- Added/updated permanent project roadmap, current status, next step, non-negotiable rules and changelog.
- Verified continuity-document builds #251–#254 succeeded.

## 2026-11-10 — Advanced Spectrum Studio batch
- Added working spectrum glow enable/blur/scale/core colour, point count/width, audio multiplier, rainbow gradient and shadow enable/colour/expansion.
- Wired values into spectrum rendering and advanced state save/restore, including restore-time UI sync.
- Added regression assertions.
- CI #255, #256, #257 and #258 succeeded. Latest exact source/test commit: `0f6e56c6c432208554d351e66efac5d367c8be24`, run #258 / `42848515717`.
- Device visual/audio QA remains pending.
- Next batch: remaining spectrum spin/shake/trail/wave delay/glow mode controls and then end-to-end tool audits.

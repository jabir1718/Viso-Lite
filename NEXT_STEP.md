# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Last confirmed successful CI: run #249 / `42845199558`
Tested commit: `d3552dc0ba39e2629a83359ba7160303275d08e4`

## Next actions (in order)
1. Fetch the latest branch head, latest HTML asset, workflow run #249 artifact list, and job logs. Do not assume the branch has not advanced.
2. Verify the grouped dock source: all 12 groups, dock order, panel activation, child panel visibility, top Export action, scroll behaviour, and Background Video last.
3. Inspect source for every grouped tool's actual control wiring. Identify controls that are only UI and implement their real behaviour; do not add fake options.
4. Continue Aspectrum parity using the recovered metadata identifiers in roadmap section 31. Prioritize visualizer glow, spin, shadow, trail, shake, point count/width, rainbow gradient and wave delay; trace each setting through render + state + undo/redo + project save/restore + export.
5. Add/adjust regression tests for each real behaviour; run CI and fix failures before moving on.
6. Continue Audio, Logo, Text, Elements, Overlay FX, Sticker, Turning Point, Tempo, Templates, Background and original VISO regression.
7. Verify APK package/source integrity and retain exact artifact ID, SHA/digest, run URL and tested commit.
8. Only after all code/build checks, provide the final APK for the owner's one-time device test. Device QA is a separate final gate.

## Resume instruction
“Read the VISO continuity files on branch `viso/advanced-studio-ui`. Verify current GitHub state and latest CI first. Continue from this file's first incomplete action. Do not restart the roadmap, re-create completed work, overwrite the baseline, or ask me to repeat requirements.”

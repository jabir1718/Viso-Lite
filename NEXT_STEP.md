# VISO — Next Work Item

Read `PROJECT_ROADMAP.md`, `CURRENT_STATUS.md`, and `DO_NOT_BREAK.md` first.

## Resume point
Branch: `viso/advanced-studio-ui`
Latest successful CI: run #337 / `42856977500`
Successful head commit: `aea52162d0df55c3b42b8ae2a5452485afef1ca3`
Dock source commit: `bbef50bbed9d7cb82bc8a928a2cc9e34c96c8595`
APK artifact: `13783047449` (`VISO-Studio-advanced-debug-apk`)
Artifact ZIP digest: `sha256:abbe696915c9f42809ca02ebf17f14580891bd75542d0c0f52839e8cdf881fe6`

## Next implementation and QA batch
1. Verify every control in the 12 dock groups is assigned to the right panel, wired to a real state/render path, undoable and serialized.
2. Logo/Photo: verify upload, logo shape, vinyl, colour key, ink glow, transforms, animation, project assets and undo/redo.
3. Sticker: verify independent layer selection, upload, colour key, transform, outline/glow/blend/motion and project round-trip.
4. Text: verify Track Name/Artist and independent text layers, font gallery/custom font, timer, style, alignment, transforms, undo and persistence.
5. Audio: verify upload/replace, frequency response, playback/loop inside Audio, beat analysis and export audio sync.
6. Turning Point: verify all transition types, point timing, timeline placement and preview/export parity.
7. Tempo: verify metronome, Sync Bar, sound/volume, BPM adjustment and beat sync.
8. Templates: verify editable template save/import/download with embedded assets, project round-trip and no missing media.
9. Background Video: verify image/video selection, drift, beat sync, dimensions, crop/fit/fill and export.
10. Verify preview/export parity, selected dimensions/FPS/quality, audio capture/sync and Android save bridge.
11. Download and inspect the newest APK artifact after final code gates; verify outer ZIP, inner APK, package ID and packaged HTML/source equality.
12. Provide the final APK only after all code/build gates. Owner tests once at final handoff; device QA remains a separate release gate.

## Resume instruction
“Continue VISO on `viso/advanced-studio-ui` from the first incomplete item. Do not restart the roadmap, overwrite the baseline, or ask me to repeat requirements. Do not request interim APK testing.”

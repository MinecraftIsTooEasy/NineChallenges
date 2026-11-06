package vbonedra.nine_challenges.challenge;

/** Implemented by a mixin on GuiSelectWorld. */
public interface WorldListChallenges {
    boolean nineChallanges$hasChallenges(int worldIndex);
    int nineChallanges$getChallengeMask(int worldIndex);

    int nineChallanges$getMouseX();
    int nineChallanges$getMouseY();
    void nineChallanges$setHoveredWorld(int worldIndex);
}
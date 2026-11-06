package vbonedra.nine_challenges.challenge;

/** Implemented by a mixin on GuiSelectWorld. */
public interface WorldListChallenges {
    boolean nineChallenges$hasChallenges(int worldIndex);
    int nineChallenges$getChallengeMask(int worldIndex);

    int nineChallenges$getMouseX();
    int nineChallenges$getMouseY();
    void nineChallenges$setHoveredWorld(int worldIndex);
}
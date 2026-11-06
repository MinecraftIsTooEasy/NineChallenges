package vbonedra.nine_challenges.challenge;

/** Implemented by mixins on GuiCreateWorld, WorldSettings and WorldInfo. */
public interface ChallengeHolder {
    int nineChallenges$getChallengeMask();
    void nineChallenges$setChallengeMask(int mask);
}
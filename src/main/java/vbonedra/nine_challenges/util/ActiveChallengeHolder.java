
package vbonedra.nine_challenges.util;

import vbonedra.nine_challenges.challenge.ChallengeHolder;
import vbonedra.nine_challenges.challenge.Challenges;

public final class ActiveChallengeHolder implements ChallengeHolder {
    @Override public int nineChallenges$getChallengeMask() { return Challenges.getActiveMask(); }
    @Override public void nineChallenges$setChallengeMask(int mask) { }
}
package vbonedra.nine_challenges.mixin.gui;

import net.minecraft.WorldSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import vbonedra.nine_challenges.challenge.ChallengeHolder;

@Mixin(WorldSettings.class)
public class WorldSettingsMixin implements ChallengeHolder {
    @Unique private int nineChallenges$mask;

    @Override public int nineChallenges$getChallengeMask() { return nineChallenges$mask; }
    @Override public void nineChallenges$setChallengeMask(int mask) { nineChallenges$mask = mask; }
}
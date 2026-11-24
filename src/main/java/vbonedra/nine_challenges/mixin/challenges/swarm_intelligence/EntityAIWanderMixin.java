package vbonedra.nine_challenges.mixin.challenges.swarm_intelligence;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.swarm_intelligence.SwarmIntelligenceHelper;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityAIWander.class)
public abstract class EntityAIWanderMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init_SaveSwarmEntity(EntityCreature par1EntityCreature, double par2, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.SWARM_INTELLIGENCE)) return;
        SwarmIntelligenceHelper.entityCreatureFromWander = par1EntityCreature;
    }

}
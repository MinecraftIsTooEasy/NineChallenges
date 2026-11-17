package vbonedra.nine_challenges.mixin.challenges.swarm_intelligence;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.EntityAISwarmMove;
import vbonedra.nine_challenges.util.SwarmIntelligenceHelper;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityAITasks.class)
public abstract class EntityAITasksMixin {

    @Shadow
    public abstract void addTask(int par1, EntityAIBase par2EntityAIBase);

    @Inject(method = "addTask", at = @At("RETURN"))
    private void addTask_AddSwarmTask(int par1, EntityAIBase par2EntityAIBase, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.SWARM_INTELLIGENCE)) return;

        if (par2EntityAIBase instanceof EntityAIWander) {
            if (SwarmIntelligenceHelper.entityCreatureFromWander != null) {
                this.addTask(par1, new EntityAISwarmMove(SwarmIntelligenceHelper.entityCreatureFromWander, 1.0D));
            }

        }

    }

}
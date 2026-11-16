package vbonedra.nine_challenges.mixin.challenges.badder_bosses.wither;

import net.minecraft.Entity;
import net.minecraft.EntityLiving;
import net.minecraft.EntitySenses;
import net.minecraft.EntityWither;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntitySenses.class)
public abstract class EntitySensesMixin {

    @Shadow EntityLiving entityObj;

    @Inject(method = "canSee(Lnet/minecraft/Entity;Z)Z", at = @At("HEAD"), cancellable = true)
    private void canSee_WitherXray(Entity entity, boolean ignore_leaves, CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.BADDER_BOSSES)) return;
        if (this.entityObj instanceof EntityWither) {
            if (entity != null) {
                cir.setReturnValue(true);
            }
        }
    }
}
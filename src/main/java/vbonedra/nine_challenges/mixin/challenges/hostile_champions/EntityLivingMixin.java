package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.EntityAnimal;
import net.minecraft.EntityLiving;
import net.minecraft.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampionType;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLiving.class)
public abstract class EntityLivingMixin {

    @Inject(method = "isTargetWithinStrikingDistance(Lnet/minecraft/EntityLivingBase;)Z", at = @At("RETURN"), cancellable = true)
    private void isTargetWithinStrikingDistance_ProjectingReach(EntityLivingBase target, CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (cir.getReturnValue()) return;
        EntityLiving self = (EntityLiving) (Object) this;

        if (((IHostileChampionType) self).NC$getHostileChampionType() == HostileChampionType.Projecting) {
            if (!(self).isAIEnabled() || self instanceof EntityAnimal) return;

            if (self.getDistance(target.posX, target.boundingBox.minY, target.posZ) <= self.getReach() * 2) {
                cir.setReturnValue(true);
            }

        }
    }
}
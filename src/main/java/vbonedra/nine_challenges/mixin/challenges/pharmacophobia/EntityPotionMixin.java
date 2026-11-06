package vbonedra.nine_challenges.mixin.challenges.pharmacophobia;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityPotion.class)
public abstract class EntityPotionMixin {

    @Redirect(
            method = "onImpact(Lnet/minecraft/RaycastCollision;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/Potion;affectEntity(Lnet/minecraft/EntityLivingBase;Lnet/minecraft/EntityLivingBase;ID)V")
    )
    private void addPotionEffect_in_onImpact_PharmacophobiaForInstantPotion(Potion potion, EntityLivingBase thrower, EntityLivingBase target, int amplifier, double intensity) {
        if (target instanceof EntityPlayer player && isChallengeEnabled(player.worldObj, Challenge.PHARMACOPHOBIA)) {
            if (potion.id == Potion.heal.id) {
                Potion.harm.affectEntity(thrower, target, amplifier, intensity);
                return;
            }
        }
        potion.affectEntity(thrower, target, amplifier, intensity);
    }

    @Redirect(
            method = "onImpact(Lnet/minecraft/RaycastCollision;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityLivingBase;addPotionEffect(Lnet/minecraft/PotionEffect;)V")
    )
    private void addPotionEffect_in_onImpact_PharmacophobiaForLongTermPotion(EntityLivingBase target, PotionEffect potionEffect) {
        if (potionEffect != null && target instanceof EntityPlayer player && isChallengeEnabled(player.worldObj, Challenge.PHARMACOPHOBIA)) {
            if (potionEffect.getPotionID() == Potion.regeneration.id) {
                target.addPotionEffect(new PotionEffect(Potion.poison.id, potionEffect.getDuration(), potionEffect.getAmplifier()));
                return;
            }
        }
        target.addPotionEffect(potionEffect);
    }
}

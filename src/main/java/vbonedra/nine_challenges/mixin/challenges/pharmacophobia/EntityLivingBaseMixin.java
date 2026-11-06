package vbonedra.nine_challenges.mixin.challenges.pharmacophobia;

import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.ItemPotion;
import net.minecraft.ItemFood;
import net.minecraft.Potion;
import net.minecraft.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @Inject(method = "addPotionEffect", at = @At("HEAD"), cancellable = true)
    private void addPotionEffect_PharmacophobiaForConsumed(PotionEffect potionEffect, CallbackInfo ci) {
        if ((Object) this instanceof EntityPlayer player) {
            if (potionEffect == null || player.worldObj.isRemote) return;
            if (isChallengeEnabled(player.worldObj, Challenge.PHARMACOPHOBIA)) {
                ItemStack usedItem = player.getItemInUse();
                if (usedItem != null && (usedItem.getItem() instanceof ItemFood || usedItem.getItem() instanceof ItemPotion)) {
                    int effectId = potionEffect.getPotionID();
                    if (effectId == Potion.heal.id) {
                        player.addPotionEffect(new PotionEffect(Potion.harm.id, potionEffect.getDuration(), potionEffect.getAmplifier()));
                        ci.cancel();
                    }
                    else if (effectId == Potion.regeneration.id) {
                        player.addPotionEffect(new PotionEffect(Potion.poison.id, potionEffect.getDuration(), potionEffect.getAmplifier()));
                        ci.cancel();
                    }
                }
            }
        }
    }

}

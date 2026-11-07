package vbonedra.nine_challenges.mixin.challenges.pharmacophobia;

import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemFood;
import net.minecraft.ItemPotion;
import net.minecraft.ItemStack;
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

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "addPotionEffect", at = @At("HEAD"), cancellable = true)
    private void addPotionEffect_PharmacophobiaForConsumed(PotionEffect potionEffect, CallbackInfo ci) {
        if (potionEffect == null
                || !((Object) this instanceof EntityPlayer player)
                || player.worldObj.isRemote
                || !isChallengeEnabled(Challenge.PHARMACOPHOBIA)) {
            return;
        }

        ItemStack usedItem = player.getItemInUse();
        if (usedItem == null || !(usedItem.getItem() instanceof ItemFood || usedItem.getItem() instanceof ItemPotion)) {
            return;
        }

        int effectId = potionEffect.getPotionID();
        if (effectId == Potion.heal.id) {
            player.addPotionEffect(new PotionEffect(Potion.harm.id, potionEffect.getDuration(), potionEffect.getAmplifier()));
            ci.cancel();
        } else if (effectId == Potion.regeneration.id) {
            player.addPotionEffect(new PotionEffect(Potion.poison.id, potionEffect.getDuration(), potionEffect.getAmplifier()));
            ci.cancel();
        }
    }
}
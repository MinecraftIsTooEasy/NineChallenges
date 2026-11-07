package vbonedra.nine_challenges.mixin.challenges.armor_is_my_faith;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vbonedra.nine_challenges.challenge.Challenge;
import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @ModifyReturnValue(method = "getTotalProtectionOfPotionEffects", at = @At("RETURN"))
    private float getTotalProtectionOfPotionEffects_FaithIsMyArmorForResistanceEffect(float original, DamageSource damage_source) {
        if ((Object) this instanceof EntityPlayer entityPlayer) {
            if (isChallengeEnabled(entityPlayer.getWorld(), Challenge.FAITH_IS_MY_ARMOR)) {
                if (damage_source == null || !damage_source.isAbsolute()) {
                    if (entityPlayer.isPotionActive(Potion.resistance)) {
                        return original * 0.5F;
                    }
                }
            }
        }
        return original;
    }
}

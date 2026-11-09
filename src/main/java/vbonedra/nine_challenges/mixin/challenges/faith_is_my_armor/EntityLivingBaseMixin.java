package vbonedra.nine_challenges.mixin.challenges.faith_is_my_armor;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @SuppressWarnings("ConstantConditions")
    @ModifyReturnValue(method = "getTotalProtectionOfPotionEffects", at = @At("RETURN"))
    private float getTotalProtectionOfPotionEffects_FaithIsMyArmorForResistanceEffect(float original, DamageSource damage_source) {
        if (Challenges.isChallengeEnabled(Challenge.FAITH_IS_MY_ARMOR)
                && (Object) this instanceof EntityPlayer player
                && (damage_source == null || !damage_source.isAbsolute())
                && player.isPotionActive(Potion.resistance)) {
            return original * 0.5F;
        }
        return original;
    }
}
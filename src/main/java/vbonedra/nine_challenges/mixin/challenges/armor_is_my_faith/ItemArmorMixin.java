package vbonedra.nine_challenges.mixin.challenges.armor_is_my_faith;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(ItemArmor.class)
public class ItemArmorMixin {
    @Unique private static final int anchorProtection = 2;

    @ModifyExpressionValue(
            method = "getProtectionAfterDamageFactor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/ItemArmor;getMultipliedProtection(Lnet/minecraft/ItemStack;)F")
    )
    private float getProtectionAfterDamageFactor_FaithIsMyArmorForArmor(float original, ItemStack item_stack, EntityLivingBase entity) {
        if (isChallengeEnabled(Challenge.FAITH_IS_MY_ARMOR)
                && entity instanceof EntityPlayer
                && item_stack != null) {
            ItemArmor itemArmor = item_stack.getItemAsArmor();
            int materialProtection = itemArmor.getMaterialProtection();

            if (materialProtection < anchorProtection) {
                original = original / 2F;
            } else if (materialProtection > anchorProtection) {
                original = ((float) itemArmor.getNumComponentsForDurability() * (float) lowerProtection(itemArmor, materialProtection)) / 24.0F;

                if (item_stack.hasEnchantment(Enchantment.protection, false)) {
                    original += original * item_stack.getEnchantmentLevelFraction(Enchantment.protection) * 0.25F;
                }
            }
        }

        return original;
    }

    // materialProtection must be > anchorProtection
    @Unique
    private static int lowerProtection(ItemArmor itemArmor, int materialProtection) {
        if (itemArmor.isChainMail()) {
            materialProtection += 2;
        }

        int newProtection = anchorProtection;

        if (materialProtection != newProtection) {
            if (materialProtection < newProtection) {
                newProtection -= 1;
            } else {
                newProtection = (materialProtection <= 6) ? 3 : materialProtection - 3;
            }
        }

        if (itemArmor.isChainMail()) {
            newProtection -= 1;
        }

        return Math.max(1, newProtection);
    }
}
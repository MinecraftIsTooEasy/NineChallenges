package vbonedra.nine_challenges.mixin.challenges.forbidden_runes;

import net.minecraft.StringTranslate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;
import static vbonedra.nine_challenges.util.EnchantmentToRuneHelper.enchantmentToRune;
import static vbonedra.nine_challenges.util.EnchantmentToRuneHelper.enchantmentToRuneDeterministic;

@Mixin(StringTranslate.class)
public class StringTranslateMixin {

    @Inject(method = "func_135064_c", at = @At("HEAD"), cancellable = true)
    private void onFunc_135064_c(String par1Str, CallbackInfoReturnable<String> cir) {
        if (isChallengeEnabled(Challenge.FORBIDDEN_RUNES)) {
            if (par1Str != null && par1Str.startsWith("enchantment.")) {
                if (par1Str.startsWith("enchantment.level.")) {
                    cir.setReturnValue(enchantmentToRuneDeterministic(par1Str, 1));
                }
                else {
                    cir.setReturnValue(enchantmentToRune(par1Str));
                }
            }
        }
    }

}

package vbonedra.nine_challenges.mixin.challenges.on_diet;

import net.minecraft.EntityPlayer;
import net.minecraft.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;
import static vbonedra.nine_challenges.util.GuiIngameHelper.isRendering;

@Mixin(FoodStats.class)
public abstract class FoodStatsMixin {

    @Shadow private EntityPlayer player;

    @Inject(method = "getNutritionLimit", at = @At("HEAD"), cancellable = true)
    private void getNutritionLimit_ReplaceReturn(CallbackInfoReturnable<Integer> cir) {
        if (isChallengeEnabled(Challenge.ON_DIET)) {
            cir.setReturnValue(getNutritionLimit());
            cir.cancel();
        }
    }

    @Unique private int getNutritionLimit() {
        int original = getNutritionLimitRaw();
        return original % 2 != 0 && isRendering ? original + 1 : original;
    }
    @Unique private int getNutritionLimitRaw() {
        return Math.max(Math.min(5 + this.player.getExperienceLevel() / 5, 12), 5);
    }

}
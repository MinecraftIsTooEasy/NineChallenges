package vbonedra.nine_challenges.mixin.challenges.on_diet;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.Item;
import net.minecraft.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow private int nutrition;

    // nutrition is just thirded
    @ModifyReturnValue(method = "getNutrition()I", at = @At("RETURN"))
    private int getNutrition_OnDiet(int original) {
        return isChallengeEnabled(Challenge.ON_DIET) ? original / 3 : original;
    }

    // satiation is halved and increased by 1 if nutrition leaves remainder
    @ModifyReturnValue(method = "getSatiation", at = @At("RETURN"))
    private int getSatiation_OnDiet(int original) {
        if (!isChallengeEnabled(Challenge.ON_DIET)) {
            return original;
        }
        int result = original == 1 ? 1 : original / 2;
        if (isNutritionRemainder()) {
            result += 1;
        }
        return result;
    }

    // if remainder in nutrition != 0, satiation gets increased by 1
    @Unique private boolean isNutritionRemainder() {
        return this.nutrition > 0 && this.nutrition % 3 != 0;
    }

    @Unique private int calculateDietStat() {
        return (int) ((float) this.nutrition * 8000F / 3F);
    }

    // it's better to modify diet stats like this; on_diet is already harsh enough, no need to lower stats even more.. maybe
    @SuppressWarnings("ConstantConditions")
    @ModifyReturnValue(method = "getProtein()I", at = @At("RETURN"))
    private int getProtein_OnDiet(int original) {
        if (isChallengeEnabled(Challenge.ON_DIET)
                && (Object) this instanceof ItemFood food
                && food.hasProtein()) {
            return calculateDietStat();
        }
        return original;
    }
    @SuppressWarnings("ConstantConditions")
    @ModifyReturnValue(method = "getEssentialFats()I", at = @At("RETURN"))
    private int getEssentialFats_OnDiet(int original) {
        if (isChallengeEnabled(Challenge.ON_DIET)
                && (Object) this instanceof ItemFood food
                && food.hasEssentialFats()) {
            return calculateDietStat();
        }
        return original;
    }
    @SuppressWarnings("ConstantConditions")
    @ModifyReturnValue(method = "getPhytonutrients()I", at = @At("RETURN"))
    private int getPhytonutrients_OnDiet(int original) {
        if (isChallengeEnabled(Challenge.ON_DIET)
                && (Object) this instanceof ItemFood food
                && food.hasPhytonutrients()) {
            return calculateDietStat();
        }
        return original;
    }

    // make satiation check modified value
    @ModifyExpressionValue(method = "addInformation", at = @At(value = "FIELD", target = "Lnet/minecraft/Item;satiation:I"))
    private int addInformation_OnDietShowSatiation(int original, @Local(ordinal = 0) int satiation) {
        return isChallengeEnabled(Challenge.ON_DIET) ? satiation : original;
    }
}
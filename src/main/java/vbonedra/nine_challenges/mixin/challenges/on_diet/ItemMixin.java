package vbonedra.nine_challenges.mixin.challenges.on_diet;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.Item;
import net.minecraft.ItemBowl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow private int nutrition;
    @Shadow private int satiation;

    // nutrition is just thirded
    @ModifyReturnValue(method = "getNutrition()I", at = @At("RETURN"))
    private int getNutrition(int original) {
        return isChallengeEnabled(Challenge.ON_DIET) ? original / getNutritionFactor() : original;
    }

    // satiation is halved and increased by 1 if nutrition leaves remainder
    @ModifyReturnValue(method = "getSatiation", at = @At("RETURN"))
    private int getSatiation(int original) {
        if (!isChallengeEnabled(Challenge.ON_DIET)) {
            return original;
        }
        int result = original == 1 ? 1 : original / 2;
        if (isNutritionRemainder()) {
            result += 1;
        }
        return result;
    }

    @Unique private int getNutritionFactor() {
        return ((Object) this) instanceof ItemBowl ? 2 : 3;
    }

    // if remainder in nutrition != 0, satiation gets increased by 1
    @Unique private boolean isNutritionRemainder() {
        return this.nutrition > 0 && this.nutrition % getNutritionFactor() != 0;
    }

    @Unique private int calculateDietStat() {
        return (int) ((float) this.nutrition * 8000F / getNutritionFactor());
    }

    @SuppressWarnings("ConstantConditions")
    @ModifyReturnValue(method = "getProtein()I", at = @At("RETURN"))
    private int getProtein(int original) {
        if (isChallengeEnabled(Challenge.ON_DIET)
                && ((Item) (Object) this).hasProtein()) {
            return calculateDietStat();
        }
        return original;
    }
    @SuppressWarnings("ConstantConditions")
    @ModifyReturnValue(method = "getEssentialFats()I", at = @At("RETURN"))
    private int getEssentialFats(int original) {
        if (isChallengeEnabled(Challenge.ON_DIET)
                && ((Item) (Object) this).hasEssentialFats()) {
            return calculateDietStat();
        }
        return original;
    }
    @SuppressWarnings("ConstantConditions")
    @ModifyReturnValue(method = "getPhytonutrients()I", at = @At("RETURN"))
    private int getPhytonutrients(int original) {
        if (isChallengeEnabled(Challenge.ON_DIET)
                && ((Item) (Object) this).hasPhytonutrients()) {
            return calculateDietStat();
        }
        return original;
    }

    // make satiation check modified value
    @ModifyExpressionValue(method = "addInformation", at = @At(value = "FIELD", target = "Lnet/minecraft/Item;satiation:I"))
    private int addInformation_ShowSatiation(int original, @Local(ordinal = 0) int satiation) {
        return isChallengeEnabled(Challenge.ON_DIET) ? satiation : original;
    }
}
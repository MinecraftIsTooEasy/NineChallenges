package vbonedra.nine_challenges.mixin.challenges.barren_land.render;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.util.DeadTint;

@Mixin(ColorizerFoliage.class)
public abstract class ColorizerFoliageMixin {

    @Inject(method = "getFoliageColor(DD)I", at = @At("RETURN"), cancellable = true)
    private static void getFoliageColor_DeadTint(double t, double h, CallbackInfoReturnable<Integer> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND))
            cir.setReturnValue(DeadTint.apply(cir.getReturnValueI()));
    }

    @Inject(method = "getFoliageColorPine()I", at = @At("RETURN"), cancellable = true)
    private static void getFoliageColorPine_DeadTint(CallbackInfoReturnable<Integer> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND))
            cir.setReturnValue(DeadTint.apply(cir.getReturnValueI()));
    }

    @Inject(method = "getFoliageColorBirch()I", at = @At("RETURN"), cancellable = true)
    private static void getFoliageColorBirch_DeadTint(CallbackInfoReturnable<Integer> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND))
            cir.setReturnValue(DeadTint.apply(cir.getReturnValueI()));
    }

    @Inject(method = "getFoliageColorBasic()I", at = @At("RETURN"), cancellable = true)
    private static void getFoliageColorBasic_DeadTint(CallbackInfoReturnable<Integer> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND))
            cir.setReturnValue(DeadTint.apply(cir.getReturnValueI()));
    }
}
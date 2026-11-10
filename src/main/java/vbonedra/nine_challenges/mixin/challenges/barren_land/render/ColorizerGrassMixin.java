package vbonedra.nine_challenges.mixin.challenges.barren_land.render;

import net.minecraft.ColorizerGrass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.util.DeadTint;

@Mixin(ColorizerGrass.class)
public abstract class ColorizerGrassMixin {

    @Inject(method = "getGrassColor(DD)I", at = @At("RETURN"), cancellable = true)
    private static void getGrassColor_DeadTint(double t, double h, CallbackInfoReturnable<Integer> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND))
            cir.setReturnValue(DeadTint.apply(cir.getReturnValueI()));
    }
}
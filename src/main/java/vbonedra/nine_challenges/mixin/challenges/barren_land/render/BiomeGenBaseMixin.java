package vbonedra.nine_challenges.mixin.challenges.barren_land.render;

import net.minecraft.BiomeGenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.util.DeadTint;

@Mixin(BiomeGenBase.class)
public abstract class BiomeGenBaseMixin {

    @Inject(method = "getBiomeGrassColor()I", at = @At("RETURN"), cancellable = true)
    private void getBiomeGrassColor_DeadTint(CallbackInfoReturnable<Integer> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND))
            cir.setReturnValue(DeadTint.apply(cir.getReturnValueI()));
    }

    @Inject(method = "getBiomeFoliageColor()I", at = @At("RETURN"), cancellable = true)
    private void getBiomeFoliageColor_DeadTint(CallbackInfoReturnable<Integer> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND))
            cir.setReturnValue(DeadTint.apply(cir.getReturnValueI()));
    }
}
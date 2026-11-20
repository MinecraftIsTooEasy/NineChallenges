package vbonedra.nine_challenges.mixin.challenges.into_darkness;

import net.minecraft.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(WorldProvider.class)
public class WorldProviderMixin {

    @Inject(method = "generateLightBrightnessTable", at = @At("RETURN"))
    protected void generateLightBrightnessTable_Darkness(CallbackInfo ci) {
        if (Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS)) ((WorldProvider) (Object) this).lightBrightnessTable[0] = 0;
    }

}

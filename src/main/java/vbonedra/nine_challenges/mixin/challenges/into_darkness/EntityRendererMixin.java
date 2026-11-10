package vbonedra.nine_challenges.mixin.challenges.into_darkness;

import net.minecraft.EntityRenderer;
import net.minecraft.GameSettings;
import net.minecraft.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @ModifyConstant(method = "updateLightmap", constant = @Constant(floatValue = 0.062F))
    private float updateLightmap_RemoveZeroLightFloor(float original) {
        return Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) ? 0.0F : original;
    }

    @ModifyConstant(method = "updateLightmap", constant = @Constant(floatValue = 0.03F))
    private float updateLightmap_RemoveZeroLightFloorPrequel(float original) {
        return Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) ? 0.0F : original;
    }

    @ModifyConstant(method = "updateLightmap", constant = @Constant(floatValue = 0.96F))
    private float updateLightmap_DoNotShrink(float original) {
        return Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) ? 1.0F : original;
    }

    @Redirect(method = "updateLightmap", at = @At(value = "FIELD", target = "Lnet/minecraft/GameSettings;gammaSetting:F"))
    private float updateLightmap_NoGamma(GameSettings settings) {
        return Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) ? 0.0F : settings.gammaSetting;
    }

    @Redirect(method = "updateLightmap", at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldClient;getMoonBrightness(FZ)F"))
    private float updateLightmap_OnlyFullMoonLights(WorldClient world, float partialTick, boolean flag) {
        float brightness = world.getMoonBrightness(partialTick, flag);
        if (Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS)
                && world.isOverworld()
                && world.provider.getMoonPhase(world.getTotalWorldTime()) != 0) {
            return 0.0F;
        }
        return brightness;
    }

}
package vbonedra.nine_challenges.mixin.challenges.into_darkness;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

// TODO: would conflict with RemoteDarkness, either don't load this mixin or make changes require not RemoteDarkness enabled
// TODO: its better to rewrite without using @ModifyConstant, since it cant be compatible with other mods that change this render
@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Unique private static boolean noNightVision() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        return !Minecraft.night_vision_override && (player == null || !player.isPotionActive(Potion.nightVision));
    }

    @ModifyConstant(method = "updateLightmap", constant = @Constant(floatValue = 0.062F))
    private float updateLightmap_RemoveZeroLightFloor(float original) {
        return Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) && noNightVision() ? 0.0F : original;
    }

    @ModifyConstant(method = "updateLightmap", constant = {
            @Constant(floatValue = 0.03F, ordinal = 0),
            @Constant(floatValue = 0.03F, ordinal = 1),
            @Constant(floatValue = 0.03F, ordinal = 2)
    })
    private float updateLightmap_RemoveMinimumBeforeNightVision(float original) {
        return Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) && noNightVision() ? 0.0F : original;
    }

    @ModifyConstant(method = "updateLightmap", constant = {
            @Constant(floatValue = 0.96F, ordinal = 0),
            @Constant(floatValue = 0.96F, ordinal = 1),
            @Constant(floatValue = 0.96F, ordinal = 2)
    })
    private float updateLightmap_DoNotShrinkBeforeNightVision(float original) {
        return Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) && noNightVision() ? 1.0F : original;
    }

    // late floor (all three channels): always removed
    @ModifyConstant(method = "updateLightmap", constant = {
            @Constant(floatValue = 0.03F, ordinal = 3),
            @Constant(floatValue = 0.03F, ordinal = 4),
            @Constant(floatValue = 0.03F, ordinal = 5)
    })
    private float updateLightmap_RemoveMinimum(float original) {
        return Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) ? 0.0F : original;
    }

    @ModifyConstant(method = "updateLightmap", constant = {
            @Constant(floatValue = 0.96F, ordinal = 3),
            @Constant(floatValue = 0.96F, ordinal = 4),
            @Constant(floatValue = 0.96F, ordinal = 5)
    })
    private float updateLightmap_DoNotShrink(float original) {
        return Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) ? 1.0F : original;
    }

    @Redirect(method = "updateLightmap", at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldClient;getMoonBrightness(FZ)F"))
    private float updateLightmap_MoonLight(WorldClient world, float partialTick, boolean flag) {
        float brightness = world.getMoonBrightness(partialTick, flag);
        if (Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) && world.isOverworld()
                && !world.isBloodMoon24HourPeriod()
                && !world.isHarvestMoon24HourPeriod()
                && !world.isBlueMoon24HourPeriod()) {
            float base = world.getCurrentMoonPhaseFactor() * 1.25F;
            if (flag) {
                float rain = world.getRainStrength(partialTick);
                if (base > 0.75F && rain > 0.0F) {
                    base = base * (1.0F - rain) + 0.75F * rain;
                }
            }
            return base;
        }
        return brightness;
    }

}
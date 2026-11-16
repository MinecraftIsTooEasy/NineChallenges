package vbonedra.nine_challenges.util;

import net.minecraft.*;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public class RenderChampionOverlayHelper {

    public static final float overlayScale = 1.0F; // this is unstable when != 1
    public static final float overlaySpeed = 0.0044F;
    public static float overlayBrightness = 0.44F;

    public static final ResourceLocation championOverlay_64x32 = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/entity/champion_overlay/64x32.png");
    public static final ResourceLocation championOverlay_64x64 = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/entity/champion_overlay/64x64.png");
    public static final ResourceLocation championOverlay_128x64 = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/entity/champion_overlay/128x64.png");
    public static final ResourceLocation championOverlay_128x128 = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/entity/champion_overlay/128x128.png");

    public static ResourceLocation getChampionOverlay(int width, int height, boolean isGiant) {

        if (width > height) {
            return isGiant ? championOverlay_128x64 : championOverlay_64x32;
        }

        return isGiant ? championOverlay_128x128 : championOverlay_64x64;

    }

}

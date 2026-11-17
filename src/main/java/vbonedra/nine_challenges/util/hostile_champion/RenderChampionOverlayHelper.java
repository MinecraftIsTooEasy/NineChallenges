package vbonedra.nine_challenges.util.hostile_champion;

import net.minecraft.*;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public class RenderChampionOverlayHelper {

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

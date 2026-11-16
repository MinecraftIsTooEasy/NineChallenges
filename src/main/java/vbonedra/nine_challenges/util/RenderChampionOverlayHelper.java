package vbonedra.nine_challenges.util;

import net.minecraft.*;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public class RenderChampionOverlayHelper {

   public static final float overlayScale = 1.0F; // this is unstable when != 1
   public static final float overlaySpeed = 0.0044F;
   public static float overlayBrightness = 0.44F;
   public static final ResourceLocation championOverlay = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/entity/champion_overlay.png");
   // TODO: add more overlays based on texture resolution + giant or not

}

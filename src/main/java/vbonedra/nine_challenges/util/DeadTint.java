package vbonedra.nine_challenges.util;

import static vbonedra.nine_challenges.manylib.NineChallengesConfigs.EnableChallengeBarrenLandsDeadTint;

public final class DeadTint {
    private static final int deadTint = 0x7A6A3E;
    private static final float tintFactor = 0.33F;

    public static int apply(int rgb) {
        if (EnableChallengeBarrenLandsDeadTint.getBooleanValue()) {
            int r = (rgb >> 16) & 255;
            int g = (rgb >> 8) & 255;
            int b = rgb & 255;

            int rTint = (deadTint >> 16) & 255;
            int gTint = (deadTint >> 8) & 255;
            int bTint = deadTint & 255;
            r = (int) (r + (rTint - r) * tintFactor);
            g = (int) (g + (gTint - g) * tintFactor);
            b = (int) (b + (bTint - b) * tintFactor);

            return (r << 16) | (g << 8) | b;
        } else return rgb;
    }
}
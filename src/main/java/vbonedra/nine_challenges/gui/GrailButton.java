package vbonedra.nine_challenges.gui;

import net.minecraft.ResourceLocation;
import vbonedra.nine_challenges.challenge.ChallengeHolder;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;
import static vbonedra.nine_challenges.challenge.Challenge.countChallenges;

public class GrailButton extends IconButton {
    public static final ResourceLocation stateOff = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/icons/grail/grail_off.png");
    public static final ResourceLocation stateOn = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/icons/grail/grail_iron.png");
    public static final ResourceLocation stateDifficultyEasy = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/icons/grail/grail_copper.png");
    public static final ResourceLocation stateDifficultyNormal = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/icons/grail/grail_silver.png");
    public static final ResourceLocation stateDifficultyHard = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/icons/grail/grail_gold.png");
    public static final ResourceLocation stateDifficultyExtreme = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/icons/grail/grail_adamantium.png");

    private final ChallengeHolder holder;

    public GrailButton(int id, int x, int y, ChallengeHolder holder) {
        super(id, x, y);
        this.holder = holder;
    }

    @Override
    protected ResourceLocation getIcon() {
        return getDynamicIcon(holder.nineChallenges$getChallengeMask());
    }

    public static ResourceLocation getDynamicIcon(int challengeMask) {
        switch (countChallenges(challengeMask)) {
            case 1, 2, 3 -> {
                return GrailButton.stateDifficultyEasy;
            }
            case 4, 5, 6 -> {
                return GrailButton.stateDifficultyNormal;
            }
            case 7, 8 -> {
                return GrailButton.stateDifficultyHard;
            }
            case 9 -> {
                return GrailButton.stateDifficultyExtreme;
            }
            default -> {
                return GrailButton.stateOff;
            }
        }
    }


}
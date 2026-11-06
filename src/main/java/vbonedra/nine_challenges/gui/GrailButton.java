package vbonedra.nine_challenges.gui;

import net.minecraft.ResourceLocation;
import vbonedra.nine_challenges.challenge.ChallengeHolder;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public class GrailButton extends IconButton {
    public static final ResourceLocation stateOn = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/grail_on.png");
    public static final ResourceLocation stateOff = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/grail_off.png");

    private final ChallengeHolder holder;

    public GrailButton(int id, int x, int y, ChallengeHolder holder) {
        super(id, x, y);
        this.holder = holder;
    }

    @Override
    protected ResourceLocation getIcon() {
        return holder.nineChallenges$getChallengeMask() != 0 ? stateOn : stateOff;
    }
}
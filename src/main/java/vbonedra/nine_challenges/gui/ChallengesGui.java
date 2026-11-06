package vbonedra.nine_challenges.gui;

import net.minecraft.GuiButton;
import net.minecraft.GuiScreen;
import net.minecraft.I18n;
import net.minecraft.ResourceLocation;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.ChallengeHolder;
import vbonedra.nine_challenges.challenge.Challenges;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public class ChallengesGui extends GuiScreen {
    private static final ResourceLocation starStateOn = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/star_on.png");
    private static final ResourceLocation starStateOff = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/star_off.png");

    private static final int listPosX = 20;
    private static final int listPosY = 30;
    private static final int rowHeight = 22;
    private static final int panelPosX = 190;
    // we can be sure that -1 is free, but cant be sure about 0..8+ because challenges use them
    private static final int DONE_ID = -1;

    private final GuiScreen parent;
    private final ChallengeHolder holder;
    private Challenge selected = Challenge.values()[0];

    public ChallengesGui(GuiScreen parent) {
        this.parent = parent;
        this.holder = Challenges.holder(parent);
    }
    public ChallengesGui(GuiScreen parent, int mask) {
        this.parent = parent;
        this.holder = new ChallengeHolder() {
            @Override
            public int nineChallenges$getChallengeMask() {
                return mask;
            }

            @Override
            public void nineChallenges$setChallengeMask(int mask) {

            }
        };
    }

    private static class StarButton extends IconButton {
        private final ChallengeHolder holder;
        private final Challenge challenge;

        StarButton(int id, int x, int y, ChallengeHolder holder, Challenge challenge) {
            super(id, x, y);
            this.holder = holder;
            this.challenge = challenge;
        }

        @Override
        protected ResourceLocation getIcon() {
            return challenge.isIn(holder.nineChallenges$getChallengeMask()) ? starStateOn : starStateOff;
        }
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        for (Challenge challenge : Challenge.values()) {
            this.buttonList.add(new StarButton(challenge.ordinal(), listPosX, listPosY + challenge.ordinal() * rowHeight, holder, challenge));
        }
        this.buttonList.add(new GuiButton(DONE_ID, this.width - 110, this.height - 28, 100, 20, I18n.getString("gui.button.done.title")));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (!button.enabled) return;
        if (button.id == DONE_ID) {
            this.mc.displayGuiScreen(parent);
        } else if (button.id >= 0 && button.id < Challenge.values().length) {
            holder.nineChallenges$setChallengeMask(holder.nineChallenges$getChallengeMask() ^ Challenge.values()[button.id].mask());
        }
    }

    @Override
    protected void mouseClicked(int mx, int my, int btn) {
        super.mouseClicked(mx, my, btn);
        if (btn != 0) return;
        for (Challenge challenge : Challenge.values()) {
            int rowY = listPosY + challenge.ordinal() * rowHeight;
            if (mx >= listPosX + 24 && mx < panelPosX - 10 && my >= rowY && my < rowY + 20) {
                selected = challenge;
            }
        }
    }

    @Override
    protected void keyTyped(char c, int key) {
        if (key == 1) this.mc.displayGuiScreen(parent);
    }

    @Override
    public void drawScreen(int mx, int my, float pt) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.getString("gui.challenge_gui.title"), this.width / 2, 12, 0xFFFFFF);

        for (Challenge challenge : Challenge.values()) {
            int rowY = listPosY + challenge.ordinal() * rowHeight;
            if (challenge == selected) {
                drawRect(listPosX + 24, rowY, panelPosX - 10, rowY + 20, 0x60FFFFFF);
            }
            GuiUtil.draw(challenge.icon, listPosX + 26, rowY + 2, 16, 16);
            this.drawString(this.fontRenderer, challenge.getTitle(), listPosX + 48, rowY + 6,
                    challenge.isIn(holder.nineChallenges$getChallengeMask()) ? 0xFFFF55 : 0xFFFFFF);
        }

        this.drawString(this.fontRenderer, "§n" + selected.getTitle(), panelPosX, listPosY + 2, 0xFFFFFF);
        selected.renderDescription(new DescriptionPainter(this.fontRenderer, panelPosX, listPosY + 16, this.width - panelPosX - 20));

        super.drawScreen(mx, my, pt);
    }
}
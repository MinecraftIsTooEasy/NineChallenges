package vbonedra.nine_challenges.gui;

import net.minecraft.GuiButton;
import net.minecraft.GuiScreen;
import net.minecraft.ResourceLocation;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.ChallengeHolder;
import vbonedra.nine_challenges.challenge.Challenges;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public class ChallengesGui extends GuiScreen {
    private static final ResourceLocation STAR_ON  = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/star_on.png");
    private static final ResourceLocation STAR_OFF = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/star_off.png");

    private static final int LIST_X = 20, LIST_Y = 30, ROW_H = 22, PANEL_X = 190;
    private static final int DONE_ID = 100;

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
            return challenge.isIn(holder.nineChallenges$getChallengeMask()) ? STAR_ON : STAR_OFF;
        }
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        for (Challenge c : Challenge.values()) {
            this.buttonList.add(new StarButton(c.ordinal(), LIST_X, LIST_Y + c.ordinal() * ROW_H, holder, c));
        }
        this.buttonList.add(new GuiButton(DONE_ID, this.width - 110, this.height - 28, 100, 20, "Done"));
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
        for (Challenge c : Challenge.values()) {
            int rowY = LIST_Y + c.ordinal() * ROW_H;
            if (mx >= LIST_X + 24 && mx < PANEL_X - 10 && my >= rowY && my < rowY + 20) {
                selected = c;
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
        this.drawCenteredString(this.fontRenderer, "Challenges", this.width / 2, 12, 0xFFFFFF);

        for (Challenge c : Challenge.values()) {
            int rowY = LIST_Y + c.ordinal() * ROW_H;
            if (c == selected) {
                drawRect(LIST_X + 24, rowY, PANEL_X - 10, rowY + 20, 0x60FFFFFF);
            }
            GuiUtil.draw(c.icon, LIST_X + 26, rowY + 2, 16, 16);
            this.drawString(this.fontRenderer, c.name, LIST_X + 48, rowY + 6,
                    c.isIn(holder.nineChallenges$getChallengeMask()) ? 0xFFFF55 : 0xFFFFFF);
        }

        this.drawString(this.fontRenderer, "§n" + selected.name, PANEL_X, LIST_Y + 2, 0xFFFFFF);
        selected.renderDescription(new DescriptionPainter(this.fontRenderer, PANEL_X, LIST_Y + 16, this.width - PANEL_X - 20));

        super.drawScreen(mx, my, pt);
    }
}
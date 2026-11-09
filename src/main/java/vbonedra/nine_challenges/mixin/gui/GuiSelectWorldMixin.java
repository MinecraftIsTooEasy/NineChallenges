package vbonedra.nine_challenges.mixin.gui;

import net.minecraft.GuiScreen;
import net.minecraft.GuiSelectWorld;
import net.minecraft.ISaveHandler;
import net.minecraft.WorldInfo;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.challenge.WorldListChallenges;
import vbonedra.nine_challenges.gui.GuiUtil;

import java.util.List;

@Mixin(GuiSelectWorld.class)
public abstract class GuiSelectWorldMixin extends GuiScreen implements WorldListChallenges {

    @Shadow private List saveList;
    @Shadow protected abstract String getSaveFileName(int index);

    @Unique private int[] masks = new int[0];
    @Unique private int mouseX;
    @Unique private int mouseY;
    @Unique private int hoveredWorld = -1;


    @Unique private static final int tooltipOutlineColor = 0xF0100010;
    @Unique private static final int tooltipBorderColorTop = 0x505000FF;
    @Unique private static final int tooltipBorderColorBottom = 0x5028007F;

    @Inject(method = "loadSaves", at = @At("TAIL"))
    private void loadSaves_CacheMasks(CallbackInfo ci) {
        int[] masks = new int[this.saveList.size()];
        for (int i = 0; i < masks.length; i++) {
            try {
                ISaveHandler handler = this.mc.getSaveLoader().getSaveLoader(this.getSaveFileName(i), false);
                WorldInfo worldInfo = handler.loadWorldInfo();
                handler.flush();
                masks[i] = Challenges.getMask(worldInfo);
            } catch (Exception e) {
                masks[i] = 0;
            }
        }
        this.masks = masks;
    }

    @Override public boolean nineChallenges$hasChallenges(int i) {
        return nineChallenges$getChallengeMask(i) != 0;
    }

    @Override public int nineChallenges$getChallengeMask(int i) {
        return i >= 0 && i < masks.length ? masks[i] : 0;
    }

    @Override public int nineChallenges$getMouseX() {
        return mouseX;
    }
    @Override public int nineChallenges$getMouseY() {
        return mouseY;
    }
    @Override public void nineChallenges$setHoveredWorld(int i) {
        hoveredWorld = i;
    }

    // stores mouse and resets hover state
    @Inject(method = "drawScreen", at = @At("HEAD"))
    private void drawScreen_StoreInitialVariables(int mouseX, int mouseY, float pt, CallbackInfo ci) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.hoveredWorld = -1;
    }

    // tooltip render over everything
    @Inject(method = "drawScreen", at = @At("TAIL"))
    private void drawScreen_DrawTooltip(int mouseX, int mouseY, float pt, CallbackInfo ci) {
        int mask = nineChallenges$getChallengeMask(hoveredWorld);
        if (hoveredWorld < 0 || mask == 0) return;

        int iconSide = 16;
        int rowHeight = iconSide + 2;
        int maxTextWidth = 0;
        int challengeCount = 0;
        for (Challenge challenge : Challenge.values()) {
            if (!challenge.isIn(mask)) continue;
            maxTextWidth = Math.max(maxTextWidth, this.fontRenderer.getStringWidth(challenge.getTitle()));
            challengeCount++;
        }

        int width = 4 + iconSide + 4 + maxTextWidth + 4;
        int height = 4 + challengeCount * rowHeight + 2;
        int x = mouseX + 12;
        int y = mouseY - 4;
        if (x + width > this.width) x = mouseX - width - 8;
        if (y + height > this.height) y = this.height - height;

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawGradientRect(x - 3, y - 4, x + width + 3, y + height + 4, tooltipOutlineColor, tooltipOutlineColor);
        this.drawGradientRect(x - 4, y - 3, x + width + 4, y + height + 3, tooltipOutlineColor, tooltipOutlineColor);

        this.drawGradientRect(x - 3, y - 3, x - 3 + 1, y + height + 3, tooltipBorderColorTop, tooltipBorderColorBottom);
        this.drawGradientRect(x + width + 2, y - 3, x + width + 3, y + height + 3, tooltipBorderColorTop, tooltipBorderColorBottom);
        this.drawGradientRect(x - 3, y - 3, x + width + 3, y - 3 + 1, tooltipBorderColorTop, tooltipBorderColorTop);
        this.drawGradientRect(x - 3, y + height + 2, x + width + 3, y + height + 3, tooltipBorderColorBottom, tooltipBorderColorBottom);

        this.drawGradientRect(x - 2, y - 2, x + width + 2, y + height + 2, tooltipOutlineColor, tooltipOutlineColor);

        int row = 0;
        for (Challenge challenge : Challenge.values()) {
            if (!challenge.isIn(mask)) continue;
            int ry = y + 4 + row * rowHeight;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GuiUtil.draw(challenge.icon, x + 4, ry, iconSide, iconSide);
            this.fontRenderer.drawStringWithShadow(challenge.getTitle(), x + iconSide + 8, ry + 3, 0xFFFFFF);
            row++;
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

}
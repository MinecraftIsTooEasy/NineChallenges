package vbonedra.nine_challenges.mixin;

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

    @Override public boolean nineChallanges$hasChallenges(int i) {
        return nineChallanges$getChallengeMask(i) != 0;
    }

    @Override public int nineChallanges$getChallengeMask(int i) {
        return i >= 0 && i < masks.length ? masks[i] : 0;
    }

    @Override public int nineChallanges$getMouseX() {
        return mouseX;
    }
    @Override public int nineChallanges$getMouseY() {
        return mouseY;
    }
    @Override public void nineChallanges$setHoveredWorld(int i) {
        hoveredWorld = i;
    }

    // stores mouse and resets hover state
    @Inject(method = "drawScreen", at = @At("HEAD"))
    private void drawScreen_StoreInitialVariables(int mx, int my, float pt, CallbackInfo ci) {
        this.mouseX = mx;
        this.mouseY = my;
        this.hoveredWorld = -1;
    }

    // tooltip render over everything
    @Inject(method = "drawScreen", at = @At("TAIL"))
    private void drawScreen_DrawTooltip(int mx, int my, float pt, CallbackInfo ci) {
        int mask = nineChallanges$getChallengeMask(hoveredWorld);
        if (hoveredWorld < 0 || mask == 0) return;

        int iconSide = 16;
        int rowHeight = iconSide + 2;
        int maxTextWidth = 0;
        int challengeCount = 0;
        for (Challenge challenge : Challenge.values()) {
            if (!challenge.isIn(mask)) continue;
            maxTextWidth = Math.max(maxTextWidth, this.fontRenderer.getStringWidth(challenge.name));
            challengeCount++;
        }

        int width = 4 + iconSide + 4 + maxTextWidth + 4;
        int height = 4 + challengeCount * rowHeight + 2;
        int x = mx + 12;
        int y = my - 4;
        if (x + width > this.width) x = mx - width - 8;
        if (y + height > this.height) y = this.height - height;

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        drawRect(x - 1, y - 1, x + width + 1, y + height + 1, 0xF0100010);
        drawRect(x, y, x + width, y + height, 0xF0200030);

        int row = 0;
        for (Challenge challenge : Challenge.values()) {
            if (!challenge.isIn(mask)) continue;
            int ry = y + 4 + row * rowHeight;
            GuiUtil.draw(challenge.icon, x + 4, ry, iconSide, iconSide);
            this.fontRenderer.drawStringWithShadow(challenge.name, x + iconSide + 8, ry + 4, 0xFFFFFF);
            row++;
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
}
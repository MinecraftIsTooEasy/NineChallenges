package vbonedra.nine_challenges.mixin.gui;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.WorldListChallenges;
import vbonedra.nine_challenges.gui.GuiUtil;

import static vbonedra.nine_challenges.challenge.Challenge.countChallenges;
import static vbonedra.nine_challenges.gui.GrailButton.getGrailDifficultyIcon;

@Mixin(GuiWorldSlot.class)
public abstract class GuiWorldSlotMixin {

    @Unique private static final int grailIconSize = 16;
    @Unique private static final int gapSize = 2;

    @Final @Shadow GuiSelectWorld parentWorldGui;

    // grail before the world name
    @Inject(method = "drawSlot", at = @At("HEAD"))
    private void drawSlot_DrawGrail(int index, int x, int y, int height, Tessellator t, CallbackInfo ci) {
        WorldListChallenges list = (WorldListChallenges) (Object) this.parentWorldGui;
        GuiUtil.draw(getGrailDifficultyIcon(list.nineChallenges$getChallengeMask(index)), x - grailIconSize - gapSize, y, grailIconSize, grailIconSize);
    }

    // icons of active challenges on 4th line in world info + hover detection
    @Inject(method = "drawSlot", at = @At("TAIL"))
    private void drawSlot_DrawChallengeCountAndCheckGrailHovered(int index, int x, int y, int height, Tessellator t, CallbackInfo ci) {
        WorldListChallenges list = (WorldListChallenges) (Object) this.parentWorldGui;
        int challengeMask = list.nineChallenges$getChallengeMask(index);
        if (challengeMask == 0) return;

        FontRenderer font = ((GuiSelectWorld) this.parentWorldGui).fontRenderer;

        String line = countChallenges(challengeMask) + "/" + Challenge.values().length;
        int lineX = x - gapSize - font.getStringWidth(line);
        int lineY = y + grailIconSize + gapSize;
        font.drawStringWithShadow(line, lineX, lineY, 0xFFFFFF);

        int mouseX = list.nineChallenges$getMouseX();
        int mouseY = list.nineChallenges$getMouseY();
        if (mouseX <= x - gapSize && mouseX > x - grailIconSize - gapSize
                && mouseY >= y && mouseY < y + grailIconSize) {
            list.nineChallenges$setHoveredWorld(index);
        }
    }


}
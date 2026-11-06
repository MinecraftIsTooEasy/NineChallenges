package vbonedra.nine_challenges.mixin.gui;

import net.minecraft.GuiSelectWorld;
import net.minecraft.GuiWorldSlot;
import net.minecraft.Tessellator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.WorldListChallenges;
import vbonedra.nine_challenges.gui.GrailButton;
import vbonedra.nine_challenges.gui.GuiUtil;

@Mixin(GuiWorldSlot.class)
public abstract class GuiWorldSlotMixin {

    @Unique private static final int iconSize = 16;
    @Unique private static final int gapSize = 2;
    @Unique private static final int lineOffsetSize = 34;

    @Final @Shadow GuiSelectWorld parentWorldGui;

    // vanilla passes 36 as the slot height to super()
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 36))
    private static int init_AddSpaceForIcons(int original) {
        return 36 + iconSize + gapSize;
    }

    // grail before the world name
    @Inject(method = "drawSlot", at = @At("HEAD"))
    private void drawSlot_DrawGrail(int index, int x, int y, int height, Tessellator t, CallbackInfo ci) {
        WorldListChallenges list = (WorldListChallenges) (Object) parentWorldGui;
        GuiUtil.draw(list.nineChallenges$hasChallenges(index) ? GrailButton.stateOn : GrailButton.stateOff, x + 2, y, 16, 16);
    }

    // shift the three vanilla text lines right to make room for the grail
    @ModifyVariable(method = "drawSlot", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    private int drawSlot_ShiftText(int x) {
        return x + iconSize + gapSize;
    }

    // icons of active challenges on 4th line in world info + hover detection
    @Inject(method = "drawSlot", at = @At("TAIL"))
    private void drawSlot_DrawChallengeIcons(int index, int x, int y, int height, Tessellator t, CallbackInfo ci) {
        WorldListChallenges list = (WorldListChallenges) (Object) parentWorldGui;
        int challengeMask = list.nineChallenges$getChallengeMask(index);
        if (challengeMask == 0) return;

        int ix = x;
        int iy = y + lineOffsetSize;
        int count = 0;
        for (Challenge challenge : Challenge.values()) {
            if (!challenge.isIn(challengeMask)) continue;
            GuiUtil.draw(challenge.icon, ix + count * (iconSize + gapSize), iy, iconSize, iconSize);
            count++;
        }

        int mx = list.nineChallenges$getMouseX(), my = list.nineChallenges$getMouseY();
        boolean inListArea = my > 32 && my < parentWorldGui.height - 64; // slot list clip area
        int rowW = count * (iconSize + gapSize) - gapSize;
        if (inListArea && mx >= ix && mx < ix + rowW && my >= iy && my < iy + iconSize) {
            list.nineChallenges$setHoveredWorld(index);
        }
    }
}
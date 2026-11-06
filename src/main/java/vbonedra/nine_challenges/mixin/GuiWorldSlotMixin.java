package vbonedra.nine_challenges.mixin;

import net.minecraft.GuiSelectWorld;
import net.minecraft.GuiWorldSlot;
import net.minecraft.Tessellator;
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

    @Shadow(aliases = {"parentWorldGui", "field_77232_a"})
    private GuiSelectWorld parentWorldGui;

    // vanilla passes 36 as the slot height to super()
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 36))
    private static int slotHeight(int original) {
        return 36 + iconSize;
    }

    // grail before the world name
    @Inject(method = "drawSlot", at = @At("HEAD"))
    private void drawSlot_DrawGrail(int index, int x, int y, int height, Tessellator t, CallbackInfo ci) {
        WorldListChallenges list = (WorldListChallenges) (Object) parentWorldGui;
        GuiUtil.draw(list.nineChallanges$hasChallenges(index) ? GrailButton.stateOn : GrailButton.stateOff, x + 2, y, 16, 16);
    }

    // shift the three vanilla text lines right to make room for the grail
    @ModifyVariable(method = "drawSlot", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    private int drawSlot_ShiftText(int x) {
        return x + 18;
    }

    // icons of active challenges on 4th line in world info + hover detection
    @Inject(method = "drawSlot", at = @At("TAIL"))
    private void drawSlot_DrawChallengeIcons(int index, int x, int y, int height, Tessellator t, CallbackInfo ci) {
        WorldListChallenges list = (WorldListChallenges) (Object) parentWorldGui;
        int mask = list.nineChallanges$getChallengeMask(index);
        if (mask == 0) return;

        int ix = x, iy = y + lineOffsetSize, count = 0;
        for (Challenge c : Challenge.values()) {
            if (!c.isIn(mask)) continue;
            GuiUtil.draw(c.icon, ix + count * (iconSize + gapSize), iy, iconSize, iconSize);
            count++;
        }

        int mx = list.nineChallanges$getMouseX(), my = list.nineChallanges$getMouseY();
        boolean inListArea = my > 32 && my < parentWorldGui.height - 64; // slot list clip area
        int rowW = count * (iconSize + gapSize) - gapSize;
        if (inListArea && mx >= ix && mx < ix + rowW && my >= iy && my < iy + iconSize) {
            list.nineChallanges$setHoveredWorld(index);
        }
    }
}
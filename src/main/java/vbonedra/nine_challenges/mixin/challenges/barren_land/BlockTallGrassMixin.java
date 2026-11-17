package vbonedra.nine_challenges.mixin.challenges.barren_land;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(BlockTallGrass.class)
public abstract class BlockTallGrassMixin {

    @Inject(method = "dropBlockAsEntityItem(Lnet/minecraft/BlockBreakInfo;)I", at = @At("HEAD"), cancellable = true)
    private void dropBlockAsEntityItem_DropOnlyWhenMinedByEffectiveTool(BlockBreakInfo info, CallbackInfoReturnable<Integer> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            if (info.wasSelfDropped() || info.wasNotLegal()) {
                return;
            }

            ItemStack stack = info.getHarvesterItemStack();
            Item item = stack == null ? null : stack.getItem();
            Block block = (Block) (Object) this;

            if (!(item instanceof ItemTool itemTool && itemTool.isEffectiveAgainstBlock(block, info.getMetadata()))) {
                cir.setReturnValue(0);
            }
        }
    }

    @ModifyConstant(method = "dropBlockAsEntityItem(Lnet/minecraft/BlockBreakInfo;)I", constant = @Constant(floatValue = 0.2F))
    private float dropBlockAsEntityItem_LowerDropChance(float original) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            return original / 2F;
        }
        return original;
    }
}
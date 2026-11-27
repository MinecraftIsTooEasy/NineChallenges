package vbonedra.nine_challenges.mixin.challenges.barren_land;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(BlockLeaves.class)
public abstract class BlockLeavesMixin {

    @Unique private static final ThreadLocal<BlockBreakInfo> currentInfo = new ThreadLocal<>();

    @Unique private static boolean isFruit(int itemId) {
        return itemId == Item.appleRed.itemID || itemId == Item.orange.itemID || itemId == Item.banana.itemID;
    }

    @Inject(method = "dropBlockAsEntityItem(Lnet/minecraft/BlockBreakInfo;)I", at = @At("HEAD"))
    private void dropBlockAsEntityItem_SaveBlockBreakInfo(BlockBreakInfo blockBreakInfo, CallbackInfoReturnable<Integer> cir) {
        currentInfo.set(blockBreakInfo);
    }

    @Inject(method = "dropBlockAsEntityItem(Lnet/minecraft/BlockBreakInfo;)I", at = @At("RETURN"))
    private void dropBlockAsEntityItem_ClearBlockBreakInfo(BlockBreakInfo blockBreakInfo, CallbackInfoReturnable<Integer> cir) {
        currentInfo.remove();
    }

    @ModifyArg(
            method = "dropBlockAsEntityItem(Lnet/minecraft/BlockBreakInfo;)I",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockLeaves;dropBlockAsEntityItem(Lnet/minecraft/BlockBreakInfo;IIIF)I"),
            index = 1
    )
    private int dropBlockAsEntityItem_FruitOnlyWithEffectiveTool(int itemId) {
        if (!Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) return itemId;
        if (!isFruit(itemId)) return itemId;

        BlockBreakInfo blockBreakInfo = currentInfo.get();
        if (blockBreakInfo != null && blockBreakInfo.wasHarvestedByPlayer()) {
            ItemStack itemStack = blockBreakInfo.getHarvesterItemStack();

            if (itemStack != null && itemStack.getItem() instanceof ItemTool itemTool) {
                if (itemTool.isEffectiveAgainstBlock((Block) (Object) this, blockBreakInfo.getMetadata())) {
                    return itemId;
                }
            }
        }
        return Item.stick.itemID;
    }

    @ModifyArg(
            method = "updateTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockLeaves;dropBlockAsEntityItem(Lnet/minecraft/BlockBreakInfo;Lnet/minecraft/Item;)I"),
            index = 1
    )
    private Item updateTick_NoRandomFruits(Item item) {
        if (!Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) return item;
        return (item == Item.appleRed || item == Item.orange || item == Item.banana) ? Item.stick : item;
    }
}
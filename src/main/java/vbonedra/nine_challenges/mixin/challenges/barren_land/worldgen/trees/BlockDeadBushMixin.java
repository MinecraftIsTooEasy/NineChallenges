package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.Block;
import net.minecraft.BlockDeadBush;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(BlockDeadBush.class)
public class BlockDeadBushMixin {

    @Inject(method = "isLegalOn", at = @At("TAIL"), cancellable = true)
    public void isLegalOn_DeadBushOnDirt(int metadata, Block block_below, int block_below_metadata, CallbackInfoReturnable<Boolean> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            if (metadata == 0) {
                if (block_below == Block.dirt || block_below == Block.grass) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}

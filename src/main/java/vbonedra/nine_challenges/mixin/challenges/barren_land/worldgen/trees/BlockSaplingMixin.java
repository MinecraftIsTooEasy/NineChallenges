package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.Block;
import net.minecraft.BlockSapling;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

import java.util.Random;

@Mixin(BlockSapling.class)
public class BlockSaplingMixin {

    @Inject(method = "growTree", at = @At("HEAD"), cancellable = true)
    public void growTree_GrowDeadBush(World par1World, int par2, int par3, int par4, Random par5Random, CallbackInfo ci) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            par1World.setBlock(par2, par3, par4, Block.deadBush.blockID);
            ci.cancel();
        }
    }

}

package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldGenHugeTrees;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

import java.util.Random;

@Mixin(WorldGenHugeTrees.class)
public abstract class WorldGenHugeTreesMixin {

    @Unique Random savedRandom;

    @Unique private int layerX;
    @Unique private int layerZ;

    @Inject(method = "growLeaves", at = @At("HEAD"))
    private void genTreeLayer_SaveTrunk(World par1World, int par2, int par3, int par4, int par5, Random par6Random, CallbackInfo ci) {
        if (!Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) return;
        this.layerX = par2;
        this.layerZ = par3;
        this.savedRandom = par6Random;
    }

    @Redirect(
            method = "growLeaves",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldGenHugeTrees;setBlockAndMetadata(Lnet/minecraft/World;IIIII)V")
    )
    private void genTreeLayer_ThinnerLeaves(WorldGenHugeTrees worldGenHugeTrees, World world, int x, int y, int z, int id, int meta) {
        if (!Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) return;
        if (this.savedRandom == null) return;

        if (id == Block.leaves.blockID) {
            int dist = Math.max(Math.abs(x - this.layerX), Math.abs(z - this.layerZ));
            if (dist > 1) {
                if (this.savedRandom.nextFloat() < 0.25F + 0.25F * (dist - 2)) return;
            }
        }

        worldGenHugeTrees.setBlockAndMetadata(world, x, y, z, id, meta);
    }
}
package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldGenBigTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

import java.util.Random;

@Mixin(WorldGenBigTree.class)
public abstract class WorldGenBigTreeMixin {

    @Shadow Random rand;

    @Unique private int posX;
    @Unique private int posZ;

    @Inject(method = "genTreeLayer(IIIFBI)V", at = @At("HEAD"))
    private void genTreeLayer_SaveTrunk(int x, int y, int z, float radius, byte axis, int id, CallbackInfo ci) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            posX = x;
            posZ = z;
        }
    }

    @Redirect(
            method = "genTreeLayer(IIIFBI)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldGenBigTree;setBlockAndMetadata(Lnet/minecraft/World;IIIII)V")
    )
    private void genTreeLayer_ThinnerLeaves(WorldGenBigTree self, World world, int x, int y, int z, int id, int meta) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            if (id == Block.leaves.blockID) {
                int dist = Math.max(Math.abs(x - posX), Math.abs(z - posZ));
                if (dist > 1) {
                    float skipChance = 0.25F + 0.25F * (dist - 2);
                    if (rand.nextFloat() < skipChance) return;
                }
            }
        }
        self.setBlockAndMetadata(world, x, y, z, id, meta);
    }
}
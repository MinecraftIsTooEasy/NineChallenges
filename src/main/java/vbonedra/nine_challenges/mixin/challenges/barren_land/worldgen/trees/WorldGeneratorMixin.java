package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.util.TreeTrunk;

@Mixin(WorldGenerator.class)
public abstract class WorldGeneratorMixin {

    @Inject(method = "setBlockAndMetadata", at = @At("HEAD"), cancellable = true)
    private void setBlockAndMetadata_LeafRadius(World world, int x, int y, int z, int id, int meta, CallbackInfo ci) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            if (id == Block.leaves.blockID) {
                int[] t = TreeTrunk.TRUNK.get();
                if (t[2] == 1 && (Math.abs(x - t[0]) > 1 || Math.abs(z - t[1]) > 1)) {
                    ci.cancel();
                }
            }
        }
    }
}
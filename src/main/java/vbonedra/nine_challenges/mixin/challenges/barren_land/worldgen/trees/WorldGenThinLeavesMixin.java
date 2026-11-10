package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.WorldGenForest;
import net.minecraft.WorldGenSwamp;
import net.minecraft.WorldGenTrees;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.util.LeafThinning;

import java.util.Random;

@Mixin({WorldGenTrees.class, WorldGenForest.class, WorldGenSwamp.class})
public abstract class WorldGenThinLeavesMixin {

    @Inject(method = "generate", at = @At("RETURN"))
    private void generate_ThinLeaves(World world, Random rand, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            if (cir.getReturnValueZ()) {
                LeafThinning.thin(world, x, y, z);
            }
        }
    }
}
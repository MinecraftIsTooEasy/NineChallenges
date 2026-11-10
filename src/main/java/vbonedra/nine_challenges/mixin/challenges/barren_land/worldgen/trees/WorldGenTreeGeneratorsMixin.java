package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.util.TreeTrunk;

import java.util.Random;

@Mixin({WorldGenTrees.class, WorldGenForest.class, WorldGenSwamp.class})
public abstract class WorldGenTreeGeneratorsMixin {

    @Inject(method = "generate", at = @At("HEAD"))
    private void generate_SaveTrunk(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            int[] t = TreeTrunk.TRUNK.get();
            t[0] = x;
            t[1] = z;
            t[2] = 1;
        }
    }

    @Inject(method = "generate", at = @At("RETURN"))
    private void generate_ClearTrunk(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            TreeTrunk.TRUNK.get()[2] = 0;
        }
    }

}
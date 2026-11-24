package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.World;
import net.minecraft.WorldGenHugeTrees;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

import java.util.Random;

@Mixin(WorldGenHugeTrees.class)
public abstract class WorldGenHugeTreesMixin {

    @Inject(method = "growLeaves", at = @At("HEAD"), cancellable = true)
    private void growLeaves_ThinnerLeaves(World par1World, int par2, int par3, int par4, int par5, Random par6Random, CallbackInfo ci) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            ci.cancel();
        }
    }

}
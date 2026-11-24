package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.WorldGenBigTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(WorldGenBigTree.class)
public abstract class WorldGenBigTreeMixin {


    @Inject(method = "genTreeLayer", at = @At("HEAD"), cancellable = true)
    private void genTreeLayer_ThinnerLeaves(int par1, int par2, int par3, float par4, byte par5, int par6, CallbackInfo ci) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            ci.cancel();
        }
    }
}
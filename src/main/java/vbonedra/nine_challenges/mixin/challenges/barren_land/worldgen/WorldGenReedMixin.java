package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen;

import net.minecraft.World;
import net.minecraft.WorldGenReed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

import java.util.Random;

@Mixin(WorldGenReed.class)
public class WorldGenReedMixin {

    @Inject(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/World;setBlock(IIIIII)Z", shift = At.Shift.AFTER), cancellable = true)
    private void generate_OneSugarcane(World par1World, Random par2Random, int par3, int par4, int par5, CallbackInfoReturnable<Boolean> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            cir.setReturnValue(true);
        }
    }

}

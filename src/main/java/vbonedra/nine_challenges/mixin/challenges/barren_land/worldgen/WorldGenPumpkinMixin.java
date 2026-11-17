package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen;

import net.minecraft.World;
import net.minecraft.WorldGenPumpkin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

import java.util.Random;

@Mixin(WorldGenPumpkin.class)
public class WorldGenPumpkinMixin {

    @Inject(method = "generate", at = @At(value = "HEAD"), cancellable = true)
    private void generate_OnePumpkin(World par1World, Random par2Random, int par3, int par4, int par5, CallbackInfoReturnable<Boolean> ci) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            ci.cancel();
        }
    }

}

package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldGenFlowers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

import java.util.Random;

@Mixin(WorldGenFlowers.class)
public class WorldGenFlowersMixin {

    @Unique private boolean shouldConvert = false;
    @Unique private Random internalRand;

    @Inject(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/WorldGenFlowers;block:Lnet/minecraft/Block;", ordinal = 0))
    private void generate_SaveRandom(World par1World, Random par2Random, int par3, int par4, int par5, CallbackInfoReturnable<Boolean> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            this.internalRand = par2Random;
        }
    }

    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/Block;canOccurAt(Lnet/minecraft/World;IIII)Z"))
    private boolean generate_RedirectCanOccurAt(Block instance, World world, int x, int y, int z, int metadata) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            this.shouldConvert = this.internalRand != null && this.internalRand.nextFloat() < 0.75F;
            if (this.shouldConvert) {
                return Block.tallGrass.canOccurAt(world, x, y, z, 0);
            }
        }
        return instance.canOccurAt(world, x, y, z, metadata);

    }

    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/World;setBlock(IIIIII)Z"))
    private boolean generate_RedirectSetBlock(World world, int x, int y, int z, int blockId, int metadata, int flag) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            if (this.shouldConvert) {
                return world.setBlock(x, y, z, Block.tallGrass.blockID, 0, flag);
            }
        }
        return world.setBlock(x, y, z, blockId, metadata, flag);
    }
}

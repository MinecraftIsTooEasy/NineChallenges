package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen;

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

    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/World;setBlock(IIIIII)Z"))
    private boolean generate_FlowersToDeadBushes(World world, int x, int y, int z, int blockId, int metadata, int flag) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND) && Block.deadBush.canOccurAt(world, x, y, z, 0)) {
            return world.setBlock(x, y, z, Block.deadBush.blockID, 0, flag);
        }
        return world.setBlock(x, y, z, blockId, metadata, flag);
    }

}

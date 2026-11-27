package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen;

import net.minecraft.Block;
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

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void generate_OnePumpkinRestSand(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (!Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) return;

        int[][] placed = new int[64][];
        int count = 0;

        for (int i = 0; i < 64; ++i) {
            int px = x + random.nextInt(8) - random.nextInt(8);
            int py = y + random.nextInt(4) - random.nextInt(4);
            int pz = z + random.nextInt(8) - random.nextInt(8);
            int meta = random.nextInt(4);

            if (world.isAirBlock(px, py, pz)
                    && world.getBlockId(px, py - 1, pz) == Block.grass.blockID
                    && Block.pumpkin.canOccurAt(world, px, py, pz, meta)
            ) {
                world.setBlock(px, py, pz, Block.sand.blockID, 0, 2);
                placed[count++] = new int[]{px, py, pz, meta};
            }
        }

        if (count > 0) {
            int[] chosen = placed[random.nextInt(count)];
            world.setBlock(chosen[0], chosen[1], chosen[2], Block.pumpkin.blockID, chosen[3], 2);
        }

        cir.setReturnValue(true);
        cir.cancel();
    }

}
package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.trees;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin({WorldGenTrees.class, WorldGenForest.class, WorldGenSwamp.class})
public abstract class WorldGenThinLeavesMixin {

    @Unique private static final float CHANCE = 0.5F;
    @Unique private static final int[][] DIRS = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};

    @Inject(method = "generate", at = @At("RETURN"))
    private void generate_ThinLeaves(World world, Random rand, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            if (cir.getReturnValueZ()) {
                thin(world, x, y, z);
            }
        }
    }

    @Unique private static void thin(World world, int cx, int cy, int cz) {
        int leaves = Block.leaves.blockID;
        List<int[]> remove = new ArrayList<>();

        for (int y = cy; y <= Math.min(cy + 16, 255); y++) {
            for (int x = cx - 3; x <= cx + 3; x++) {
                for (int z = cz - 3; z <= cz + 3; z++) {
                    if (world.getBlockId(x, y, z) != leaves) continue;

                    int leafN = 0, airN = 0;
                    for (int[] d : DIRS) {
                        int id = world.getBlockId(x + d[0], y + d[1], z + d[2]);
                        if (id == leaves) leafN++;
                        else if (id == 0) airN++;
                    }
                    if (leafN == 3 && airN == 3) {
                        Random r = new Random(x * 3129871L ^ z * 116129781L ^ y);
                        if (r.nextFloat() < CHANCE) remove.add(new int[]{x, y, z});
                    }
                }
            }
        }
        for (int[] p : remove) {
            world.setBlock(p[0], p[1], p[2], 0, 0, 2);
        }
    }

}
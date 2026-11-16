package vbonedra.nine_challenges.mixin.challenges.badder_bosses.dragon;

import net.minecraft.Block;
import net.minecraft.EntityEnderCrystal;
import net.minecraft.World;
import net.minecraft.WorldGenSpikes;
import net.minecraft.WorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.EndPillarGenHelper;

import java.util.Random;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(WorldGenSpikes.class)
public abstract class WorldGenSpikesMixin extends WorldGenerator {

    @Inject(method = "generate", at = @At("RETURN"))
    private void generate_MidCrystal(World world, Random random, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.BADDER_BOSSES)) return;
        if (!cir.getReturnValueZ()) return;
        if (random.nextFloat() <= 0.25F && !EndPillarGenHelper.generateSecondCrystal) return;

        int topY = j;
        while (topY < 128 && world.getBlockId(i, topY, k) == Block.obsidian.blockID) {
            topY++;
        }
        int pillarHeight = topY - j;
        if (pillarHeight < 6) return;

        int crystalY = j + pillarHeight / 2 + random.nextInt(5) - 3;
        crystalY = Math.max(j + 3, Math.min(topY - 6, crystalY));

        int radius = 0;
        for (int r = 1; r <= 4; r++) {
            if (world.getBlockId(i + r, crystalY, k) == Block.obsidian.blockID) {
                radius = r;
            } else {
                break;
            }
        }
        if (radius < 3) return;

        for (int dy = 0; dy <= 3; dy++) {
            int by = crystalY + dy;
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (Math.abs(dx) >= 2 && Math.abs(dz) >= 2) continue;
                    int bx = i + dx;
                    int bz = k + dz;
                    if (world.getBlockId(bx, by, bz) == Block.obsidian.blockID) {
                        world.setBlockToAir(bx, by, bz);
                    }
                }
            }
        }

        world.setBlock(i, crystalY, k, Block.netherrack.blockID, 0, 2);

        EntityEnderCrystal crystal = new EntityEnderCrystal(world);
        crystal.setLocationAndAngles(i + 0.5, crystalY, k + 0.5, random.nextFloat() * 360.0F, 0.0F);
        world.spawnEntityInWorld(crystal);
    }

    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/World;setBlock(IIIIII)Z"))
    private boolean generate_BedrockToNetherrack(World world, int x, int y, int z, int blockId, int metadata, int flags) {
        if (isChallengeEnabled(Challenge.BADDER_BOSSES) && blockId == Block.bedrock.blockID) {
            return world.setBlock(x, y, z, Block.netherrack.blockID, metadata, flags);
        }
        return world.setBlock(x, y, z, blockId, metadata, flags);
    }
}
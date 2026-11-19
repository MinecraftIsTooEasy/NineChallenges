package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.waterless;

import net.minecraft.Block;
import net.minecraft.ChunkProviderUnderworld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(ChunkProviderUnderworld.class)
public abstract class ChunkProviderUnderworldMixin {

    @Inject(method = "generateNetherTerrain", at = @At("RETURN"), remap = false)
    private void generateNetherTerrain_RemoveTopWater(int chunkX, int chunkZ, byte[] blocks, CallbackInfo ci) {
        if (!Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) return;

        byte waterId = (byte) Block.waterStill.blockID;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int columnBase = (x << 11) | (z << 7);
                for (int y = 0; y < 128; y++) {
                    int index = columnBase | y;
                    if (blocks[index] == waterId) {
                        if (y == 127 || blocks[index + 1] != waterId) {
                            blocks[index] = 0;
                        }
                    }
                }
            }
        }
    }

}

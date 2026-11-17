package vbonedra.nine_challenges.mixin.challenges.barren_land;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(BlockStem.class)
public abstract class BlockStemMixin {

    @Redirect(method = "updateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/World;setBlockMetadataWithNotify(IIIII)Z"))
    private boolean updateTick_GrowWhenFertilized(World world, int x, int y, int z, int newMeta, int flags) {
        if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) {
            BlockStem self = (BlockStem) (Object) this;

            if (world.getBlockId(x, y, z) == self.blockID) {
                int oldMeta = world.getBlockMetadata(x, y, z);
                if (BlockStem.getGrowth(newMeta) > BlockStem.getGrowth(oldMeta)) {
                    if (world.getBlockId(x, y - 1, z) != Block.tilledField.blockID) {
                        return false;
                    }
                    int farmMeta = world.getBlockMetadata(x, y - 1, z);
                    if (!BlockFarmland.isFertilized(farmMeta)) {
                        return false;
                    }

                    boolean result = world.setBlockMetadataWithNotify(x, y, z, newMeta, flags);
                    world.setBlockMetadataWithNotify(x, y - 1, z, BlockFarmland.setFertilized(farmMeta, false), 2);
                    return result;
                }
            }
        }
        return world.setBlockMetadataWithNotify(x, y, z, newMeta, flags);
    }

}
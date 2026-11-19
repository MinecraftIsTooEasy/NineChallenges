    package vbonedra.nine_challenges.mixin.challenges.barren_land.worldgen.waterless;

    import net.minecraft.Block;
    import net.minecraft.World;
    import net.minecraft.WorldGenLakes;
    import org.spongepowered.asm.mixin.Mixin;
    import org.spongepowered.asm.mixin.Shadow;
    import org.spongepowered.asm.mixin.Unique;
    import org.spongepowered.asm.mixin.injection.At;
    import org.spongepowered.asm.mixin.injection.Inject;
    import org.spongepowered.asm.mixin.injection.Redirect;
    import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
    import vbonedra.nine_challenges.challenge.Challenge;
    import vbonedra.nine_challenges.challenge.Challenges;

    import java.util.HashSet;
    import java.util.Random;
    import java.util.Set;

    @Mixin(WorldGenLakes.class)
    public abstract class WorldGenLakesMixin {

        @Shadow private int blockIndex;

        @Unique private final ThreadLocal<Set<Long>> waters = ThreadLocal.withInitial(HashSet::new);

        @Inject(method = "generate", at = @At("HEAD"))
        private void head_ResetWaters(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
            this.waters.get().clear();
        }


        @Redirect(
                method = "generate",
                at = @At(value = "INVOKE", target = "Lnet/minecraft/World;setBlock(IIIIII)Z", ordinal = 0)
        )
        private boolean fill_UpdateWaters(World world, int x, int y, int z, int id, int meta, int flags) {
            if (Challenges.isChallengeEnabled(Challenge.BARREN_LAND)
                    && this.blockIndex == Block.waterStill.blockID
                    && id == Block.waterStill.blockID) {
                this.waters.get().add(setPos(x, y, z));
            }
            return world.setBlock(x, y, z, id, meta, flags);
        }

        @Inject(method = "generate", at = @At("RETURN"))
        private void return_RemoveTopLayer(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
            if (!Challenges.isChallengeEnabled(Challenge.BARREN_LAND)) return;
            if (this.waters.get().isEmpty()) return;

            if (!cir.getReturnValueZ()) {
                for (long key : this.waters.get()) {
                    world.setBlock(getX(key), getY(key), getZ(key), 0, 0, 3);
                }
                this.waters.get().clear();
                return;
            }

            for (long key : this.waters.get()) {
                int px = getX(key);
                int py = getY(key);
                int pz = getZ(key);
                long aboveKey = setPos(px, py + 1, pz);
                if (!this.waters.get().contains(aboveKey)) {
                    world.setBlock(px, py, pz, 0, 0, 3);
                }
            }
            this.waters.get().clear();
        }

        @Unique
        private static long setPos(int x, int y, int z) {
            return ((long) (x & 0x3FFFFFF))
                    | ((long) (z & 0x3FFFFFF) << 26)
                    | ((long) (y & 0xFFF) << 52);
        }

        @Unique
        private static int getX(long key) {
            int x = (int) (key & 0x3FFFFFF);
            if ((x & 0x2000000) != 0) x |= 0xFC000000;
            return x;
        }

        @Unique
        private static int getZ(long key) {
            int z = (int) ((key >> 26) & 0x3FFFFFF);
            if ((z & 0x2000000) != 0) z |= 0xFC000000;
            return z;
        }

        @Unique
        private static int getY(long key) {
            int y = (int) ((key >> 52) & 0xFFF);
            if ((y & 0x800) != 0) y |= 0xFFFFF000;
            return y;
        }
    }
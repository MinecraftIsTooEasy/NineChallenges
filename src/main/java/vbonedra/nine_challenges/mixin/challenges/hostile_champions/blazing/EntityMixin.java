package vbonedra.nine_challenges.mixin.challenges.hostile_champions.blazing;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public World worldObj;
    @Shadow public double posX;
    @Shadow public double posY;
    @Shadow public double posZ;
    @Shadow public float height;
    @Shadow public boolean isDead;
    @Shadow protected Random rand;
    @Shadow public abstract boolean isWet();

    @Shadow public abstract EntityLivingBase getAsEntityLivingBase();

    @Inject(method = "setDead()V", at = @At("HEAD"))
    private void setDead_BlazingDeath(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this.isDead) return;
        if (this.worldObj == null || this.worldObj.isWorldClient()) return;

        if ((this instanceof IHostileChampionType iHostileChampionType)) {
            if (iHostileChampionType.NC$getHostileChampionType() != HostileChampion.Blazing) return;

            // safe to do since only instanceof EntityLivingBase has IHostileChampionTyp checked before
            if (this.getAsEntityLivingBase().getHealth() > 0.0F) return;

            if (this.isWet()) {
                this.worldObj.playSoundEffect(
                        this.posX, this.posY, this.posZ,
                        "random.fizz",
                        0.5F,
                        2.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8F
                );
            } else {
                spawnFireBurst(HostileChampion.championBlazingExplosionSize, HostileChampion.championBlazingExplosionFireChance);
            }
        }
    }

    @Unique
    private boolean isPassable(int x, int y, int z) {
        int id = this.worldObj.getBlockId(x, y, z);
        if (id == 0) return true;
        Block block = Block.blocksList[id];
        return block != null
                && !this.worldObj.isBlockSolid(block, x, y, z)
                && (block instanceof BlockPlant || block == Block.snow);
    }

    @Unique
    private void spawnFireBurst(float size, float fireChance) {
        final int gridResolution = 16;
        final double originX = this.posX;
        final double originY = this.posY + this.height * 0.5D;
        final double originZ = this.posZ;

        Set<ChunkPosition> candidatePositions = new HashSet<>();

        for (int i = 0; i < gridResolution; i++) {
            for (int j = 0; j < gridResolution; j++) {
                for (int k = 0; k < gridResolution; k++) {
                    if (i != 0 && i != gridResolution - 1
                            && j != 0 && j != gridResolution - 1
                            && k != 0 && k != gridResolution - 1) continue;

                    double dirX = (double) i / (gridResolution - 1.0D) * 2.0D - 1.0D;
                    double dirY = (double) j / (gridResolution - 1.0D) * 2.0D - 1.0D;
                    double dirZ = (double) k / (gridResolution - 1.0D) * 2.0D - 1.0D;
                    double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
                    dirX /= length;
                    dirY /= length;
                    dirZ /= length;

                    float rayEnergy = size * (0.7F + this.rand.nextFloat() * 0.6F);
                    double curX = originX;
                    double curY = originY;
                    double curZ = originZ;

                    for (float step = 0.3F; rayEnergy > 0.0F; rayEnergy -= step * 0.75F) {
                        int blockX = MathHelper.floor_double(curX);
                        int blockY = MathHelper.floor_double(curY);
                        int blockZ = MathHelper.floor_double(curZ);

                        if (!isPassable(blockX, blockY, blockZ)) break;

                        int below = this.worldObj.getBlockId(blockX, blockY - 1, blockZ);
                        if (below > 0 && Block.opaqueCubeLookup[below]) {
                            candidatePositions.add(new ChunkPosition(blockX, blockY, blockZ));
                        }

                        curX += dirX * step;
                        curY += dirY * step;
                        curZ += dirZ * step;
                    }
                }
            }
        }

        for (ChunkPosition pos : candidatePositions) {
            if (this.rand.nextFloat() <= fireChance && isPassable(pos.x, pos.y, pos.z)) {
                this.worldObj.setBlock(pos.x, pos.y, pos.z, Block.fire.blockID, 0, 2);
            }
        }

        this.worldObj.playSoundEffect(
                this.posX, this.posY, this.posZ,
                "mob.ghast.fireball",
                1.0F,
                1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F
        );
    }

    @Inject(method = "isImmuneTo(Lnet/minecraft/DamageSource;)Z", at = @At("RETURN"), cancellable = true)
    private void isImmuneTo_Blazing(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this instanceof IHostileChampionType iHostileChampionType && iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Antimagic) {
            if (damageSource.hasMagicAspect()) {
                if (damageSource.isArrowDamage()) {
                    EntityArrow arrow = (EntityArrow) damageSource.getImmediateEntity();
                    if (arrow.getLauncher() == null || !arrow.getLauncher().hasEnchantment(Enchantment.flame, true)) {
                        cir.setReturnValue(false);
                    }
                } else {
                    ItemStack item_stack = damageSource.getItemAttackedWith();
                    if (item_stack == null || !item_stack.hasEnchantment(Enchantment.fireAspect, true)) {
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }

}
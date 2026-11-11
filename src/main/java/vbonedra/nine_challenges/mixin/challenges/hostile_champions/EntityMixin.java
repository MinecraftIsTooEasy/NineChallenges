package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampionType;
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
    @Shadow protected Random rand;
    @Shadow public abstract boolean isWet();

    @Inject(method = "isImmuneTo(Lnet/minecraft/DamageSource;)Z", at = @At("RETURN"), cancellable = true)
    private void isImmuneTo_BlessedAspects(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this instanceof IHostileChampionType iHostileChampionType) {
            if (iHostileChampionType.NC$getHostileChampionType() == HostileChampionType.Blessed) {
                if (!cir.getReturnValue()
                        && damageSource.getResponsibleEntity() != null
                        && !(damageSource.hasMagicAspect() || damageSource.hasSilverAspect())
                ) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("RETURN"))
    private void attackEntityFrom_BlazingIgnites(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        EntityDamageResult result = cir.getReturnValue();
        if (result == null || !result.entityLostHealth()) return;

        Entity attacker = damage.getResponsibleEntity();
        if (attacker instanceof IHostileChampionType attackerIHostileChampionType) {
            if (attackerIHostileChampionType.NC$getHostileChampionType() == HostileChampionType.Blazing) {
                if (!attacker.isWet()) {
                    ((Entity) (Object) this).setFire(5);
                    damage.setAmount(damage.getAmount() * 1.25f);
                }
            } else if (attackerIHostileChampionType.NC$getHostileChampionType() == HostileChampionType.Projecting) {
                damage.setAmount(damage.getAmount() * 1.25f);
            }
        }

    }



    @Inject(method = "setDead", at = @At("HEAD"))
    private void onDeath(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (!this.worldObj.isRemote) {
            if (this instanceof IHostileChampionType iHostileChampionType) {
                if (iHostileChampionType.NC$getHostileChampionType() == HostileChampionType.Blazing) {
                    if (!this.isWet()) {
                        spawnFireBurst(1.0F, 1F);
                    }
                }
            }
        }
    }

    @Inject(method = "canCatchFire()Z", at = @At("HEAD"), cancellable = true)
    private void canCatchFire_BlazingNoFire(CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this instanceof IHostileChampionType iHostileChampionType) {
            if (iHostileChampionType.NC$getHostileChampionType() == HostileChampionType.Blazing) {
                cir.setReturnValue(false);
            }
        }
    }
    @Inject(method = "isHarmedByFire()Z", at = @At("HEAD"), cancellable = true)
    private void isHarmedByFire_BlazingNoFire(CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this instanceof IHostileChampionType iHostileChampionType) {
            if (iHostileChampionType.NC$getHostileChampionType() == HostileChampionType.Blazing) {
                cir.setReturnValue(false);
            }
        }
    }
    @Inject(method = "isHarmedByLava()Z", at = @At("HEAD"), cancellable = true)
    private void isHarmedByLava_BlazingNoFire(CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this instanceof IHostileChampionType iHostileChampionType) {
            if (iHostileChampionType.NC$getHostileChampionType() == HostileChampionType.Blazing) {
                cir.setReturnValue(false);
            }
        }
    }

    @SuppressWarnings("ConstantValue")
    @Unique
    private void spawnFireBurst(float size, float fireChance) {
        if (this.worldObj.isRemote) {
            return;
        }
        int gridResolution = 16;
        Set<ChunkPosition> candidatePositions = new HashSet<>();
        for (int i = 0; i < gridResolution; i++) {
            for (int j = 0; j < gridResolution; j++) {
                for (int k = 0; k < gridResolution; k++) {
                    double directionX = (float) i / ((float) gridResolution - 1.0F) * 2.0F - 1.0F;
                    double directionY = (float) j / ((float) gridResolution - 1.0F) * 2.0F - 1.0F;
                    double directionZ = (float) k / ((float) gridResolution - 1.0F) * 2.0F - 1.0F;

                    double length = Math.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
                    directionX /= length;
                    directionY /= length;
                    directionZ /= length;

                    float rayEnergy = size * (0.7F + this.rand.nextFloat() * 0.6F);

                    double curX = this.posX;
                    double curY = this.posY + (double) (((Entity) (Object) this).height * 0.5F);
                    double curZ = this.posZ;

                    int prevBlockX = MathHelper.floor_double(curX);
                    int prevBlockY = MathHelper.floor_double(curY);
                    int prevBlockZ = MathHelper.floor_double(curZ);

                    for (float step = 0.3F; rayEnergy > 0.0F; rayEnergy -= step * 0.75F) {

                        int blockX = MathHelper.floor_double(curX);
                        int blockY = MathHelper.floor_double(curY);
                        int blockZ = MathHelper.floor_double(curZ);

                        int blockId = this.worldObj.getBlockId(blockX, blockY, blockZ);

                        if (blockId > 0) {
                            if (this.worldObj.getBlockId(prevBlockX, prevBlockY, prevBlockZ) == 0
                                    && Block.fire.canBePlacedAt(this.worldObj, prevBlockX, prevBlockY, prevBlockZ, 0)) {
                                candidatePositions.add(new ChunkPosition(prevBlockX, prevBlockY, prevBlockZ));
                            }
                            break;
                        }


                        prevBlockX = blockX;
                        prevBlockY = blockY;
                        prevBlockZ = blockZ;

                        curX += directionX * (double) step;
                        curY += directionY * (double) step;
                        curZ += directionZ * (double) step;
                    }
                }
            }
        }
        for (ChunkPosition pos : candidatePositions) {
            if (this.rand.nextFloat() <= fireChance) {
                this.worldObj.setBlock(pos.x, pos.y, pos.z, Block.fire.blockID);
            }
        }
        this.worldObj.playSoundEffect(
                this.posX, this.posY, this.posZ,
                "mob.ghast.fireball",
                1.0F,
                1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F
        );
    }

}

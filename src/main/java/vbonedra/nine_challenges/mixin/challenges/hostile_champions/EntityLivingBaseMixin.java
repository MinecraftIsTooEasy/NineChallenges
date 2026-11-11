package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampionType;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_LOGGER;
import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;


@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity implements IHostileChampionType {

    @Unique private byte hostileChampionType = -1;
    @Override public byte NC$getHostileChampionType() {
        return this.hostileChampionType;
    }
    @Override public void NC$setHostileChampionType(byte type) {
        if (this.isEntityPlayer()) {
            NINE_CHALLENGES_LOGGER.warn("Why set hostileChampionType for player?");
            return;
        }
        if ((Object) this instanceof EntityDragon) {
            NINE_CHALLENGES_LOGGER.warn("Why set hostileChampionType for boss EntityDragon?");
            return;
        }
        if ((Object) this instanceof EntityWither) {
            NINE_CHALLENGES_LOGGER.warn("Why set hostileChampionType for boss EntityWither?");
            return;
        }
        this.hostileChampionType = type;
    }

    public EntityLivingBaseMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = "applyEntityAttributes()V", at = @At("HEAD"))
    private void applyEntityAttributes_HostileChampionTypeInitialization(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.NC$getHostileChampionType() == HostileChampionType.Unset) {
            this.NC$setHostileChampionType(HostileChampionType.Vanilla);
            if (this.isEntityPlayer()
                    || (Object) this instanceof EntityDragon
                    || (Object) this instanceof EntityWither
            ) return;
            World world = this.worldObj;
            if (world != null && world.isWorldServer()) {
                if (this.rand.nextFloat() < 0.1 || true) {
                    byte hostileChampionType = HostileChampionType.getRandomChampionType(this.rand);
                    if (hostileChampionType == HostileChampionType.Giant) {
                        if (!hasSpaceToGrow(world)) { // TODO: check if spawns
                            hostileChampionType = HostileChampionType.getRandomChampionTypeWithoutSpawnCheck(this.rand);
                        }
                    }
                    this.NC$setHostileChampionType(hostileChampionType);
                }
            }
            this.NC$setHostileChampionType(HostileChampionType.Blazing);
        }
    }

    @ModifyVariable(method = "setEntityAttribute(Lnet/minecraft/Attribute;D)Lnet/minecraft/AttributeInstance;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private double setEntityAttribute(double original, Attribute attribute) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return original;
        byte hostileChampionType = this.NC$getHostileChampionType();

        if (attribute == SharedMonsterAttributes.maxHealth) {
            if (hostileChampionType == HostileChampionType.Giant) {
                return original * 5;
            }
            if (hostileChampionType == HostileChampionType.Antimagic) {
                return original * 2;
            }
        }

        return original;
    }

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void attackEntityFrom_AttackEffects(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        Entity attacker = damage.getResponsibleEntity();
        if (attacker instanceof IHostileChampionType championAttacker) {
            byte type = championAttacker.NC$getHostileChampionType();
            if (type == HostileChampionType.Blazing) {
                if (!this.isWet()) {
                    damage.setAmount(damage.getAmount() * 1.25F);
                }
            }
            else if (type == HostileChampionType.Projecting) {
                damage.setAmount(damage.getAmount() * 1.25F);
            }
        }
    }

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("RETURN"))
    private void attackEntityFrom_Ignite(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        EntityDamageResult result = cir.getReturnValue();

        if (result == null || !result.entityLostHealth()) return;
        if (damage.getResponsibleEntity() instanceof IHostileChampionType iHostileChampionType
                && iHostileChampionType.NC$getHostileChampionType() == HostileChampionType.Blazing
                && !this.worldObj.isRemote
        ) {
            if (this.isWet()) {
                this.worldObj.playSoundEffect(
                        this.posX, this.posY, this.posZ,
                        "random.fizz",
                        0.5F,
                        2.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8F
                );
            } else {
                this.setFire(5);
            }
        }
    }

    @Inject(method = "canCatchFire()Z", at = @At("HEAD"), cancellable = true)
    private void canCatchFire_BlazingNoFire(CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.NC$getHostileChampionType() == HostileChampionType.Blazing) {
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "isHarmedByFire()Z", at = @At("HEAD"), cancellable = true)
    private void isHarmedByFire_BlazingNoFire(CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.NC$getHostileChampionType() == HostileChampionType.Blazing) {
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "isHarmedByLava()Z", at = @At("HEAD"), cancellable = true)
    private void isHarmedByLava_BlazingNoFire(CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.NC$getHostileChampionType() == HostileChampionType.Blazing) {
            cir.setReturnValue(false);
        }
    }

//    @Inject(method = "getNaturalDefense(Lnet/minecraft/DamageSource;)F", at = @At("RETURN"), remap = false)
//    private void getNaturalDefense(DamageSource damage_source, CallbackInfoReturnable<Float> cir) {
//        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
//    }

    @ModifyConstant(method = "onLivingUpdate()V", constant = @Constant(floatValue = 0.1F))
    private float onLivingUpdate_ModifyRegenPercentage(float original) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return original;
        byte hostileChampionType = this.NC$getHostileChampionType();

        if (hostileChampionType == HostileChampionType.Blessed) {
            return original * 4;
        }

        return original;
    }

    @Inject(method = "writeEntityToNBT(Lnet/minecraft/NBTTagCompound;)V", at = @At("RETURN"))
    private void writeEntityToNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        par1NBTTagCompound.setByte("NCHostileChampionType", this.NC$getHostileChampionType());
    }

    @Inject(method = "readEntityFromNBT(Lnet/minecraft/NBTTagCompound;)V", at = @At("RETURN"))
    private void readEntityFromNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (par1NBTTagCompound.hasKey("NCHostileChampionType")) {
            this.NC$setHostileChampionType(par1NBTTagCompound.getByte("NCHostileChampionType"));
        }
    }

    @Unique private boolean hasSpaceToGrow(World world) {
        // TODO: dangerous, probably reason it doesn't spawn if it doesn't, caused log spam cause chunk wasn't loaded, might be fixed by chunk check but still requires caution
        if (!world.getChunkProvider().chunkExists(this.getChunkPosX(), this.getChunkPosZ())) return false;

        double halfWidth = this.width;
        double height = this.height * 2.0D;

        AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(
                this.posX - halfWidth, this.posY, this.posZ - halfWidth,
                this.posX + halfWidth, this.posY + height, this.posZ + halfWidth);

        return world.getCollidingBoundingBoxes(this, box).isEmpty();
    }

}

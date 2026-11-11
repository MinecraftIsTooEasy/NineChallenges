package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampionType;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;


@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity implements IHostileChampionType {

    @Unique private byte hostileChampionType = -1;
    @Override public byte NC$getHostileChampionType() {
        return this.hostileChampionType;
    }
    @Override public void NC$setHostileChampionType(byte type) {
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
            World world = this.worldObj;
            if (world != null && world.isWorldServer()) {
                // TODO: dangerous, caused log spam cause chunk wasn't loaded, might be fixed by chunk check but still requires caution

                if (this.rand.nextFloat() < 0.1 || true) {
                    this.NC$setHostileChampionType(HostileChampionType.getRandomChampionType(this.rand));
                    if (this.NC$getHostileChampionType() == HostileChampionType.Giant) {
                        if (!hasSpaceToGrow(world)) {
                            this.NC$setHostileChampionType(HostileChampionType.getRandomChampionTypeWithoutSpawnCheck(this.rand));
                        }
                    }

                }
            }
//            this.NC$setHostileChampionType(HostileChampionType.Blazing);
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
        if (!world.getChunkProvider().chunkExists(this.getChunkPosX(), this.getChunkPosZ())) return false;

        double halfWidth = this.width;
        double height = this.height * 2.0D;

        AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(
                this.posX - halfWidth, this.posY, this.posZ - halfWidth,
                this.posX + halfWidth, this.posY + height, this.posZ + halfWidth);

        return world.getCollidingBoundingBoxes(this, box).isEmpty();
    }

}

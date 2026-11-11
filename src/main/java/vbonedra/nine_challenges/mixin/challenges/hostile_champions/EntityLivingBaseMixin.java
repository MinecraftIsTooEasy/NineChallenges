package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.util.HostileChampionTypeData;
import vbonedra.nine_challenges.util.IHostileChampionType;

import java.util.List;


@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity implements IHostileChampionType {

    @Unique private byte hostileChampionType = 0;
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
    private void applyEntityAttributes_hostileChampionTypeInitialization(CallbackInfo ci) {
//        if (this.hostileChampionType == hostileChampionTypeUnset) {
//            // TODO: add canXray data
//            this.hostileChampionType = hostileChampionTypeVanilla;
//            World world = this.worldObj;
//            Object entity = this;
//            if (world != null && world.isWorldServer()) {
//                int difficulty = get_difficulty_level(world);
//                // TODO: dangerous, caused log spam cause chunk wasn't loaded, might be fixed by chunk check but still requires caution
//                boolean isLoaded = world.getChunkProvider().chunkExists(this.getChunkPosX(), this.getChunkPosZ());
//                double chanceLurkers = isLoaded && world.canBlockSeeTheSky(this.getBlockPosX(), this.getBlockPosY(), this.getBlockPosZ()) ? 0.05 : world.isOverworld() ? 0.1 : 0.2;
//                if (entity instanceof EntityPhaseSpider) {
//                    if (this.rand.nextFloat() < difficulty * chanceLurkers * 0.5) {
//                        this.hostileChampionType = CelestialArachnid.WARP.id;
//                    }
//                }
//                else if (entity instanceof EntityCreeper) {
////                    if (entity.getClass().getSimpleName().equals("EntityCreeper")) {
////                        if (this.rand.nextFloat() < difficulty * 0.2) {
////                            this.hostileChampionType = CelestialCreeper_Fire;
////                        }
////                    }
//                    if (this.rand.nextFloat() < difficulty * 0.2) {
//                        this.hostileChampionType = addHostileChampionType(this.hostileChampionType, CelestialCreeper.MIMIC.id);
//                    }
//                }
//                else if (entity instanceof EntitySkeleton) {
//                    if (this.rand.nextFloat() < (difficulty - (world.isOverworld() ? 1 : 0)) * chanceLurkers) {
//                        this.hostileChampionType = CelestialSkeleton.WITHERED.id;
//                    }
//                }
//                else if (entity instanceof EntityZombie) {
//                    if (true || this.rand.nextFloat() < (difficulty + 1) * chanceLurkers * 0.5) {
//                        this.hostileChampionType = addHostileChampionType(this.hostileChampionType, CelestialZombie.PHASE.id);
//                    }
//                    if (true || this.rand.nextFloat() < difficulty * chanceLurkers) {
//                        this.hostileChampionType = addHostileChampionType(this.hostileChampionType, CelestialZombie.PLAGUE.id);
//                        this.celestialSubtype = (byte) this.rand.nextInt(256);
//                    }
//                }
//                else if (entity instanceof EntityGhoul) {
//                    if (this.rand.nextFloat() < (difficulty + 1) * chanceLurkers) {
//                        this.hostileChampionType = CelestialGhoul.VAMPIRIC.id;
//                    }
//                }
//                else if (entity instanceof EntityShadow) {
//                    if (this.rand.nextFloat() < (difficulty + 1) * 0.1) {
//                        this.hostileChampionType = CelestialShadow.GLOOM.id;
//                    }
//                    if (this.rand.nextFloat() < difficulty * 0.1) {
//                        this.hostileChampionType = CelestialShadow.SPECTRAL.id;
//                    }
//                }
//                else if (entity instanceof EntitySquid) {
//                    if (this.rand.nextFloat() < (difficulty + 1) * 0.1) {
//                        this.hostileChampionType = CelestialSquid.GLOW.id;
//                    }
//                }
//                else if (entity instanceof EntityInvisibleStalker) {
//                    if (entity.getClass().getSimpleName().equals("EntityInvisibleStalker")) {
//                        if (this.rand.nextFloat() < difficulty * 0.05) {
//                            this.hostileChampionType = CelestialInvisibleStalker.MIRROR.id;
//                        }
//                    }
//                }
//
//            }
//        }
    }

    @ModifyVariable(method = "setEntityAttribute(Lnet/minecraft/Attribute;D)Lnet/minecraft/AttributeInstance;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private double setEntityAttribute(double value, Attribute attribute) {
//        Object entity = this;
//        int hostileChampionType = this.NC$getHostileChampionType();
//
//        if (attribute == SharedMonsterAttributes.maxHealth) {
//            if (entity instanceof EntitySkeleton) {
//                if (hasHostileChampionType(hostileChampionType, CelestialSkeleton.WITHERED.id)) return value * 2.0D;
//            }
//        }
        return value;
    }


    @Inject(method = "getExperienceValue()I", at = @At("RETURN"), cancellable = true, remap = false)
    private void getExperienceValue(CallbackInfoReturnable<Integer> cir) {
//        Object entity = this;
//        int hostileChampionType = this.NC$getHostileChampionType();
//
//        if (entity instanceof EntitySkeleton) {
//            if (hostileChampionType != hostileChampionTypeVanilla) cir.setReturnValue(cir.getReturnValue() * 2);
//        }
//        else if (entity instanceof EntityZombie) {
//            if (hasHostileChampionType(hostileChampionType, CelestialZombie.PHASE.id)) cir.setReturnValue(cir.getReturnValue() * 2);
//            if (hasHostileChampionType(hostileChampionType, CelestialZombie.PLAGUE.id)) cir.setReturnValue(cir.getReturnValue() * 2);
//        }
//        else if (entity instanceof EntityCreeper) {
//            if (hasHostileChampionType(hostileChampionType, CelestialCreeper.MIMIC.id)) cir.setReturnValue(cir.getReturnValue() * 2);
//        }
//        else if (entity instanceof EntityGhoul) {
//            if (hasHostileChampionType(hostileChampionType, CelestialGhoul.VAMPIRIC.id)) cir.setReturnValue(cir.getReturnValue() * 2);
//        }
//        else if (entity instanceof EntityShadow) {
//            if (hasHostileChampionType(hostileChampionType, CelestialShadow.GLOOM.id)) cir.setReturnValue(cir.getReturnValue() * 2);
//            if (hasHostileChampionType(hostileChampionType, CelestialShadow.SPECTRAL.id)) cir.setReturnValue(cir.getReturnValue() * 2);
//        }
//        else if (entity instanceof EntitySquid) {
//            if (hasHostileChampionType(hostileChampionType, CelestialSquid.GLOW.id)) cir.setReturnValue(cir.getReturnValue() * 2);
//        }
//        else if (entity instanceof EntityInvisibleStalker) {
//            if (hasHostileChampionType(hostileChampionType, CelestialInvisibleStalker.MIRROR.id)) cir.setReturnValue(cir.getReturnValue() * 2);
//        }

    }

    @Inject(method = "getNaturalDefense(Lnet/minecraft/DamageSource;)F", at = @At("RETURN"), cancellable = true, remap = false)
    private void getNaturalDefense(DamageSource damage_source, CallbackInfoReturnable<Float> cir) {
//        Object entity = this;
//        int hostileChampionType = this.NC$getHostileChampionType();
//
//        if (entity instanceof EntityIronGolem) {
//            if (hostileChampionType >= 0) {
//                if (damage_source.bypassesMundaneArmor()) {
//                    cir.setReturnValue(cir.getReturnValue() + IronGolemBlockType.getNaturalDefenseForBlockId(hostileChampionType));
//                }
//            }
//        }
//        if (entity instanceof EntityGhoul) {
//            if (hasHostileChampionType(hostileChampionType, CelestialGhoul.VAMPIRIC.id)) cir.setReturnValue(cir.getReturnValue() + 4);
//        }
//        else if (entity instanceof EntitySilverfish) {
//            if (hostileChampionType > 0) {
//                Block block = Block.getBlock(hostileChampionType);
//                if (block != null) {
//                    cir.setReturnValue(cir.getReturnValue() + Math.min(0f, block.getBlockHardness(0)));
//                }
//            }
//        }
    }

    @ModifyConstant(method = "onLivingUpdate()V", constant = @Constant(floatValue = 0.1F))
    private float modifyRegenPercentage(float original) {
//        Object entity = this;
//        int hostileChampionType = this.NC$getHostileChampionType();
//
//        if (entity instanceof EntityIronGolem) {
//            if (hostileChampionType >= 0) {
//                if (IronGolemBlockType.isValidGolemBlock(hostileChampionType)) {
//                    return IronGolemBlockType.getRegenPercentageForBlockId(hostileChampionType);
//                }
//            }
//        }

        return original;
    }

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("RETURN"))
    private void attackEntityFrom(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
//        EntityDamageResult result = cir.getReturnValue();
//        if (result != null && result.entityLostHealth() && !result.entityWasDestroyed()) {
//            Entity attacker = damage.getResponsibleEntity();
//            if (attacker instanceof EntityZombie && attacker instanceof IHostileChampionType) {
//                int hostileChampionType = ((IHostileChampionType) attacker).NC$getHostileChampionType();
//                if (hasHostileChampionType(hostileChampionType, CelestialZombie.PLAGUE.id)) {
//                    EntityLivingBase victim = (EntityLivingBase) (Object) this;
//                    victim.addPotionEffect(new PotionEffect(Potion.poison.id, 480, 0));
//                }
//            }
//        }
    }

    @Inject(method = "writeEntityToNBT(Lnet/minecraft/NBTTagCompound;)V", at = @At("RETURN"))
    private void writeEntityToNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
        par1NBTTagCompound.setInteger("NCHostileChampionType", this.NC$getHostileChampionType());
    }

    @Inject(method = "readEntityFromNBT(Lnet/minecraft/NBTTagCompound;)V", at = @At("RETURN"))
    private void readEntityFromNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
        if (par1NBTTagCompound.hasKey("NCHostileChampionType")) {
            this.NC$setHostileChampionType(par1NBTTagCompound.getByte("NCHostileChampionType"));
        }
    }


    @Inject(method = "onDeath(Lnet/minecraft/DamageSource;)V", at = @At("HEAD"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
//        if (!this.worldObj.isRemote) {
//            if ((Object) this instanceof EntityWither) {
//                double sqRadius = 16384D;
//                List<?> playersNearby = this.worldObj.playerEntities;
//                for (Object obj : playersNearby) {
//                    if (obj instanceof EntityPlayer player) {
//                        double dX = player.posX - this.posX;
//                        double dY = player.posY - this.posY;
//                        double dZ = player.posZ - this.posZ;
//                        if ((dX * dX + dY * dY + dZ * dZ) <= sqRadius) {
//                            if (AchievementExtend.killWither != null) {
//                                player.addStat(AchievementExtend.killWither, 1);
//                            }
//                            if (AchievementExtend.legendaryMode != null) {
//                                player.addStat(AchievementExtend.legendaryMode, 1);
//                            }
//                        }
//                    }
//                }
//            }
//            if ((Object) this instanceof EntityDragon) {
//                double sqRadius = 16384D;
//                List<?> playersNearby = this.worldObj.playerEntities;
//                for (Object obj : playersNearby) {
//                    if (obj instanceof EntityPlayer player) {
//                        double dX = player.posX - this.posX;
//                        double dY = player.posY - this.posY;
//                        double dZ = player.posZ - this.posZ;
//                        if ((dX * dX + dY * dY + dZ * dZ) <= sqRadius) {
//                            if (AchievementExtend.endgameMode != null) {
//                                player.addStat(AchievementExtend.endgameMode, 1);
//                            }
//                        }
//                    }
//                }
//            }
//            if ((Object) this instanceof EntityInvisibleStalker) {
//                if (this.NC$getHostileChampionType() == CelestialInvisibleStalker.MIRROR.id) {
//                    for (int slot = 0; slot <= 4; slot++) {
//                        this.setCurrentItemOrArmor(slot, null);
//                    }
//                }
//            }
//        }
    }


}

package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.*;

import java.util.Set;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity implements IHostileChampionType, IGiantHitboxHelper {

    @Shadow protected abstract boolean isAIEnabled();

    @Unique private byte hostileChampionType = -1;
    @Override public byte NC$getHostileChampionType() {
        return this.hostileChampionType;
    }
    @Override public void NC$setHostileChampionType(byte type) {
        this.hostileChampionType = type;
        if (type == HostileChampion.Giant) this.NC$refreshGiantHitbox();
    }

    public EntityLivingBaseMixin(World world) {
        super(world);
    }

    // TODO: EntityBoneLord and EntityCube must spawn entities with same type, look into HaTE
    @Inject(method = "onEntityUpdate()V", at = @At("HEAD"))
    private void onEntityUpdate_HostileChampionTypeInit(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this.NC$getHostileChampionType() == HostileChampion.Unset) {
            this.NC$setHostileChampionType(HostileChampion.Vanilla);

            if (this.worldObj == null || this.worldObj.isWorldClient()
                    || (Object) this instanceof EntityPlayer
                    || (Object) this instanceof EntityDragon
                    || (Object) this instanceof EntityWither
            ) return;

            if (this.rand.nextFloat() <= HostileChampion.spawnChance) {

                Set<Byte> possibleHostileChampionTypes = HostileChampion.getAllChampionTypes();

                if (!hasSpaceToSpawnGiant()) possibleHostileChampionTypes.remove(HostileChampion.Giant);
                if (!this.isAIEnabled()) possibleHostileChampionTypes.remove(HostileChampion.Projecting);
                if (!isImmuneTo(DamageSource.magic)) possibleHostileChampionTypes.remove(HostileChampion.Blessed);
                if (!isImmuneTo(DamageSource.generic)) possibleHostileChampionTypes.remove(HostileChampion.Antimagic);

                if (!possibleHostileChampionTypes.isEmpty()) {
                    this.NC$setHostileChampionType(possibleHostileChampionTypes.toArray(new Byte[0])[this.rand.nextInt(possibleHostileChampionTypes.size())]);
                }

            }
        }
    }


    @Inject(method = "onEntityUpdate", at = @At("HEAD"))
    private void onEntityUpdate_UpdateGiantHitbox(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this.worldObj == null || this.worldObj.isWorldServer()) return;

        if (this.hostileChampionType != HostileChampion.Giant && !HostileChampionTypeCache.clientHostileChampionTypeMap.containsKey(this.entityId)) return;

        if (HostileChampionTypeGetter.getHostileChampionType(this.getAsEntityLivingBase()) != HostileChampion.Giant) return;

        float naturalWidth = this.NC$getNaturalWidth();

        if (naturalWidth <= 0) {
            this.NC$refreshGiantHitbox();
            return;
        }

        float scale = HostileChampion.championGiantScale;
        float expectedWidth = naturalWidth * scale;
        float expectedHeight = this.NC$getNaturalHeight() * scale;

        if (Math.abs(this.width - expectedWidth) > 1.0E-4F || Math.abs(this.height - expectedHeight) > 1.0E-4F) {
            this.NC$refreshGiantHitbox();
        }
    }


    @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
    private void writeEntityToNBT(NBTTagCompound nbt, CallbackInfo ci) {
        nbt.setByte("NCHostileChampionType", this.NC$getHostileChampionType());
    }

    @Inject(method = "readEntityFromNBT", at = @At("RETURN"))
    private void readEntityFromNBT(NBTTagCompound nbt, CallbackInfo ci) {
        if (nbt.hasKey("NCHostileChampionType")) {
            this.NC$setHostileChampionType(nbt.getByte("NCHostileChampionType"));
        } else {
            System.out.println("MISSING NBT KEY: NCHostileChampionType");
        }
    }


    @Unique
    private boolean hasSpaceToSpawnGiant() {
        EntityLivingBase entityLivingBase = this.getAsEntityLivingBase();
        World world = entityLivingBase.worldObj;
        if (world == null) return false;
        int cx = entityLivingBase.getChunkPosX(), cz = entityLivingBase.getChunkPosZ();
        if (!world.getChunkProvider().chunkExists(cx, cz)) return false;
        if (!world.getChunkProvider().chunkExists(cx - 1, cz - 1)) return false;
        if (!world.getChunkProvider().chunkExists(cx + 1, cz + 1)) return false;

        double scale = HostileChampion.championGiantScale;
        double halfW = entityLivingBase.width * 0.5 * scale + 0.1;
        double h = entityLivingBase.height * scale + 0.1;
        AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(
                entityLivingBase.posX - halfW, entityLivingBase.posY,
                entityLivingBase.posZ - halfW,
                entityLivingBase.posX + halfW,
                entityLivingBase.posY + h,
                entityLivingBase.posZ + halfW
        );
        return world.getCollidingBoundingBoxes(entityLivingBase, box).isEmpty();
    }

}
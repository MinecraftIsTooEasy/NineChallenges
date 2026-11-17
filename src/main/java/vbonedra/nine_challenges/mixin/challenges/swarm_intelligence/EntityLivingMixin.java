package vbonedra.nine_challenges.mixin.challenges.swarm_intelligence;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.ISwarmIntelligence;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLiving.class)
public abstract class EntityLivingMixin extends EntityLivingBase implements ISwarmIntelligence {


    @Unique private boolean lastTargetIsPlayer = false;
    @Unique private double swarmX;
    @Unique private double swarmY;
    @Unique private double swarmZ;
    @Unique private long swarmSetTime = -1L;

    public EntityLivingMixin(World par1World) {
        super(par1World);
    }

    @Override public boolean NC$hasSwarmTarget() {
        return this.swarmSetTime < 0L;
    }
    @Override public double NC$getSwarmX() {
        return this.swarmX;
    }
    @Override public double NC$getSwarmY() {
        return this.swarmY;
    }
    @Override public double NC$getSwarmZ() {
        return this.swarmZ;
    }
    @Override public long NC$getSwarmSetTime() {
        return this.swarmSetTime;
    }
    @Override public void NC$setSwarmTarget(double x, double y, double z) {
        this.swarmX = x;
        this.swarmY = y;
        this.swarmZ = z;
        this.swarmSetTime = this.worldObj.getTotalWorldTime();
    }
    @Override public void NC$clearSwarmTarget() {
        this.swarmSetTime = -1L;
    }

    @Inject(method = "onLivingUpdate", at = @At("RETURN"))
    private void onLivingUpdate_SwarmShareTarget(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.SWARM_INTELLIGENCE)) return;

        EntityLiving entityLiving = (EntityLiving) (Object) this;
        if (entityLiving.worldObj == null || entityLiving.worldObj.isRemote) return;

        EntityLivingBase target = entityLiving.getTarget();

        if (target instanceof EntityPlayer && !this.lastTargetIsPlayer) {
            this.shareTarget(target);
        }
        this.lastTargetIsPlayer = target instanceof EntityPlayer;
    }

    @Unique
    private void shareTarget(Entity player) {
        EntityLiving entityLiving = (EntityLiving) (Object) this;

        for (Object entity : entityLiving.worldObj.getEntitiesWithinAABB(EntityLiving.class, entityLiving.boundingBox.expand(16, 8, 16))) {
            EntityLiving entityToShare = (EntityLiving) entity;
                if (entityToShare == entityLiving || entityToShare.isDead) continue;

                if (entityToShare instanceof ISwarmIntelligence iSwarmIntelligence) {
                    if (iSwarmIntelligence.NC$hasSwarmTarget() && this.canSeeEntity(entityToShare)) {
                        iSwarmIntelligence.NC$setSwarmTarget(player.posX, player.posY, player.posZ);
                    }

            }
        }
    }
}
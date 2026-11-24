package vbonedra.nine_challenges.util.hostile_champion.swarm_intelligence;

import net.minecraft.EntityAIBase;
import net.minecraft.EntityLiving;

public class EntityAISwarmMove extends EntityAIBase {

    private final EntityLiving entity;
    private final double speed;
    private int repathCooldown;

    public EntityAISwarmMove(EntityLiving entity, double speed) {
        this.entity = entity;
        this.speed = speed;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        ISwarmIntelligence swarm = (ISwarmIntelligence) this.entity;
        if (swarm.NC$hasSwarmTarget()) return false;
        long age = this.entity.worldObj.getTotalWorldTime() - swarm.NC$getSwarmSetTime();
        if (age > 600L) {
            swarm.NC$clearSwarmTarget();
            return false;
        }
        return !this.isNearSwarmTarget(swarm);
    }

    @Override
    public boolean continueExecuting() {
        return this.shouldExecute();
    }

    @Override
    public void startExecuting() {
        ISwarmIntelligence swarm = (ISwarmIntelligence) this.entity;
        this.entity.getNavigator().tryMoveToXYZ(swarm.NC$getSwarmX(), swarm.NC$getSwarmY(), swarm.NC$getSwarmZ(), this.speed);
        this.repathCooldown = 0;
    }

    @Override
    public void updateTask() {
        if (--this.repathCooldown > 0) return;
        ISwarmIntelligence swarm = (ISwarmIntelligence) this.entity;
        this.entity.getNavigator().tryMoveToXYZ(swarm.NC$getSwarmX(), swarm.NC$getSwarmY(), swarm.NC$getSwarmZ(), this.speed);
        this.repathCooldown = 20;
    }

    @Override
    public void resetTask() {
        this.entity.getNavigator().clearPathEntity();
        ((ISwarmIntelligence) this.entity).NC$clearSwarmTarget();
    }

    private boolean isNearSwarmTarget(ISwarmIntelligence swarm) {
        double dx = this.entity.posX - swarm.NC$getSwarmX();
        double dy = this.entity.posY - swarm.NC$getSwarmY();
        double dz = this.entity.posZ - swarm.NC$getSwarmZ();
        return dx * dx + dy * dy + dz * dz < 4.0;
    }
}
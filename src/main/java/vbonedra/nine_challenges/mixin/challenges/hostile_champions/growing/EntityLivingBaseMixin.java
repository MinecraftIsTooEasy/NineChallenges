package vbonedra.nine_challenges.mixin.challenges.hostile_champions.growing;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.*;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;
import static vbonedra.nine_challenges.util.HostileChampion.*;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity implements IHostileChampionType, IGrowingChampion {

    @Shadow public abstract EntityLivingBase getAITarget();
    @Shadow public abstract boolean canSeeEntity(Entity par1Entity);
    @Shadow public abstract float getMaxHealth();
    @Shadow public abstract void setHealth(float par1);
    @Shadow public abstract float getHealth();

    @Unique private int growTicks = 0;

    public EntityLivingBaseMixin(World par1World) {
        super(par1World);
    }

    @Override public int NC$getGrowTicks() {
        return this.growTicks;
    }
    @Override public void NC$setGrowTicks(int amount) {
        int old = this.growTicks;
        this.growTicks = Math.max(championGrowingMinGrowTicks, Math.min(amount, championGrowingMaxGrowTicks));

        if (this.worldObj == null || this.worldObj.isWorldClient()) return;

        float multiplier = 1F + this.growTicks * championGrowingAdditionalEffectiveHealthPerTick;
        float baseMax = this.getMaxHealth() / multiplier;
        if (baseMax <= 0) return;

        this.setHealth(
                this.getMaxHealth() * MathHelper.clamp_float(this.getHealth() / (baseMax * (1F + old * championGrowingAdditionalEffectiveHealthPerTick)), 0F, 1F)
        );
    }

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void attackEntityFrom_Growing(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        Entity attacker = damage.getResponsibleEntity();
        if (attacker instanceof IHostileChampionType iHostileChampionType) {
            if (iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Growing) {
                if (attacker instanceof IGrowingChampion iGrowingChampion) {
                    damage.setAmount(damage.getAmount() * (1F + iGrowingChampion.NC$getGrowTicks() * championGrowingAdditionalDamagePerTick));
                    iGrowingChampion.NC$setGrowTicks(iGrowingChampion.NC$getGrowTicks() + 20);
                }
            }
        }
    }

    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    private void getMaxHealth_Growing(CallbackInfoReturnable<Float> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this.NC$getHostileChampionType() == HostileChampion.Growing) {
            cir.setReturnValue(cir.getReturnValueF() * (1F + this.NC$getGrowTicks() * championGrowingAdditionalEffectiveHealthPerTick));
        }
    }

    @Inject(method = "onEntityUpdate", at = @At("HEAD"))
    private void onEntityUpdate_GrowingTicks(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this.NC$getHostileChampionType() == HostileChampion.Growing) {
            if (this.growTicks <= 0) this.NC$setGrowTicks(championGrowingMinGrowTicks);

            EntityLivingBase target = this.getAITarget();
            if (target != null && this.canSeeEntity(target)) {
                this.NC$setGrowTicks(this.growTicks + 1);
            }
        }
    }

    @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
    private void writeEntityToNBT(NBTTagCompound nbt, CallbackInfo ci) {
        if (this.NC$getHostileChampionType() != HostileChampion.Growing) return;
        nbt.setInteger("NCHostileGrowingChampionGrowTicks", this.growTicks);
    }

    @Inject(method = "readEntityFromNBT", at = @At("RETURN"))
    private void readEntityFromNBT(NBTTagCompound nbt, CallbackInfo ci) {
        if (!nbt.hasKey("NCHostileGrowingChampionGrowTicks")) return;
        this.NC$setGrowTicks(nbt.getInteger("NCHostileGrowingChampionGrowTicks"));
    }
}
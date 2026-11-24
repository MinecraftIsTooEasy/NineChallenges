package vbonedra.nine_challenges.mixin.challenges.hostile_champions.giant;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin implements IHostileChampionType {

    @Shadow protected abstract void dropFewItems(boolean par1, DamageSource damage_source);

    @Redirect(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityLivingBase;dropFewItems(ZLnet/minecraft/DamageSource;)V"))
    public void dropFewItems_in_onDeath_GiantMoreDrop(EntityLivingBase instance, boolean par1, DamageSource damage_source) {
        this.dropFewItems(par1, damage_source);
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this.NC$getHostileChampionType() == HostileChampion.Giant) {
            for (int i = 0; i < HostileChampion.championGiantAdditionalDropAmount; i++) {
                this.dropFewItems(par1, damage_source);
            }
        }
    }

    @Unique private boolean loadedFromNbt = false;
    @Unique private boolean spawnHealApplied = false;

    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    private void getMaxHealth(CallbackInfoReturnable<Float> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.NC$getHostileChampionType() == HostileChampion.Giant) {
            cir.setReturnValue(cir.getReturnValueF() * HostileChampion.championGiantHealthMultiplier);
        }
    }

    @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
    private void writeEntityToNBT_WriteMaxHealth(NBTTagCompound nbt, CallbackInfo ci) {
        if (this.NC$getHostileChampionType() != HostileChampion.Giant) return;
        nbt.setFloat("NCHostileGiantMaxHealth", ((EntityLivingBase) (Object) this).getMaxHealth());
    }

    @Inject(method = "readEntityFromNBT", at = @At("HEAD"))
    private void readEntityFromNBT_MarkLoaded(NBTTagCompound nbt, CallbackInfo ci) {
        this.loadedFromNbt = true;
    }

    @Inject(method = "readEntityFromNBT", at = @At("RETURN"))
    private void readEntityFromNBT_RestoreHealth(NBTTagCompound nbt, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (!nbt.hasKey("NCHostileChampionType")) return;
        if (!nbt.hasKey("HealF")) return;

        if (nbt.getByte("NCHostileChampionType") == HostileChampion.Giant) {
            EntityLivingBase self = (EntityLivingBase) (Object) this;
            float baseMax = (float) self.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
            if (baseMax <= 0.0F) return;

            float currentScaledMax = baseMax * HostileChampion.championGiantHealthMultiplier;
            float savedMaxHealth = nbt.hasKey("NCHostileGiantMaxHealth") ? nbt.getFloat("NCHostileGiantMaxHealth") : baseMax;
            if (savedMaxHealth <= 0.0F) return;

            self.setHealth(Math.max(0.0F, Math.min(1.0F, nbt.getFloat("HealF") / savedMaxHealth)) * currentScaledMax, false, null);
        }
    }

    @Inject(method = "onEntityUpdate", at = @At("HEAD"))
    private void onEntityUpdate_SpawnHeal(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.spawnHealApplied) return;
        if (this.NC$getHostileChampionType() == HostileChampion.Giant) {
            EntityLivingBase self = (EntityLivingBase) (Object) this;
            if (self.worldObj == null || self.worldObj.isRemote) return;
            this.spawnHealApplied = true;
            if (!this.loadedFromNbt) {
                float max = self.getMaxHealth();
                if (self.getHealth() < max) {
                    self.setHealth(max, false, null);
                }
            }
        }
    }

}
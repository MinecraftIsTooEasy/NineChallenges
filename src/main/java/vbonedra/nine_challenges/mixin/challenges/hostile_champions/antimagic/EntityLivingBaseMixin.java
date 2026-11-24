package vbonedra.nine_challenges.mixin.challenges.hostile_champions.antimagic;

import net.minecraft.EntityLivingBase;
import net.minecraft.NBTTagCompound;
import net.minecraft.SharedMonsterAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin implements IHostileChampionType {

    @Unique private boolean loadedFromNbt = false;
    @Unique private boolean spawnHealApplied = false;

    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    private void getMaxHealth(CallbackInfoReturnable<Float> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.NC$getHostileChampionType() == HostileChampion.Antimagic) {
            cir.setReturnValue(cir.getReturnValueF() * HostileChampion.championAntimagicHealthMultiplier);
        }
    }

    @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
    private void writeEntityToNBT_WriteMaxHealth(NBTTagCompound nbt, CallbackInfo ci) {
        if (this.NC$getHostileChampionType() != HostileChampion.Antimagic) return;
        nbt.setFloat("NCHostileAntimagicMaxHealth", ((EntityLivingBase) (Object) this).getMaxHealth());
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

        if (nbt.getByte("NCHostileChampionType") == HostileChampion.Antimagic) {
            EntityLivingBase self = (EntityLivingBase) (Object) this;
            float baseMax = (float) self.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
            if (baseMax <= 0.0F) return;

            float currentScaledMax = baseMax * HostileChampion.championAntimagicHealthMultiplier;
            float savedMaxHealth = nbt.hasKey("NCHostileAntimagicMaxHealth") ? nbt.getFloat("NCHostileAntimagicMaxHealth") : baseMax;
            if (savedMaxHealth <= 0.0F) return;

            self.setHealth(Math.max(0.0F, Math.min(1.0F, nbt.getFloat("HealF") / savedMaxHealth)) * currentScaledMax, false, null);
        }
    }

    @Inject(method = "onEntityUpdate", at = @At("HEAD"))
    private void onEntityUpdate_SpawnHeal(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.spawnHealApplied) return;
        if (this.NC$getHostileChampionType() == HostileChampion.Antimagic) {
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
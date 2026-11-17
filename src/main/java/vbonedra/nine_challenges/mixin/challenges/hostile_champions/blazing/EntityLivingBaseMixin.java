package vbonedra.nine_challenges.mixin.challenges.hostile_champions.blazing;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;
import static vbonedra.nine_challenges.util.hostile_champion.HostileChampion.championBlazingFireAspectSeconds;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity implements IHostileChampionType {

    public EntityLivingBaseMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void attackEntityFrom_BlazingBuff(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        Entity attacker = damage.getResponsibleEntity();
        if (attacker instanceof IHostileChampionType iHostileChampionType) {
            if (iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Blazing) {
                if (!this.isWet() && !attacker.isWet()) {
                    damage.setAmount(damage.getAmount() * HostileChampion.championBlazingDamageMultiplier);
                } else {
                    this.worldObj.playSoundEffect(
                            this.posX, this.posY, this.posZ,
                            "random.fizz",
                            0.5F,
                            2.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8F
                    );
                }
            }
        }
    }

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("RETURN"))
    private void attackEntityFrom_BlazingIgnite(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        EntityDamageResult result = cir.getReturnValue();
        if (result == null || !result.entityLostHealth()) return;

        if (damage.getResponsibleEntity() instanceof IHostileChampionType iHostileChampionType
                && iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Blazing
                && this.worldObj.isWorldServer()
        ) {
            if (this.isWet()) {
                this.worldObj.playSoundEffect(
                        this.posX, this.posY, this.posZ,
                        "random.fizz",
                        0.5F,
                        2.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8F
                );
            } else {
                this.setFire(championBlazingFireAspectSeconds);
            }
        }
    }

    @Inject(method = "canCatchFire()Z", at = @At("HEAD"), cancellable = true)
    private void canCatchFire_BlazingImmune(CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.NC$getHostileChampionType() == HostileChampion.Blazing) {
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "isHarmedByFire()Z", at = @At("HEAD"), cancellable = true)
    private void isHarmedByFire_BlazingImmune(CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.NC$getHostileChampionType() == HostileChampion.Blazing) {
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "isHarmedByLava()Z", at = @At("HEAD"), cancellable = true)
    private void isHarmedByLava_BlazingImmune(CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (this.NC$getHostileChampionType() == HostileChampion.Blazing) {
            cir.setReturnValue(false);
        }
    }

}

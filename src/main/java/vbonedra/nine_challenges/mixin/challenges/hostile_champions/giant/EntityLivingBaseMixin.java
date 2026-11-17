package vbonedra.nine_challenges.mixin.challenges.hostile_champions.giant;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin implements IHostileChampionType {

    @Shadow
    protected abstract void dropFewItems(boolean par1, DamageSource damage_source);

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void attackEntityFrom_ResistanceGiant(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this.NC$getHostileChampionType() == HostileChampion.Giant) {
            damage.setAmount(damage.getAmount() * HostileChampion.championGiantDamageResistanceMultiplier);
        }
    }

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

}
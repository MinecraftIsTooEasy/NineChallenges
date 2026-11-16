package vbonedra.nine_challenges.mixin.challenges.hostile_champions.projecting;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;
import static vbonedra.nine_challenges.util.HostileChampion.championBlazingFireAspectSeconds;


@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void attackEntityFrom_ProjectingBuff(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        Entity attacker = damage.getResponsibleEntity();
        if (attacker instanceof IHostileChampionType iHostileChampionType) {
            if (iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Projecting) {
                damage.setAmount(damage.getAmount() * (damage.isMelee() ? HostileChampion.championProjectingDamageMultiplierMelee : HostileChampion.championProjectingDamageMultiplierRanged));
            }
        }
    }

}

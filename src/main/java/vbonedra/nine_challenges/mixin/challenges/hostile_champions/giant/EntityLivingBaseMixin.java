package vbonedra.nine_challenges.mixin.challenges.hostile_champions.giant;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.ChampionEnchantContext;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin implements IHostileChampionType {

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void attackEntityFrom_ResistanceGiant(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this.NC$getHostileChampionType() == HostileChampion.Giant) {
            damage.setAmount(damage.getAmount() * HostileChampion.championGiantDamageResistanceMultiplier);
        }
    }

}
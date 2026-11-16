package vbonedra.nine_challenges.mixin.challenges.hostile_champions.antimagic;

import net.minecraft.Damage;
import net.minecraft.EntityDamageResult;
import net.minecraft.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin implements IHostileChampionType {

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void attackEntityFrom_Resistance(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this.NC$getHostileChampionType() == HostileChampion.Antimagic) {
            damage.setAmount(damage.getAmount() * HostileChampion.championAntimagicDamageResistanceMultiplier);
        }
    }

}
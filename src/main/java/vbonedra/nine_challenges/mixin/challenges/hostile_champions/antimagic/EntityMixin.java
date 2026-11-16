package vbonedra.nine_challenges.mixin.challenges.hostile_champions.antimagic;

import net.minecraft.DamageSource;
import net.minecraft.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "isImmuneTo(Lnet/minecraft/DamageSource;)Z", at = @At("RETURN"), cancellable = true)
    private void isImmuneTo_Antimagic(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this instanceof IHostileChampionType iHostileChampionType) {
            if (iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Antimagic) {
                if (!cir.getReturnValue() && damageSource.hasMagicAspect()) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}

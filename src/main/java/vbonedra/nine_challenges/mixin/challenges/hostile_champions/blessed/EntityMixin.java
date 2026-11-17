package vbonedra.nine_challenges.mixin.challenges.hostile_champions.blessed;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "isImmuneTo(Lnet/minecraft/DamageSource;)Z", at = @At("RETURN"), cancellable = true)
    private void isImmuneTo_BlessedAspects(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        if (this instanceof IHostileChampionType iHostileChampionType) {
            if (iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Blessed) {
                if (!cir.getReturnValue()
                        && damageSource.getResponsibleEntity() != null
                        && !(damageSource.hasMagicAspect() || damageSource.hasSilverAspect())
                ) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}

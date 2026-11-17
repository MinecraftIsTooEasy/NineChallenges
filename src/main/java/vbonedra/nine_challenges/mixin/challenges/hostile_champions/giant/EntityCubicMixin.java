package vbonedra.nine_challenges.mixin.challenges.hostile_champions.giant;

import net.minecraft.EntityCubic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityCubic.class)
public abstract class EntityCubicMixin {

    @Inject(method = "getReachSquared()F", at = @At("RETURN"), cancellable = true)
    private void getReachSquared_Giant(CallbackInfoReturnable<Float> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (((IHostileChampionType) this).NC$getHostileChampionType() == HostileChampion.Giant) {
            cir.setReturnValue(cir.getReturnValueF() * HostileChampion.championProjectingReachMultiplier * HostileChampion.championProjectingReachMultiplier);
        }
    }
}
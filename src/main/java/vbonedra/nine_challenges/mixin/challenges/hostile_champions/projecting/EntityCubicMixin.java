package vbonedra.nine_challenges.mixin.challenges.hostile_champions.projecting;

import net.minecraft.EntityCubic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityCubic.class)
public abstract class EntityCubicMixin {

    @Inject(method = "getReachSquared()F", at = @At("RETURN"), cancellable = true)
    private void getReachSquared_Projecting(CallbackInfoReturnable<Float> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (((IHostileChampionType) this).NC$getHostileChampionType() == HostileChampion.Projecting) {
            cir.setReturnValue(cir.getReturnValueF() * HostileChampion.championProjectingReachMultiplier * HostileChampion.championProjectingReachMultiplier);
        }
    }
}
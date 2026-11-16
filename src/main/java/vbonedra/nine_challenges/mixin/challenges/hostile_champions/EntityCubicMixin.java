package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityCubic.class)
public abstract class EntityCubicMixin implements IHostileChampionType {

    @Redirect(method = "setDead", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityCubic;createInstance()Lnet/minecraft/EntityCubic;"))
    public EntityCubic createInstance_in_setDead_ShareChampionType(EntityCubic instance) {
        EntityCubic child = instance.createInstance();
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return child;

        if (instance instanceof IHostileChampionType iHostileChampionType) {
            ((IHostileChampionType) child).NC$setHostileChampionType(iHostileChampionType.NC$getHostileChampionType());
        }
        return child;
    }

}
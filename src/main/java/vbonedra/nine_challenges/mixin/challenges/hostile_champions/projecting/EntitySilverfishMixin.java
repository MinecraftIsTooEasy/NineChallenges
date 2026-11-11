package vbonedra.nine_challenges.mixin.challenges.hostile_champions.projecting;

import net.minecraft.EntitySilverfish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntitySilverfish.class)
public abstract class EntitySilverfishMixin {

    @ModifyConstant(method = "attackEntity(Lnet/minecraft/Entity;F)V", constant = @Constant(floatValue = 1.2F))
    private float attackEntity_Projecting(float original) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return original;
        return ((IHostileChampionType) this).NC$getHostileChampionType() == HostileChampion.Projecting ? original * HostileChampion.championProjectingReachMultiplier : original;
    }
}
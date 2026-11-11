package vbonedra.nine_challenges.mixin.challenges.hostile_champions.projecting;

import net.minecraft.EntityArachnid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityArachnid.class)
public abstract class EntityArachnidMixin {

    @ModifyConstant(method = "attackEntity(Lnet/minecraft/Entity;F)V", constant = @Constant(floatValue = 6.0F))
    private float attackEntity_ProjectingLeapMax(float original) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return original;
        return ((IHostileChampionType) this).NC$getHostileChampionType() == HostileChampion.Projecting ? 6.0F * HostileChampion.championProjectingReachMultiplier : original;
    }

    @ModifyConstant(method = "attackEntity(Lnet/minecraft/Entity;F)V", constant = {@Constant(doubleValue = 0.5D, ordinal = 0), @Constant(doubleValue = 0.5D, ordinal = 1)})
    private double attackEntity_ProjectingLeapMultiplier(double original) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return original;
        return ((IHostileChampionType) this).NC$getHostileChampionType() == HostileChampion.Projecting ? original * HostileChampion.championProjectingReachMultiplier : original;
    }
}
package vbonedra.nine_challenges.mixin.challenges.hostile_champions.blessed;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin implements IHostileChampionType {

    @ModifyVariable(method = "healByPercentage(F)V", at = @At("HEAD"), argsOnly = true)
    public float healByPercentage(float value) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return value;

        return this.NC$getHostileChampionType() == HostileChampion.Blessed ? value * HostileChampion.championBlessedRegenerationMultiplier : value;
    }

}
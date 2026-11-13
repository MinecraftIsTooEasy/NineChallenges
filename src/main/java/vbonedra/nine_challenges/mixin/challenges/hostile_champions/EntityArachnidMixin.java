package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.EntityArachnid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityArachnid.class)
public abstract class EntityArachnidMixin {

    @Unique private float getReachMultiplier() {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return 1.0F;
        byte type = ((IHostileChampionType) this).NC$getHostileChampionType();
        if (type == HostileChampion.Giant) {
            return HostileChampion.championGiantReachMultiplier;
        }
        if (type == HostileChampion.Projecting) {
            return HostileChampion.championProjectingReachMultiplier;
        }
        return 1.0F;
    }

    @ModifyConstant(method = "attackEntity(Lnet/minecraft/Entity;F)V", constant = @Constant(floatValue = 6.0F))
    private float attackEntity_ChampionLeapMax(float original) {
        return original * getReachMultiplier();
    }

    @ModifyConstant(
        method = "attackEntity(Lnet/minecraft/Entity;F)V",
        constant = {@Constant(doubleValue = 0.5D, ordinal = 0), @Constant(doubleValue = 0.5D, ordinal = 1)}
    )
    private double attackEntity_ChampionLeapMultiplier(double original) {
        return original * getReachMultiplier();
    }
    
}

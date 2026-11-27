package vbonedra.nine_challenges.mixin.challenges.into_darkness;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.EnumCreatureType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(EnumCreatureType.class)
public class EnumCreatureTypeMixin {

    @ModifyReturnValue(method = "getMaxNumberOfCreature", at = @At("RETURN"))
    private int intoDarkness$increaseMonsterMax(int original) {
        if (Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS)) {
            if ((Object) this == EnumCreatureType.monster) {
                return (int) (original * 1.5);
            }
        }
        return original;
    }
}
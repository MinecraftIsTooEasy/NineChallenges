package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.HostileChampionTypeGetter;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract EntityLivingBase getAsEntityLivingBase();

    // TODO: must work on
    //  - client
    //  - server
    //  - WAILA/HWITE
//    @Inject(method = "getEntityName", at = @At("RETURN"), cancellable = true)
//    private void getEntityName_ChampionPrefix(CallbackInfoReturnable<String> cir) {
//        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
//        if (this instanceof IHostileChampionType) {
//            cir.setReturnValue(HostileChampion.getPrefix(HostileChampionTypeGetter.getHostileChampionType(this.getAsEntityLivingBase())) + cir.getReturnValue());
//        }
//    }

}
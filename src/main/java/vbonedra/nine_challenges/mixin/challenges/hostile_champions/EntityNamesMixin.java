package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static vbonedra.nine_challenges.util.hostile_champion.HostileChampion.getNameWithPrefix;

@Mixin({Entity.class, EntityLiving.class, EntityHorse.class, EntityOcelot.class})
public abstract class EntityNamesMixin {

    @Inject(method = "getEntityName()Ljava/lang/String;", at = @At("RETURN"), cancellable = true)
    private void getEntityName_HostileChampionPrefix(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(getNameWithPrefix((Entity) (Object) this, cir.getReturnValue()));
    }

}
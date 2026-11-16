package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.llamalad7.mixinextras.sugar.Local;
import vbonedra.nine_challenges.util.IHostileChampionType;

@Mixin(EntityBoneLord.class)
public abstract class EntityBoneLordMixin implements IHostileChampionType{

    @Inject(method = "trySummonTroop(Lnet/minecraft/EntityLivingBase;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/World;spawnEntityInWorld(Lnet/minecraft/Entity;)Z"))
    private void trySummonTroop_ChampionTypeShare(EntityLivingBase target, CallbackInfoReturnable<Integer> cir, @Local EntitySkeleton skeleton) {
        if (skeleton != null) {
            ((IHostileChampionType) skeleton).NC$setHostileChampionType(this.NC$getHostileChampionType());
            skeleton.reapplyEntityAttributes();
            skeleton.setHealth(skeleton.getMaxHealth());
        }
    }
}

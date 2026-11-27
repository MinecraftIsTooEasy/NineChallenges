package vbonedra.nine_challenges.mixin.network;

import net.minecraft.Entity;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampionTypeCache;

@Mixin(World.class)
public abstract class WorldMixin {
    @Inject(method = "removeEntity(Lnet/minecraft/Entity;)V", at = @At("HEAD"))
    private void removeEntity_ClearChampionCache(Entity par1Entity, CallbackInfo ci) {
        if (par1Entity != null && par1Entity.worldObj != null && par1Entity.worldObj.isRemote) {
            HostileChampionTypeCache.remove(par1Entity.entityId);
        }
    }
}
package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.Minecraft;
import net.minecraft.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.util.HostileChampionTypeCache;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "loadWorld(Lnet/minecraft/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
    private void loadWorld_ClearCacheBeforeLoading(WorldClient par1WorldClient, String par2Str, CallbackInfo ci) {
        HostileChampionTypeCache.clear();
    }

}

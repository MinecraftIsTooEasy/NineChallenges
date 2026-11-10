package vbonedra.nine_challenges.mixin.challenges.into_darkness;

import net.minecraft.WorldProviderHell;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldProviderHell.class)
public class WorldProviderHellMixin {


    @Inject(method = "generateLightBrightnessTable", at = @At("RETURN"))
    protected void generateLightBrightnessTable_Darkness(CallbackInfo ci) {
        ((WorldProviderHell) (Object) this).lightBrightnessTable[0] = 0;
    }


}

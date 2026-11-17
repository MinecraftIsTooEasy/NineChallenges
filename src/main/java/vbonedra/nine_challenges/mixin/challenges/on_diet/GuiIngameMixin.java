package vbonedra.nine_challenges.mixin.challenges.on_diet;

import net.minecraft.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vbonedra.nine_challenges.util.OnDietGuiIngameHelper;

@Mixin(GuiIngame.class)
public class GuiIngameMixin {

    @Redirect(method = "renderGameOverlay(FZII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/GuiIngame;func_110327_a(II)V"))
    private void GuiIngame_in_renderGameOverlay_SetRenderingState(GuiIngame instance, int width, int height) {
        try {
            OnDietGuiIngameHelper.isRendering = true;
            instance.func_110327_a(width, height);
        } finally {
            OnDietGuiIngameHelper.isRendering = false;
        }
    }

}
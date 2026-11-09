package vbonedra.nine_challenges.mixin.challenges.forbidden_runes;

import net.minecraft.FontRenderer;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.util.EnchantmentToRuneHelper;

@Mixin(FontRenderer.class)
public abstract class FontRendererRuneMixin {

    @Inject(method = "getStringWidth(Ljava/lang/String;)I", at = @At("HEAD"), cancellable = true)
    private void getStringWidth_RuneWidth(String text, CallbackInfoReturnable<Integer> cir) {
        if (text != null && text.contains(EnchantmentToRuneHelper.TAG)) {
            cir.setReturnValue(Minecraft.theMinecraft.standardGalacticFontRenderer.getStringWidth(text.replace(EnchantmentToRuneHelper.TAG, "")));
        }
    }

    @Inject(method = "drawString(Ljava/lang/String;IIIZ)I", at = @At("HEAD"), cancellable = true)
    private void drawString_DrawRune(String text, int x, int y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
        if (text != null && text.contains(EnchantmentToRuneHelper.TAG)) {
            cir.setReturnValue(Minecraft.theMinecraft.standardGalacticFontRenderer.drawString(text.replace(EnchantmentToRuneHelper.TAG, ""), x, y, color, shadow));
        }
    }

}
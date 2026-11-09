package vbonedra.nine_challenges.mixin.challenges.forbidden_runes;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;

import java.util.Random;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(GuiEnchantment.class)
public abstract class GuiEnchantmentMixin extends GuiContainer {

    @Shadow private ContainerEnchantment containerEnchantment;

    public GuiEnchantmentMixin(Container par1Container) {
        super(par1Container);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked_DisableEnchanting(int par1, int par2, int par3, CallbackInfo ci) {
        if (isChallengeEnabled(Challenge.FORBIDDEN_RUNES)) {
            super.mouseClicked(par1, par2, par3);
            ci.cancel();
        }
    }

    @Inject(
            method = "drawGuiContainerBackgroundLayer(FII)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/EnchantmentNameParts;generateRandomEnchantName()Ljava/lang/String;"),
            cancellable = true
    )
    private void drawGuiContainerBackgroundLayer_RunesWithGibberish(float par1, int mouseX, int mouseY, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.FORBIDDEN_RUNES)) return;

        int posX = (this.width - this.xSize) / 2;
        int posY = (this.height - this.ySize) / 2;

        EnchantmentNameParts.instance.setRandSeed(this.containerEnchantment.nameSeed);
        Random fontRand = new Random();

        for (int entry = 0; entry < 3; entry++) {
            String runeText = EnchantmentNameParts.instance.generateRandomEnchantName();

            int level = this.containerEnchantment.enchantLevels[entry];
            int y = posY + 14 + 19 * entry;

            this.zLevel = 0.0F;
            this.mc.getTextureManager().bindTexture(GuiEnchantment.enchantingTableGuiTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (level == 0) {
                this.drawTexturedModalRect(posX + 60, y, 0, 185, 108, 19);
                continue;
            }

            this.drawTexturedModalRect(posX + 60, y, 0, 185, 108, 19);

            this.mc.standardGalacticFontRenderer.drawSplitString(
                    runeText, posX + 62, y + 2, 104, (6839882 & 16711422) >> 1);

            fontRand.setSeed(((long) this.containerEnchantment.nameSeed << 32) + entry);
            String gibberish = EnumChatFormatting.ITALIC
                    + I18n.getString("gui.enchanting.gibberish." + fontRand.nextInt(10));
            this.fontRenderer.drawSplitString(gibberish, posX + 62, y + 2, 104, 0xE0E0E0);
        }
        ci.cancel();
    }
}

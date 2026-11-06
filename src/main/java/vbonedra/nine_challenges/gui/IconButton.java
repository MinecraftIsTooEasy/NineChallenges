package vbonedra.nine_challenges.gui;

import net.minecraft.GuiButton;
import net.minecraft.Minecraft;
import net.minecraft.ResourceLocation;

/** 20x20 button with a 16x16 icon centered on it. */
public abstract class IconButton extends GuiButton {
    public IconButton(int id, int x, int y) {
        super(id, x, y, 20, 20, "");
    }

    protected abstract ResourceLocation getIcon();

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!this.drawButton) return;
        super.drawButton(mc, mouseX, mouseY);
        GuiUtil.draw(getIcon(), this.xPosition + (this.width - 16) / 2, this.yPosition + (this.height - 16) / 2, 16, 16);
    }
}
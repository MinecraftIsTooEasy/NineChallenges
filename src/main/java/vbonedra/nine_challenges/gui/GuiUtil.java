package vbonedra.nine_challenges.gui;

import net.minecraft.Minecraft;
import net.minecraft.ResourceLocation;
import net.minecraft.Tessellator;
import org.lwjgl.opengl.GL11;

public final class GuiUtil {
    private GuiUtil() {}

    public static void draw(ResourceLocation tex, int x, int y, int w, int h) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV(x,     y + h, 0, 0, 1);
        t.addVertexWithUV(x + w, y + h, 0, 1, 1);
        t.addVertexWithUV(x + w, y,     0, 1, 0);
        t.addVertexWithUV(x,     y,     0, 0, 0);
        t.draw();
        GL11.glDisable(GL11.GL_BLEND);
    }
}
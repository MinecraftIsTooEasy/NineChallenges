package vbonedra.nine_challenges.gui;

import net.minecraft.FontRenderer;
import net.minecraft.ResourceLocation;

public class DescriptionPainter {
    private final FontRenderer font;
    private final int x, width;
    private int y;

    public DescriptionPainter(FontRenderer font, int x, int y, int width) {
        this.font = font;
        this.x = x;
        this.y = y;
        this.width = width;
    }

    /** Word-wrapped text; supports § color codes. */
    public DescriptionPainter text(String s) {
        for (Object line : font.listFormattedStringToWidth(s, width)) {
            font.drawStringWithShadow((String) line, x, y, 0xFFFFFF);
            y += font.FONT_HEIGHT + 1;
        }
        return this;
    }

    /** Texture on its own line, size x size pixels. */
    public DescriptionPainter icon(ResourceLocation tex, int size) {
        GuiUtil.draw(tex, x, y, size, size);
        y += size + 2;
        return this;
    }

    public DescriptionPainter gap(int pixels) {
        y += pixels;
        return this;
    }
}
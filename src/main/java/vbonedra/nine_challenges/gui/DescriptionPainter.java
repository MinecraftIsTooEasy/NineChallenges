package vbonedra.nine_challenges.gui;

import net.minecraft.FontRenderer;
import net.minecraft.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DescriptionPainter {
    private static final Logger log = LogManager.getLogger(DescriptionPainter.class);
    private final FontRenderer font;
    private int x;
    private int width;
    private int y;

    public DescriptionPainter(FontRenderer font, int x, int y, int width) {
        this.font = font;
        this.x = x;
        this.y = y;
        this.width = width;
    }


    public void processAndDrawText(String text) {
        text = text.replace("\\n", "\n");
        String[] paragraphs = text.split("\n");

        for (String paragraph : paragraphs) {
            processAndDrawParagraph(paragraph);
            this.y += font.FONT_HEIGHT + 1;
        }
    }
    public void processAndDrawParagraph(String paragraph) {
        String[] words = paragraph.split(" ");
        if (words.length == 0) return;

        StringBuilder line = new StringBuilder(words[0] + " ");
        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            if (font.getStringWidth(line + word) > this.width) {
                drawLine(String.valueOf(line).trim());
                line = new StringBuilder(word + " ");
            }
            else {
                line.append(word).append(" ");
            }
        }
        if (!line.isEmpty()) {
            drawLine(String.valueOf(line));
        }
    }
    public void drawLine(String string) {
        font.drawStringWithShadow(string, this.x, this.y, 0xFFFFFF);
        this.y += font.FONT_HEIGHT + 1;
    }

    // TODO: must render icon even without adding new line, so its possible to render text and icons in same line
    // TODO: would i ever use it? it might be useful for HostileChampions, though just text coloring would be fine, ig.
    public void icon(ResourceLocation tex, int size) {
        GuiUtil.draw(tex, x, y, size, size);
        y += size + 2;
    }

    public void gapXY(int X, int Y) {
        x += X;
        y += Y;
    }
    public void gapY(int pixels) {
        gapXY(0, pixels);
    }
    public void gapX(int pixels) {
        gapXY(pixels, 0);
    }

}
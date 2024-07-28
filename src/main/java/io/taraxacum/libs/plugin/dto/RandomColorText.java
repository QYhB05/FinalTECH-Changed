package io.taraxacum.libs.plugin.dto;

import io.taraxacum.libs.plugin.util.TextUtil;
import org.bukkit.Color;

import javax.annotation.Nonnull;
 
public abstract class RandomColorText {
    private final String[] text;
    private final ColorString[] colorStrings;
    private final StringBuilder stringBuilder;

    public RandomColorText(@Nonnull String... text) {
        this.text = text;
        this.colorStrings = this.initColorList(text);
        this.stringBuilder = new StringBuilder(text.length);
    }

    public String[] getText() {
        return text;
    }

    @Nonnull
    public String get() {
        this.stringBuilder.delete(0, this.stringBuilder.length());

        for (ColorString colorString : this.colorStrings) {
            stringBuilder.append(TextUtil.toTextCode(colorString.color)).append(colorString.text);
        }

        return stringBuilder.toString();
    }

    @Nonnull
    public String getNext() {
        this.calNextColorList();

        return this.get();
    }

    public int getSize() {
        return this.colorStrings.length;
    }

    public abstract void calNextColorList();

    protected void setColor(@Nonnull Color color, int index) {
        this.colorStrings[index % this.getSize()].setColor(color);
    }

    protected void setText(@Nonnull String text, int index) {
        this.colorStrings[index % this.getSize()].setText(text);
    }

    @Nonnull
    protected abstract ColorString[] initColorList(@Nonnull String... text);

    public static class ColorString {
        private String text;
        private Color color;

        public ColorString(String text, Color color) {
            this.text = text;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
}

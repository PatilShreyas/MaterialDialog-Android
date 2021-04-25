package dev.shreyaspatil.MaterialDialog.model;

import android.text.Spanned;

public abstract class DialogMessage<T extends CharSequence> {
    private final TextAlignment textAlignment;

    private DialogMessage(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public static SpannedMessage spanned(Spanned text, TextAlignment alignment) {
        return new SpannedMessage(text, alignment);
    }

    public static TextMessage text(String text, TextAlignment alignment) {
        return new TextMessage(text, alignment);
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public abstract T getText();

    public static class SpannedMessage extends DialogMessage<Spanned> {

        private final Spanned text;

        SpannedMessage(Spanned text, TextAlignment textAlignment) {
            super(textAlignment);
            this.text = text;
        }

        @Override
        public Spanned getText() {
            return text;
        }
    }

    public static class TextMessage extends DialogMessage<String> {

        private final String text;

        TextMessage(String text, TextAlignment textAlignment) {
            super(textAlignment);
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
    }
}

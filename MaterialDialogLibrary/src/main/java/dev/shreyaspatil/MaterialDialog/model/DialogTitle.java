package dev.shreyaspatil.MaterialDialog.model;

public class DialogTitle {
    private final String text;
    private final TextAlignment textAlignment;

    public DialogTitle(String text, TextAlignment textAlignment) {
        this.text = text;
        this.textAlignment = textAlignment;
    }

    public String getText() {
        return text;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }
}

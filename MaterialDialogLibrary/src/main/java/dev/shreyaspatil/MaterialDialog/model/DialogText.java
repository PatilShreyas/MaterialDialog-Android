package dev.shreyaspatil.MaterialDialog.model;

public class DialogText {
    private final String text;
    private final TextAlignment textAlignment;

    public DialogText(String text, TextAlignment textAlignment) {
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

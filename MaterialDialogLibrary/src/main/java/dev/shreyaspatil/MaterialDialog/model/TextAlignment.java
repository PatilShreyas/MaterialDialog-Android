package dev.shreyaspatil.MaterialDialog.model;

import android.view.View;

public enum TextAlignment {
    START(View.TEXT_ALIGNMENT_TEXT_START),
    END(View.TEXT_ALIGNMENT_TEXT_END),
    CENTER(View.TEXT_ALIGNMENT_CENTER);

    private final int alignment;

    TextAlignment(int alignment) {
        this.alignment = alignment;
    }

    public int getAlignment() {
        return alignment;
    }
}

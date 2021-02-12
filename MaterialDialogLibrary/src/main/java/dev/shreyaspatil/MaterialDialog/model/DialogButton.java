package dev.shreyaspatil.MaterialDialog.model;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;

public class DialogButton {
    private String title;
    private int icon;
    private AbstractDialog.OnClickListener onClickListener;

    public DialogButton(String title, int icon, AbstractDialog.OnClickListener onClickListener) {
        this.title = title;
        this.icon = icon;
        this.onClickListener = onClickListener;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public AbstractDialog.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}

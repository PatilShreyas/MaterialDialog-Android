package com.shreyaspatil.MaterialDialog.model;

import com.shreyaspatil.MaterialDialog.SimpleMaterialDialog;

public class DialogButton {
    private String title;
    private int icon;
    private SimpleMaterialDialog.OnClickListener onClickListener;

    public DialogButton(String title, int icon, SimpleMaterialDialog.OnClickListener onClickListener) {
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

    public SimpleMaterialDialog.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}

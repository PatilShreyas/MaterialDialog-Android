package com.shreyaspatil.MaterialDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.shreyaspatil.MaterialDialog.model.DialogButton;

public class BottomSheetMaterialDialog extends MaterialDialog {

    private AppCompatActivity mActivity;

    protected BottomSheetMaterialDialog(@NonNull AppCompatActivity mActivity,
                                        @NonNull String title,
                                        @NonNull String message,
                                        boolean mCancelable,
                                        @NonNull DialogButton mPositiveButton,
                                        @NonNull DialogButton mNegativeButton,
                                        int mAnimationResId,
                                        @NonNull String mAnimationFile) {
        super(mActivity, title, message, mCancelable, mPositiveButton, mNegativeButton, mAnimationResId, mAnimationFile);

        this.mActivity = mActivity;

        // Init Dialog, Create Bottom Sheet Dialog
        mDialog = new BottomSheetDialog(mActivity);

        LayoutInflater inflater = mActivity.getLayoutInflater();

        mDialog.setContentView(createView(inflater, null));

        // Set Cancelable property
        mDialog.setCancelable(mCancelable);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return super.createView(inflater, container);
    }

    public static class Builder {
        private AppCompatActivity activity;
        private String title;
        private String message;
        private boolean isCancelable = false;
        private DialogButton positiveButton;
        private DialogButton negativeButton;
        private int animationResId = NO_ANIMATION;
        private String animationFile;

        public Builder(@NonNull AppCompatActivity activity) {
            this.activity = activity;
        }

        @NonNull
        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        @NonNull
        public Builder setMessage(@NonNull String message) {
            this.message = message;
            return this;
        }

        @NonNull
        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        @NonNull
        public Builder setPositiveButton(@NonNull String name, @NonNull OnClickListener onClickListener) {
            positiveButton = new DialogButton(name, NO_ICON, onClickListener);
            return this;
        }

        @NonNull
        public Builder setPositiveButton(@NonNull String name, int icon, @NonNull OnClickListener onClickListener) {
            positiveButton = new DialogButton(name, icon, onClickListener);
            return this;
        }

        @NonNull
        public Builder setNegativeButton(@NonNull String name, @NonNull OnClickListener onClickListener) {
            negativeButton = new DialogButton(name, NO_ICON, onClickListener);
            return this;
        }

        @NonNull
        public Builder setNegativeButton(@NonNull String name, int icon, @NonNull OnClickListener onClickListener) {
            negativeButton = new DialogButton(name, icon, onClickListener);
            return this;
        }

        @NonNull
        public Builder setAnimation(int animationResId) {
            this.animationResId = animationResId;
            return this;
        }

        @NonNull
        public Builder setAnimation(@NonNull String fileName) {
            this.animationFile = fileName;
            return this;
        }

        @NonNull
        public BottomSheetMaterialDialog build() {
            return new BottomSheetMaterialDialog(activity, title, message, isCancelable, positiveButton, negativeButton, animationResId, animationFile);
        }

    }

    class BottomSheetDialog extends com.google.android.material.bottomsheet.BottomSheetDialog {

        public BottomSheetDialog(@NonNull Context context) {
            super(context, R.style.BottomSheetDialogTheme);
        }
    }
}

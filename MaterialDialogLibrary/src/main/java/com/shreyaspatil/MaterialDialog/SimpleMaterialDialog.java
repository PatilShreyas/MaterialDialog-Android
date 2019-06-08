package com.shreyaspatil.MaterialDialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.shreyaspatil.MaterialDialog.model.DialogButton;

public final class SimpleMaterialDialog {
    private Dialog mDialog;
    private Activity mActivity;
    private String title;
    private String message;
    private boolean mCancelable;
    private DialogButton mPositiveButton;
    private DialogButton mNegativeButton;
    private int mAnimationResId = -111;
    private String mAnimationFile = null;

    public static final int BUTTON_POSITIVE = 1;
    public static final int BUTTON_NEGATIVE = -1;

    private SimpleMaterialDialog(Activity mActivity, String title, String message, boolean mCancelable, DialogButton mPositiveButton, DialogButton mNegativeButton, int mAnimationResId, String mAnimationFile) {
        this.mActivity = mActivity;
        this.title = title;
        this.message = message;
        this.mCancelable = mCancelable;
        this.mPositiveButton = mPositiveButton;
        this.mNegativeButton = mNegativeButton;
        this.mAnimationResId = mAnimationResId;
        this.mAnimationFile = mAnimationFile;
    }

    public void show() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        // Get the layout inflater
        LayoutInflater inflater = mActivity.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_alert_dialog, null, false);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView);

        // Initialize Views
        TextView mTitleView = dialogView.findViewById(R.id.textView_title);
        TextView mMessageView = dialogView.findViewById(R.id.textView_message);
        MaterialButton mPositiveButtonView = dialogView.findViewById(R.id.button_positive);
        MaterialButton mNegativeButtonView = dialogView.findViewById(R.id.button_negative);
        LottieAnimationView mAnimationView = dialogView.findViewById(R.id.animation_view);

        // Set Cancelable
        builder.setCancelable(mCancelable);

        // Set Title
        if (title != null) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(title);
        } else {
            mTitleView.setVisibility(View.GONE);
        }

        // Set Message
        if (message != null) {
            mMessageView.setVisibility(View.VISIBLE);
            mMessageView.setText(message);
        } else {
            mMessageView.setVisibility(View.GONE);
        }

        // Set Positive Button
        if (mPositiveButton != null) {
            mPositiveButtonView.setVisibility(View.VISIBLE);
            mPositiveButtonView.setText(mPositiveButton.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mPositiveButtonView.setIcon(mActivity.getDrawable(mPositiveButton.getIcon()));
            }

            mPositiveButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPositiveButton.getOnClickListener().onClick(mDialog, BUTTON_POSITIVE);
                }
            });
        } else {
            mPositiveButtonView.setVisibility(View.GONE);
        }

        // Set Negative Button
        if (mNegativeButton != null) {
            mNegativeButtonView.setVisibility(View.VISIBLE);
            mNegativeButtonView.setText(mNegativeButton.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mNegativeButtonView.setIcon(mActivity.getDrawable(mNegativeButton.getIcon()));
            }

            mNegativeButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNegativeButton.getOnClickListener().onClick(mDialog, BUTTON_NEGATIVE);
                }
            });
        } else {
            mNegativeButtonView.setVisibility(View.GONE);
        }

        // Set Animation
        if (mAnimationResId != -111) {
            mAnimationView.setVisibility(View.VISIBLE);
            mAnimationView.setAnimation(mAnimationResId);
            mAnimationView.playAnimation();
        }

        if (mAnimationFile != null) {
            mAnimationView.setVisibility(View.VISIBLE);
            mAnimationView.setAnimation(mAnimationFile);
            mAnimationView.playAnimation();
        }

        // Build and Show dialog
        mDialog = builder.create();
        mDialog.show();
    }

    public static class Builder {
        private Activity activity;
        private String title;
        private String message;
        private boolean isCancelable;
        private DialogButton positiveButton;
        private DialogButton negativeButton;
        private int animationResId;
        private String animationFile;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public Builder setPositiveButton(String name, int icon, OnClickListener onClickListener) {
            positiveButton = new DialogButton(name, icon, onClickListener);
            return this;
        }

        public Builder setNegativeButton(String name, int icon, OnClickListener onClickListener) {
            negativeButton = new DialogButton(name, icon, onClickListener);
            return this;
        }

        public Builder setAnimation(int animationResId) {
            this.animationResId = animationResId;
            return this;
        }

        public Builder setAnimation(String fileName) {
            this.animationFile = fileName;
            return this;
        }

        public SimpleMaterialDialog build() {
            return new SimpleMaterialDialog(activity, title, message, isCancelable, positiveButton, negativeButton, animationResId, animationFile);
        }

    }

    public interface OnClickListener extends AlertDialog.OnClickListener { }
}

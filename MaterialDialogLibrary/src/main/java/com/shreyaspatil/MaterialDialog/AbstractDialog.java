package com.shreyaspatil.MaterialDialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener;
import com.shreyaspatil.MaterialDialog.model.DialogButton;

public class AbstractDialog implements DialogInterface {

    //Constants
    public static final int BUTTON_POSITIVE = 1;
    public static final int BUTTON_NEGATIVE = -1;
    public static final int NO_ICON = -111;
    public static final int NO_ANIMATION = -111;

    protected Dialog mDialog;
    protected Activity mActivity;
    protected String title;
    protected String message;
    protected boolean mCancelable;
    protected DialogButton mPositiveButton;
    protected DialogButton mNegativeButton;
    protected int mAnimationResId;
    protected String mAnimationFile;
    protected LottieAnimationView mAnimationView;

    protected OnDismissListener mOnDismissListener;
    protected OnCancelListener mOnCancelListener;
    protected OnShowListener mOnShowListener;


    protected AbstractDialog(@NonNull Activity mActivity,
                             @NonNull String title,
                             @NonNull String message,
                             boolean mCancelable,
                             @NonNull DialogButton mPositiveButton,
                             @NonNull DialogButton mNegativeButton,
                             int mAnimationResId,
                             @NonNull String mAnimationFile) {
        this.mActivity = mActivity;
        this.title = title;
        this.message = message;
        this.mCancelable = mCancelable;
        this.mPositiveButton = mPositiveButton;
        this.mNegativeButton = mNegativeButton;
        this.mAnimationResId = mAnimationResId;
        this.mAnimationFile = mAnimationFile;
    }

    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.layout_alert_dialog, container, false);

        // Initialize Views
        TextView mTitleView = dialogView.findViewById(R.id.textView_title);
        TextView mMessageView = dialogView.findViewById(R.id.textView_message);
        MaterialButton mPositiveButtonView = dialogView.findViewById(R.id.button_positive);
        MaterialButton mNegativeButtonView = dialogView.findViewById(R.id.button_negative);
        mAnimationView = dialogView.findViewById(R.id.animation_view);

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mPositiveButton.getIcon() != NO_ICON) {
                mPositiveButtonView.setIcon(mActivity.getDrawable(mPositiveButton.getIcon()));
            }

            mPositiveButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPositiveButton.getOnClickListener().onClick(AbstractDialog.this, BUTTON_POSITIVE);
                }
            });
        } else {
            mPositiveButtonView.setVisibility(View.INVISIBLE);
        }

        // Set Negative Button
        if (mNegativeButton != null) {
            mNegativeButtonView.setVisibility(View.VISIBLE);
            mNegativeButtonView.setText(mNegativeButton.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mNegativeButton.getIcon() != NO_ICON) {
                mNegativeButtonView.setIcon(mActivity.getDrawable(mNegativeButton.getIcon()));
            }

            mNegativeButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNegativeButton.getOnClickListener().onClick(AbstractDialog.this, BUTTON_NEGATIVE);
                }
            });
        } else {
            mNegativeButtonView.setVisibility(View.INVISIBLE);
        }

        // Set Animation from Resource
        if (mAnimationResId != NO_ANIMATION) {
            mAnimationView.setVisibility(View.VISIBLE);
            mAnimationView.setAnimation(mAnimationResId);
            mAnimationView.playAnimation();

            // Set Animation from Assets File
        } else if (mAnimationFile != null) {
            mAnimationView.setVisibility(View.VISIBLE);
            mAnimationView.setAnimation(mAnimationFile);
            mAnimationView.playAnimation();

        } else {
            mAnimationView.setVisibility(View.GONE);
        }

        return dialogView;
    }

    /**
     * Displays the Dialog
     */
    public void show() {
        if (mDialog != null) {
            mDialog.show();
        } else {
            throwNullDialog();
        }
    }

    /**
     * Cancels the Dialog
     */
    @Override
    public void cancel() {
        if (mDialog != null) {
            mDialog.cancel();
        } else {
            throwNullDialog();
        }
    }

    /**
     * Dismisses the Dialog
     */
    @Override
    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        } else {
            throwNullDialog();
        }
    }

    /**
     * @param onShowListener interface for callback events when dialog is showed.
     */
    public void setOnShowListener(@NonNull final OnShowListener onShowListener) {
        this.mOnShowListener = onShowListener;

        mDialog.setOnShowListener(new android.content.DialogInterface.OnShowListener() {
            @Override
            public void onShow(android.content.DialogInterface dialogInterface) {
                showCallback();
            }
        });
    }

    /**
     * @param onCancelListener interface for callback events when dialog is cancelled.
     */
    public void setOnCancelListener(@NonNull final OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;

        mDialog.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(android.content.DialogInterface dialogInterface) {
                cancelCallback();
            }
        });
    }

    /**
     * @param onDismissListener interface for callback events when dialog is dismissed;
     */
    public void setOnDismissListener(@NonNull final OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;

        mDialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(android.content.DialogInterface dialogInterface) {
                dismissCallback();
            }
        });
    }

    /**
     * @return {@link LottieAnimationView} from the Dialog.
     */
    public LottieAnimationView getAnimationView() {
        return mAnimationView;
    }

    private void showCallback() {
        if (mOnShowListener != null) {
            mOnShowListener.onShow(this);
        }
    }

    private void dismissCallback() {
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(this);
        }
    }

    private void cancelCallback() {
        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel(this);
        }
    }

    private void throwNullDialog() {
        throw new NullPointerException("Called method on null Dialog. Create dialog using `Builder` before calling on Dialog");
    }

    public interface OnClickListener {
        void onClick(DialogInterface dialogInterface, int which);
    }
}

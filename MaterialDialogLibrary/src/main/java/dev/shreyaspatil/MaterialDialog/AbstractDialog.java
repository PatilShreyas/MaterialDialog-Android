package dev.shreyaspatil.MaterialDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;

import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import dev.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import dev.shreyaspatil.MaterialDialog.interfaces.OnShowListener;
import dev.shreyaspatil.MaterialDialog.model.DialogButton;
import dev.shreyaspatil.MaterialDialog.model.DialogMessage;
import dev.shreyaspatil.MaterialDialog.model.DialogTitle;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

@SuppressWarnings("unused")
public abstract class AbstractDialog implements DialogInterface {

    //Constants
    public static final int BUTTON_POSITIVE = 1;
    public static final int BUTTON_NEGATIVE = -1;
    public static final int NO_ICON = -111;
    public static final int NO_ANIMATION = -111;

    protected Dialog mDialog;
    protected Activity mActivity;
    protected DialogTitle title;
    protected DialogMessage message;
    protected boolean mCancelable;
    protected DialogButton mPositiveButton;
    protected DialogButton mNegativeButton;
    protected int mAnimationResId;
    protected String mAnimationFile;
    protected LottieAnimationView mAnimationView;

    protected TextAlignment mTitleTextAlignment;
    protected TextAlignment mMessageTextAlignment;

    protected OnDismissListener mOnDismissListener;
    protected OnCancelListener mOnCancelListener;
    protected OnShowListener mOnShowListener;

    protected AbstractDialog(@NonNull Activity mActivity,
                             @NonNull DialogTitle title,
                             @NonNull DialogMessage message,
                             boolean mCancelable,
                             @NonNull DialogButton mPositiveButton,
                             @NonNull DialogButton mNegativeButton,
                             @RawRes int mAnimationResId,
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

    @SuppressLint("WrongConstant")
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
            mTitleView.setText(title.getText());
            mTitleView.setTextAlignment(title.getTextAlignment().getAlignment());
        } else {
            mTitleView.setVisibility(View.GONE);
        }

        // Set Message
        if (message != null) {
            mMessageView.setVisibility(View.VISIBLE);

            mMessageView.setText(message.getText());
            mMessageView.setTextAlignment(message.getTextAlignment().getAlignment());
        } else {
            mMessageView.setVisibility(View.GONE);
        }

        // Set Positive Button
        if (mPositiveButton != null) {
            mPositiveButtonView.setVisibility(View.VISIBLE);
            mPositiveButtonView.setText(mPositiveButton.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mPositiveButton.getIcon() != NO_ICON) {
                mPositiveButtonView.setIcon(ContextCompat.getDrawable(mActivity, mPositiveButton.getIcon()));
            }

            mPositiveButtonView.setOnClickListener(view ->
                    mPositiveButton.getOnClickListener().onClick(AbstractDialog.this, BUTTON_POSITIVE)
            );
        } else {
            mPositiveButtonView.setVisibility(View.INVISIBLE);
        }

        // Set Negative Button
        if (mNegativeButton != null) {
            mNegativeButtonView.setVisibility(View.VISIBLE);
            mNegativeButtonView.setText(mNegativeButton.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mNegativeButton.getIcon() != NO_ICON) {
                mNegativeButtonView.setIcon(ContextCompat.getDrawable(mActivity, mNegativeButton.getIcon()));
            }

            mNegativeButtonView.setOnClickListener(view ->
                    mNegativeButton.getOnClickListener().onClick(AbstractDialog.this, BUTTON_NEGATIVE)
            );
        } else {
            mNegativeButtonView.setVisibility(View.INVISIBLE);
        }

        // If Orientation is Horizontal, Hide AnimationView
        int orientation = mActivity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mAnimationView.setVisibility(View.GONE);
        } else {
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
        }

        // Apply Styles
        TypedArray a = mActivity.getTheme().obtainStyledAttributes(R.styleable.MaterialDialog);

        try {
            // Set Dialog Background
            dialogView.setBackgroundColor(
                    a.getColor(R.styleable.MaterialDialog_material_dialog_background,
                            mActivity.getResources().getColor(R.color.material_dialog_background)));

            // Set Title Text Color
            mTitleView.setTextColor(
                    a.getColor(R.styleable.MaterialDialog_material_dialog_title_text_color,
                            mActivity.getResources().getColor(R.color.material_dialog_title_text_color)));

            // Set Message Text Color
            mMessageView.setTextColor(
                    a.getColor(R.styleable.MaterialDialog_material_dialog_message_text_color,
                            mActivity.getResources().getColor((R.color.material_dialog_message_text_color))));

            // Set Positive Button Icon Tint
            ColorStateList mPositiveButtonTint = a.getColorStateList(
                    R.styleable.MaterialDialog_material_dialog_positive_button_text_color);

            if (mPositiveButtonTint == null) {
                mPositiveButtonTint = ContextCompat.getColorStateList(
                        mActivity.getApplicationContext(),
                        R.color.material_dialog_positive_button_text_color);
            }
            mPositiveButtonView.setTextColor(mPositiveButtonTint);
            mPositiveButtonView.setIconTint(mPositiveButtonTint);

            // Set Negative Button Icon & Text Tint
            ColorStateList mNegativeButtonTint = a.getColorStateList(
                    R.styleable.MaterialDialog_material_dialog_negative_button_text_color);

            if (mNegativeButtonTint == null) {
                mNegativeButtonTint = ContextCompat.getColorStateList(
                        mActivity.getApplicationContext(),
                        R.color.material_dialog_negative_button_text_color);
            }
            mNegativeButtonView.setIconTint(mNegativeButtonTint);
            mNegativeButtonView.setTextColor(mNegativeButtonTint);

            // Set Positive Button Background Tint
            ColorStateList mBackgroundTint = a.getColorStateList(
                    R.styleable.MaterialDialog_material_dialog_positive_button_color);

            if (mBackgroundTint == null) {
                mBackgroundTint = ContextCompat.getColorStateList(
                        mActivity.getApplicationContext(),
                        R.color.material_dialog_positive_button_color);
            }
            mPositiveButtonView.setBackgroundTintList(mBackgroundTint);
            if (mBackgroundTint != null) {
                mNegativeButtonView.setRippleColor(mBackgroundTint.withAlpha(75));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
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

        mDialog.setOnShowListener(dialogInterface -> showCallback());
    }

    /**
     * @param onCancelListener interface for callback events when dialog is cancelled.
     */
    public void setOnCancelListener(@NonNull final OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;

        mDialog.setOnCancelListener(dialogInterface -> cancelCallback());
    }

    /**
     * @param onDismissListener interface for callback events when dialog is dismissed;
     */
    public void setOnDismissListener(@NonNull final OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;

        mDialog.setOnDismissListener(dialogInterface -> dismissCallback());
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

    /**
     * Builder for {@link AbstractDialog}.
     */
    public static abstract class Builder<D extends AbstractDialog> {
        protected final Activity activity;
        protected DialogTitle title;
        protected DialogMessage message;
        protected boolean isCancelable;
        protected DialogButton positiveButton;
        protected DialogButton negativeButton;
        protected int animationResId = NO_ANIMATION;
        protected String animationFile;

        /**
         * @param activity where Material Dialog is to be built.
         */
        public Builder(@NonNull Activity activity) {
            this.activity = activity;
        }

        /**
         * @param title Sets the Title of Material Dialog with the default alignment as center.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setTitle(@NonNull String title) {
            return setTitle(title, TextAlignment.CENTER);
        }

        /**
         * @param title     Sets the Title of Material Dialog.
         * @param alignment Sets the Alignment for the title.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setTitle(@NonNull String title, @NonNull TextAlignment alignment) {
            this.title = new DialogTitle(title, alignment);
            return this;
        }

        /**
         * @param message Sets the plain text Message of Material Dialog with the default alignment as center.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setMessage(@NonNull String message) {
            return setMessage(message, TextAlignment.CENTER);
        }

        /**
         * @param message   Sets the plain text Message of Material Dialog.
         * @param alignment Sets the Alignment for the message.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setMessage(@NonNull String message, @NonNull TextAlignment alignment) {
            this.message = DialogMessage.text(message, alignment);
            return this;
        }

        /**
         * @param message Sets the spanned text Message of Material Dialog with the default alignment as center.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setMessage(@NonNull Spanned message) {
            return setMessage(message, TextAlignment.CENTER);
        }

        /**
         * @param message   Sets the spanned text Message of Material Dialog.
         * @param alignment Sets the Alignment for the message.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setMessage(@NonNull Spanned message, @NonNull TextAlignment alignment) {
            this.message = DialogMessage.spanned(message, alignment);
            return this;
        }

        /**
         * @param isCancelable Sets cancelable property of Material Dialog.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        /**
         * Sets the Positive Button to Material Dialog without icon
         *
         * @param name            sets the name/label of button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setPositiveButton(@NonNull String name, @NonNull OnClickListener onClickListener) {
            return setPositiveButton(name, NO_ICON, onClickListener);
        }

        /**
         * Sets the Positive Button to Material Dialog with icon
         *
         * @param name            sets the name/label of button.
         * @param icon            sets the resource icon for button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setPositiveButton(@NonNull String name, int icon, @NonNull OnClickListener onClickListener) {
            positiveButton = new DialogButton(name, icon, onClickListener);
            return this;
        }

        /**
         * Sets the Negative Button to Material Dialog without icon.
         *
         * @param name            sets the name/label of button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setNegativeButton(@NonNull String name, @NonNull OnClickListener onClickListener) {
            return setNegativeButton(name, NO_ICON, onClickListener);
        }

        /**
         * Sets the Negative Button to Material Dialog with icon
         *
         * @param name            sets the name/label of button.
         * @param icon            sets the resource icon for button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setNegativeButton(@NonNull String name, int icon, @NonNull OnClickListener onClickListener) {
            negativeButton = new DialogButton(name, icon, onClickListener);
            return this;
        }

        /**
         * It sets the resource json to the {@link com.airbnb.lottie.LottieAnimationView}.
         *
         * @param animationResId sets the resource to {@link com.airbnb.lottie.LottieAnimationView}.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setAnimation(@RawRes int animationResId) {
            this.animationResId = animationResId;
            return this;
        }

        /**
         * It sets the json file to the {@link com.airbnb.lottie.LottieAnimationView} from assets.
         *
         * @param fileName sets the file from assets to {@link com.airbnb.lottie.LottieAnimationView}.
         * @return this, for chaining.
         */
        @NonNull
        public Builder<D> setAnimation(@NonNull String fileName) {
            this.animationFile = fileName;
            return this;
        }

        /**
         * Builds the dialog.
         */
        @NonNull
        public abstract D build();
    }
}

package com.shreyaspatil.MaterialDialog;

import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import com.shreyaspatil.MaterialDialog.model.DialogButton;

/**
 * Creates BottomSheet Material Dialog with 2 buttons.
 * <p>
 * Use {@link BottomSheetMaterialDialog.Builder} to create a new instance.
 */
public class BottomSheetMaterialDialog extends AbstractDialog {

    private AppCompatActivity mActivity;

    protected BottomSheetMaterialDialog(@NonNull final AppCompatActivity mActivity,
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

        View dialogView = createView(inflater, null);
        mDialog.setContentView(dialogView);

        // Set Cancelable property
        mDialog.setCancelable(mCancelable);

        // Clip AnimationView to round Corners
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    float radius = mActivity.getResources().getDimension(R.dimen.radiusTop);
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight() + (int) radius, radius);
                }
            });
            dialogView.setClipToOutline(true);
        } else {
            dialogView.findViewById(R.id.relative_layout_dialog).setPadding(0, (int) mActivity.getResources().getDimension(R.dimen.paddingTop),0,0);
        }
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return super.createView(inflater, container);
    }

    /**
     * Builder for {@link BottomSheetMaterialDialog}.
     */
    public static class Builder {
        private AppCompatActivity activity;
        private String title;
        private String message;
        private boolean isCancelable;
        private DialogButton positiveButton;
        private DialogButton negativeButton;
        private int animationResId = NO_ANIMATION;
        private String animationFile;

        /**
         * @param activity where BottomSheet Material Dialog is to be built.
         */
        public Builder(@NonNull AppCompatActivity activity) {
            this.activity = activity;
        }

        /**
         * @param title Sets the Title of BottomSheet Material Dialog.
         * @return this, for chaining.
         */
        @NonNull
        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        /**
         * @param message Sets the Message of BottomSheet Material Dialog.
         * @return this, for chaining.
         */
        @NonNull
        public Builder setMessage(@NonNull String message) {
            this.message = message;
            return this;
        }

        /**
         * @param isCancelable Sets cancelable property of BottomSheet Material Dialog.
         * @return this, for chaining.
         */
        @NonNull
        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        /** Sets the Positive Button to BottomSheet Material Dialog without icon
         * @param name sets the name/label of button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        @NonNull
        public Builder setPositiveButton(@NonNull String name, @NonNull OnClickListener onClickListener) {
            return setPositiveButton(name, NO_ICON, onClickListener);
        }

        /** Sets the Positive Button to BottomSheet Material Dialog with icon
         * @param name sets the name/label of button.
         * @param icon sets the resource icon for button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        @NonNull
        public Builder setPositiveButton(@NonNull String name, int icon, @NonNull OnClickListener onClickListener) {
            positiveButton = new DialogButton(name, icon, onClickListener);
            return this;
        }

        /** Sets the Negative Button to BottomSheet Material Dialog without icon.
         * @param name sets the name/label of button.
         * @param onClickListener interface for callback event on click of button.
         * @see this, for chaining.
         */
        @NonNull
        public Builder setNegativeButton(@NonNull String name, @NonNull OnClickListener onClickListener) {
            return setNegativeButton(name, NO_ICON, onClickListener);
        }

        /** Sets the Negative Button to BottomSheet Material Dialog with icon
         * @param name sets the name/label of button.
         * @param icon sets the resource icon for button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        @NonNull
        public Builder setNegativeButton(@NonNull String name, int icon, @NonNull OnClickListener onClickListener) {
            negativeButton = new DialogButton(name, icon, onClickListener);
            return this;
        }

        /** It sets the resource json to the {@link com.airbnb.lottie.LottieAnimationView}.
         * @param animationResId sets the resource to {@link com.airbnb.lottie.LottieAnimationView}.
         * @return this, for chaining.
         */
        @NonNull
        public Builder setAnimation(@RawRes int animationResId) {
            this.animationResId = animationResId;
            return this;
        }

        /** It sets the json file to the {@link com.airbnb.lottie.LottieAnimationView} from assets.
         * @param fileName sets the file from assets to {@link com.airbnb.lottie.LottieAnimationView}.
         * @return this, for chaining.
         */
        @NonNull
        public Builder setAnimation(@NonNull String fileName) {
            this.animationFile = fileName;
            return this;
        }

        /**
         * Build the {@link BottomSheetMaterialDialog}.
         */
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

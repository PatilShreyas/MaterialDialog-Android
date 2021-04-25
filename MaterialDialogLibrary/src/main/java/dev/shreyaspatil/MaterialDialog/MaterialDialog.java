package dev.shreyaspatil.MaterialDialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AlertDialog;

import dev.shreyaspatil.MaterialDialog.model.DialogButton;
import dev.shreyaspatil.MaterialDialog.model.DialogMessage;
import dev.shreyaspatil.MaterialDialog.model.DialogTitle;

/**
 * Creates a Material Dialog with 2 buttons.
 * <p>
 * Use {@link Builder} to create a new instance.
 */
@SuppressWarnings("unused")
public final class MaterialDialog extends AbstractDialog {

    private MaterialDialog(@NonNull final Activity mActivity,
                           @NonNull DialogTitle title,
                           @NonNull DialogMessage message,
                           boolean mCancelable,
                           @NonNull DialogButton mPositiveButton,
                           @NonNull DialogButton mNegativeButton,
                           @RawRes int mAnimationResId,
                           @NonNull String mAnimationFile) {
        super(mActivity, title, message, mCancelable, mPositiveButton, mNegativeButton, mAnimationResId, mAnimationFile);

        // Init Dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        LayoutInflater inflater = mActivity.getLayoutInflater();

        View dialogView = createView(inflater, null);

        builder.setView(dialogView);

        // Set Cancelable property
        builder.setCancelable(mCancelable);

        // Create and show dialog
        mDialog = builder.create();
    }

    /**
     * Builder for {@link MaterialDialog}.
     */
    public static class Builder extends AbstractDialog.Builder<MaterialDialog> {
        /**
         * @param activity where Material Dialog is to be built.
         */
        public Builder(@NonNull Activity activity) {
            super(activity);
        }

        /**
         * Builds the {@link MaterialDialog}.
         */
        @NonNull
        @Override
        public MaterialDialog build() {
            return new MaterialDialog(
                    activity,
                    title,
                    message,
                    isCancelable,
                    positiveButton,
                    negativeButton,
                    animationResId,
                    animationFile
            );
        }
    }
}

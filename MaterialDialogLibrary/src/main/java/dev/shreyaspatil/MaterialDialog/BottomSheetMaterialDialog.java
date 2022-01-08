package dev.shreyaspatil.MaterialDialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import dev.shreyaspatil.MaterialDialog.model.DialogButton;
import dev.shreyaspatil.MaterialDialog.model.DialogMessage;
import dev.shreyaspatil.MaterialDialog.model.DialogTitle;

/**
 * Creates BottomSheet Material Dialog with 2 buttons.
 * <p>
 * Use {@link BottomSheetMaterialDialog.Builder} to create a new instance.
 */
@SuppressWarnings("unused")
public final class BottomSheetMaterialDialog extends AbstractDialog {

    private BottomSheetMaterialDialog(@NonNull final Activity mActivity,
                                      @NonNull DialogTitle title,
                                      @NonNull DialogMessage message,
                                      boolean mCancelable,
                                      @NonNull DialogButton mPositiveButton,
                                      @NonNull DialogButton mNegativeButton,
                                      @RawRes int mAnimationResId,
                                      @NonNull String mAnimationFile) {
        super(mActivity, title, message, mCancelable, mPositiveButton, mNegativeButton, mAnimationResId, mAnimationFile);

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
            dialogView.findViewById(R.id.relative_layout_dialog).setPadding(0, (int) mActivity.getResources().getDimension(R.dimen.paddingTop), 0, 0);
        }

        // Expand Bottom Sheet after showing.
        mDialog.setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;

            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);

            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return super.createView(inflater, container);
    }

    /**
     * Builder for {@link BottomSheetMaterialDialog}.
     */
    public static class Builder extends AbstractDialog.Builder<BottomSheetMaterialDialog> {
        /**
         * @param activity where Material Dialog is to be built.
         */
        public Builder(@NonNull Activity activity) {
            super(activity);
        }

        /**
         * Builds the {@link BottomSheetMaterialDialog}.
         */
        @NonNull
        @Override
        public BottomSheetMaterialDialog build() {
            return new BottomSheetMaterialDialog(
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

    static class BottomSheetDialog extends com.google.android.material.bottomsheet.BottomSheetDialog {
        BottomSheetDialog(@NonNull Context context) {
            super(context, R.style.BottomSheetDialogTheme);
        }
    }
}

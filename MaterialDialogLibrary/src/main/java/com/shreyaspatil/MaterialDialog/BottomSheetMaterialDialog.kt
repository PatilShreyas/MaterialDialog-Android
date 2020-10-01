package com.shreyaspatil.MaterialDialog

import android.app.Activity
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shreyaspatil.MaterialDialog.model.DialogBuilder
import com.shreyaspatil.MaterialDialog.model.DialogButton
import kotlinx.android.synthetic.main.layout_alert_dialog.view.*

/**
 * Creates BottomSheet Material Dialog with 2 buttons.
 * <p>
 * Use {@link BottomSheetMaterialDialog.Builder} to create a new instance.
 */
class BottomSheetMaterialDialog(
        mActivity: Activity,
        title: String?,
        message: String?,
        mCancelable: Boolean?,
        mPositiveButton: DialogButton?,
        mNegativeButton: DialogButton?,
        mAnimationResId: Int?,
        mAnimationFile: String?
) : AbstractDialog(mActivity, title, message, mPositiveButton, mNegativeButton, mAnimationResId, mAnimationFile) {

    init {

        val dialogView = createView(mActivity.layoutInflater, null)
                .apply {
                    // Clip AnimationView to round Corners
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outlineProvider = object : ViewOutlineProvider() {
                            override fun getOutline(view: View?, outline: Outline?) {
                                val radius = mActivity.resources.getDimension(R.dimen.radiusTop)
                                outline!!.setRoundRect(0, 0, view!!.width, view.height + radius.toInt(), radius)
                            }
                        }
                        clipToOutline = true
                    } else {
                        relative_layout_dialog.setPadding(
                                0,
                                mActivity.resources.getDimension(R.dimen.paddingTop).toInt(),
                                0,
                                0
                        )
                    }
                }

        // Init Dialog, Create Bottom Sheet Dialog
        mDialog = BottomSheetDialog(mActivity, R.style.BottomSheetDialogTheme)
                .apply {
                    setContentView(dialogView)
                    setCancelable(mCancelable!!)

                    setOnShowListener {
                        val d = it as BottomSheetDialog
                        val bottomSheet = d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

                        if (bottomSheet != null) {
                            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }
                }
    }

    /**
     * Builder for {@link BottomSheetMaterialDialog}.
     * @param activity where BottomSheet Material Dialog is to be built.
     */
    class Builder(private val activity: Activity) : DialogBuilder() {

        /**
         * Build the {@link BottomSheetMaterialDialog}.
         */
        fun build(): BottomSheetMaterialDialog =
                BottomSheetMaterialDialog(activity, title, message, isCancelable, mPositiveButton, mNegativeButton, animationResId, mAnimationFile)
    }
}
package com.shreyaspatil.MaterialDialog

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.shreyaspatil.MaterialDialog.model.DialogBuilder
import com.shreyaspatil.MaterialDialog.model.DialogButton

/**
 * Creates a Material Dialog with 2 buttons.
 * <p>
 * Use {@link Builder} to create a new instance.
 */
class MaterialDialog(
        mActivity: Activity,
        title: String?,
        message: String?,
        mCancelable: Boolean?,
        mPositiveButton: DialogButton?,
        mNegativeButton: DialogButton?,
        mAnimationResId: Int?,
        mAnimationFile: String?
) : AbstractDialog(mActivity, title, message, mPositiveButton, mNegativeButton, mAnimationResId, mAnimationFile)
{

    init {

        val dialogView = createView(mActivity.layoutInflater, null)

        //init dialog
        mDialog = AlertDialog.Builder(mActivity).run {
            setView(dialogView)
            setCancelable(mCancelable!!)
            create()
        }
    }

    /**
     * Builder for {@link MaterialDialog}.
     * @param activity where Material Dialog is to be built.
     */
    class Builder(private val activity: Activity) : DialogBuilder(){

        /**
         * Build the {@link MaterialDialog}.
         */
        fun build() : MaterialDialog =
                MaterialDialog(activity, title, message, isCancelable, mPositiveButton, mNegativeButton, animationResId, mAnimationFile)
    }
}
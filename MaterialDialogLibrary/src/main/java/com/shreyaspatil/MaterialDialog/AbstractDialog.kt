package com.shreyaspatil.MaterialDialog

import android.app.Activity
import android.app.Dialog
import android.content.res.Configuration
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener
import com.shreyaspatil.MaterialDialog.model.DialogButton
import kotlinx.android.synthetic.main.layout_alert_dialog.view.*

abstract class AbstractDialog(
        private val mActivity: Activity,
        private val title: String?,
        private val message: String?,
        private val mPositiveButton: DialogButton?,
        private val mNegativeButton: DialogButton?,
        private val mAnimationResId: Int?,
        private val mAnimationFile: String?
) : DialogInterface {

    companion object {
        //Constants
        const val BUTTON_POSITIVE = 1
        const val BUTTON_NEGATIVE = -1
        const val NO_ICON = -111
        const val NO_ANIMATION = -111
    }

    private lateinit var mAnimationView: LottieAnimationView
    protected var mDialog: Dialog? = null

    private lateinit var mOnDismissListener: OnDismissListener
    private lateinit var mOnCancelListener: OnCancelListener
    private lateinit var mOnShowListener: OnShowListener

    protected fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val dialogView = inflater.inflate(R.layout.layout_alert_dialog, container, false)

        // Initialize Views
        val mTitleView = dialogView.textView_title
        val mMessageView = dialogView.textView_message
        val mPositiveButtonView = dialogView.button_positive
        val mNegativeButtonView = dialogView.button_negative
        mAnimationView = dialogView.animation_view

        // Set Title
        title?.let {
            mTitleView.apply {
                visibility = View.VISIBLE
                text = it
            }
        }

        // set message
        message?.let {
            mMessageView.apply {
                visibility = View.VISIBLE
                text = it
            }
        }

        // Set positive button
        mPositiveButton?.let {
            mPositiveButtonView.apply {
                visibility = View.VISIBLE
                text = it.title
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && it.icon != NO_ICON) {
                    icon = ContextCompat.getDrawable(mActivity, it.icon)
                }
                setOnClickListener { mPositiveButton.onClick(this@AbstractDialog, BUTTON_POSITIVE) }
            }
        }

        // Set negative button
        mNegativeButton?.let {
            mNegativeButtonView.apply {
                visibility = View.VISIBLE
                text = it.title
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && it.icon != NO_ICON) {
                    icon = ContextCompat.getDrawable(mActivity, it.icon)
                }
                setOnClickListener { mNegativeButton.onClick(this@AbstractDialog, BUTTON_NEGATIVE) }
            }
        }

        // If Orientation is Horizontal, Hide AnimationView
        val orientation = mActivity.resources.configuration.orientation
        mAnimationView.apply {
            if (orientation == Configuration.ORIENTATION_LANDSCAPE){
                visibility = View.GONE
            } else {
                // Set Animation from Resource
                when {
                    mAnimationResId != NO_ANIMATION -> {
                        visibility = View.VISIBLE
                        setAnimation(mAnimationResId!!)
                        playAnimation()

                    }
                    mAnimationFile != null -> {
                        // Set Animation from Assets File
                        visibility = View.VISIBLE
                        setAnimation(mAnimationFile)
                        playAnimation()
                    }
                    else -> visibility = View.GONE
                }
            }
        }

        // Apply Styles
        val a = mActivity.theme.obtainStyledAttributes(R.styleable.MaterialDialog)

        try {
            // Set Dialog Background
            dialogView.setBackgroundColor(
                    a.getColor(R.styleable.MaterialDialog_material_dialog_background,
                            ContextCompat.getColor(mActivity, R.color.material_dialog_background)))

            // Set Title Text Color
            mTitleView.setTextColor(
                    a.getColor(R.styleable.MaterialDialog_material_dialog_title_text_color,
                            ContextCompat.getColor(mActivity, R.color.material_dialog_title_text_color)))

            // Set Message Text Color
            mMessageView.setTextColor(
                    a.getColor(R.styleable.MaterialDialog_material_dialog_message_text_color,
                            ContextCompat.getColor(mActivity, R.color.material_dialog_message_text_color)))

            // Set Positive Button Icon Tint
            var mPositiveButtonTint = a.getColorStateList(
                    R.styleable.MaterialDialog_material_dialog_positive_button_text_color)
            if (mPositiveButtonTint == null) {
                mPositiveButtonTint = ContextCompat.getColorStateList(
                        mActivity.applicationContext,
                        R.color.material_dialog_positive_button_text_color)
            }
            mPositiveButtonView.setTextColor(mPositiveButtonTint)
            mPositiveButtonView.iconTint = mPositiveButtonTint

            // Set Negative Button Icon & Text Tint
            var mNegativeButtonTint = a.getColorStateList(
                    R.styleable.MaterialDialog_material_dialog_negative_button_text_color)
            if (mNegativeButtonTint == null) {
                mNegativeButtonTint = ContextCompat.getColorStateList(
                        mActivity.applicationContext,
                        R.color.material_dialog_negative_button_text_color)
            }
            mNegativeButtonView.iconTint = mNegativeButtonTint
            mNegativeButtonView.setTextColor(mNegativeButtonTint)

            // Set Positive Button Background Tint
            var mBackgroundTint = a.getColorStateList(
                    R.styleable.MaterialDialog_material_dialog_positive_button_color)
            if (mBackgroundTint == null) {
                mBackgroundTint = ContextCompat.getColorStateList(
                        mActivity.applicationContext,
                        R.color.material_dialog_positive_button_color)
            }
            mPositiveButtonView.backgroundTintList = mBackgroundTint
            if (mBackgroundTint != null) {
                mNegativeButtonView.rippleColor = mBackgroundTint.withAlpha(75)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            a.recycle()
        }

        return dialogView
    }

    /**
     * Displays the Dialog
     */
    fun show() {
        mDialog?.show() ?: throwNullDialog()
    }

    /**
     * Cancels the Dialog
     */
    override fun cancel() {
        mDialog?.cancel() ?: throwNullDialog()
    }

    /**
     * Dismisses the Dialog
     */
    override fun dismiss() {
        mDialog?.dismiss() ?: throwNullDialog()
    }

    /**
     * @param onShowListener interface for callback events when dialog is showed.
     */
    fun setOnShowListener(onShowListener: OnShowListener) {
        mOnShowListener = onShowListener
        mDialog?.setOnShowListener { showCallback() }
    }

    /**
     * @param onCancelListener interface for callback events when dialog is cancelled.
     */
    fun setOnCancelListener(onCancelListener: OnCancelListener) {
        mOnCancelListener = onCancelListener
        mDialog?.setOnCancelListener { cancelCallback() }
    }

    /**
     * @param onDismissListener interface for callback events when dialog is dismissed;
     */
    fun setOnDismissListener(onDismissListener: OnDismissListener) {
        mOnDismissListener = onDismissListener
        mDialog?.setOnDismissListener { dismissCallback() }
    }

    /**
     * @return [LottieAnimationView] from the Dialog.
     */
    fun getAnimationView(): LottieAnimationView? {
        return mAnimationView
    }

    private fun showCallback() {
        mOnShowListener.onShow(this)
    }

    private fun dismissCallback() {
        mOnDismissListener.onDismiss(this)
    }

    private fun cancelCallback() {
        mOnCancelListener.onCancel(this)
    }

    private fun throwNullDialog() {
        throw NullPointerException("Called method on null Dialog. Create dialog using `Builder` before calling on Dialog")
    }

    interface OnClickListener {
        fun onClick(dialogInterface: DialogInterface?, which: Int)
    }
}
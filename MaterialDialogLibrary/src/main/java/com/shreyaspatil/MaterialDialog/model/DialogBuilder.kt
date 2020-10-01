package com.shreyaspatil.MaterialDialog.model

import com.shreyaspatil.MaterialDialog.AbstractDialog
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface

/**
 * Builder for Dialog.
 */
open class DialogBuilder() {

    var title: String? = null
    var message: String? = null
    var isCancelable: Boolean? = true
    protected var mPositiveButton: DialogButton? = null
    protected var mNegativeButton: DialogButton? = null
    protected var animationResId: Int? = AbstractDialog.NO_ANIMATION
    protected var mAnimationFile: String? = null

    /** Sets the Positive Button to Dialog without icon
     * @param name sets the name/label of button.
     * @param onClick higher order function for callback event on click of button.
     */
    fun setPositiveButton(name: String, icon: Int, onClick: (DialogInterface, Int) -> Unit) {
        mPositiveButton = DialogButton(name, icon, onClick)
    }

    /** Sets the Positive Button to Dialog without icon
     * @param name sets the name/label of button.
     * @param onClick higher order function for callback event on click of button.
     */
    fun setPositiveButton(name: String, onClick: (DialogInterface, Int) -> Unit) {
        mPositiveButton = DialogButton(name, AbstractDialog.NO_ICON, onClick)
    }

    /** Sets the Positive Button to Dialog with icon
     * @param name sets the name/label of button.
     * @param icon sets the resource icon for button.
     * @param onClick higher order function for callback event on click of button.
     */
    fun setNegativeButton(name: String, icon: Int, onClick: (DialogInterface, Int) -> Unit) {
        mNegativeButton = DialogButton(name, icon, onClick)
    }

    /** Sets the Positive Button to Dialog without icon
     * @param name sets the name/label of button.
     * @param onClick higher order function for callback event on click of button.
     */
    fun setNegativeButton(name: String, onClick: (DialogInterface, Int) -> Unit) {
        mNegativeButton = DialogButton(name, AbstractDialog.NO_ICON, onClick)
    }

    /** It sets the resource json to the {@link com.airbnb.lottie.LottieAnimationView}.
     * @param animationResId sets the resource to {@link com.airbnb.lottie.LottieAnimationView}.
     */
    fun setAnimation(animationResId: Int) {
        this.animationResId = animationResId
    }

    /** It sets the json file to the {@link com.airbnb.lottie.LottieAnimationView} from assets.
     * @param fileName sets the file from assets to {@link com.airbnb.lottie.LottieAnimationView}.
     */
    fun setAnimation(fileName: String) {
        this.mAnimationFile = fileName
    }
}
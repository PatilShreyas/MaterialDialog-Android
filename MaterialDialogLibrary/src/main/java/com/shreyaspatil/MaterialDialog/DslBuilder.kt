package com.shreyaspatil.MaterialDialog

import android.app.Activity

/**
 * Creates a Material Dialog with 2 buttons.
 * <p>
 * Use {@link Builder} to create a new instance.
 * <p>
 * @param activity where Material Dialog is to be built.
 * @param block a lambda function for configuration
 */
fun materialDialog(
        activity: Activity,
        block: MaterialDialog.Builder.() -> Unit
) = MaterialDialog.Builder(activity)
        .also(block).build()

/**
 * Creates BottomSheet Material Dialog with 2 buttons.
 * <p>
 * Use {@link BottomSheetMaterialDialog.Builder} to create a new instance.
 * <p>
 * @param activity where Material Dialog is to be built.
 * @param block a lambda function for configuration
 */
fun bottomSheetMaterialDialog(
        activity: Activity,
        block: BottomSheetMaterialDialog.Builder.() -> Unit
) = BottomSheetMaterialDialog.Builder(activity)
        .also(block).build()

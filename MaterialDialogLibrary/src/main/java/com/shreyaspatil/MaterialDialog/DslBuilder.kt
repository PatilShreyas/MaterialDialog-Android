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
        builder: MaterialDialog.Builder.() -> Unit
) : MaterialDialog = MaterialDialog.Builder(activity)
        .apply(builder).build()

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
        builder: BottomSheetMaterialDialog.Builder.() -> Unit
) : BottomSheetMaterialDialog = BottomSheetMaterialDialog.Builder(activity)
        .apply(builder).build()

package com.shreyaspatil.MaterialDialog.model

import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface

data class DialogButton(
        val title: String,
        val icon: Int,
        val onClick: (DialogInterface, Int) -> Unit
)


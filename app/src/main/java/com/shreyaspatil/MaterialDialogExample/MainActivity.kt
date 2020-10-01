package com.shreyaspatil.MaterialDialogExample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import com.shreyaspatil.MaterialDialog.MaterialDialog
import com.shreyaspatil.MaterialDialog.bottomSheetMaterialDialog
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener
import com.shreyaspatil.MaterialDialog.materialDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, OnShowListener, OnCancelListener, OnDismissListener {

    private lateinit var mSimpleDialog: MaterialDialog
    private lateinit var mAnimatedDialog: MaterialDialog
    private lateinit var mSimpleBottomSheetDialog: BottomSheetMaterialDialog
    private lateinit var mAnimatedBottomSheetDialog: BottomSheetMaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSimpleDialog = materialDialog(this) {
            title = "Delete"
            message = "Are you sure you want to delete this file?"
            isCancelable = false
            setPositiveButton("Delete", R.drawable.ic_delete) { dialog, which ->
                Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setNegativeButton("Cancel", R.drawable.ic_close) { dialog, which ->
                Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        mSimpleBottomSheetDialog = bottomSheetMaterialDialog(this) {
            title = "Delete"
            message = "Are you sure you want to delete this file?"
            isCancelable = false
            setPositiveButton("Delete", R.drawable.ic_delete) { dialog, which ->
                Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setNegativeButton("Cancel", R.drawable.ic_close) { dialog, which ->
                Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        mAnimatedDialog = materialDialog(this) {
            title = "Delete"
            message = "Are you sure you want to delete this file?"
            isCancelable = false
            setPositiveButton("Delete", R.drawable.ic_delete) { dialog, which ->
                Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setNegativeButton("Cancel", R.drawable.ic_close) { dialog, which ->
                Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setAnimation("delete_anim.json")
        }

        mAnimatedBottomSheetDialog = bottomSheetMaterialDialog(this) {
            title = "Delete"
            message = "Are you sure you want to delete this file?"
            isCancelable = false
            setPositiveButton("Delete", R.drawable.ic_delete) { dialog, which ->
                Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setNegativeButton("Cancel", R.drawable.ic_close) { dialog, which ->
                Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setAnimation("delete_anim.json")
        }


        button_simple_dialog.setOnClickListener(this)
        button_simple_bottomsheet_dialog.setOnClickListener(this)
        button_animated_dialog.setOnClickListener(this)
        button_animated_bottomsheet_dialog.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_simple_dialog -> mSimpleDialog.show()
            R.id.button_simple_bottomsheet_dialog -> mSimpleBottomSheetDialog.show()
            R.id.button_animated_dialog -> mAnimatedDialog.show()
            R.id.button_animated_bottomsheet_dialog -> mAnimatedBottomSheetDialog.show()
        }
    }

    override fun onShow(dialogInterface: DialogInterface) {

    }

    override fun onCancel(dialogInterface: DialogInterface) {

    }

    override fun onDismiss(dialogInterface: DialogInterface) {

    }
}
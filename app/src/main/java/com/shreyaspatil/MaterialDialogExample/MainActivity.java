package com.shreyaspatil.MaterialDialogExample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.SimpleMaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SimpleMaterialDialog alertDialog = new SimpleMaterialDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure want to exit?\nThis will quit dialog")
                .setCancelable(false)
                .setPositiveButton("OK", new SimpleMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "YES Click", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .setAnimation("plane.json")
                .build();

        final BottomSheetMaterialDialog sheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Exit?")
                .setCancelable(false)
                .setMessage("Are you sure want to exit?\nThis will quit dialog")
                .setPositiveButton("Delete", R.drawable.ic_check, new SimpleMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "YES Click", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new SimpleMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "YES Click", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .build();

        findViewById(R.id.button_smdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });

        findViewById(R.id.button_bsdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog.show();
            }
        });

        sheetDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                System.out.println("CANCELLED");
            }
        });

        sheetDialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                System.out.println("SHOWED");
            }
        });

        sheetDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                System.out.println("DISMISS");
            }
        });
    }
}

package com.shreyaspatil.MaterialDialogExample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.shreyaspatil.MaterialDialog.SimpleMaterialDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleMaterialDialog dialog = new SimpleMaterialDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure want to exit?\nThis will quit dialog")
                .setCancelable(false)
                .setPositiveButton("OK", R.drawable.ic_check, new SimpleMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "YES Click", Toast.LENGTH_SHORT).show();
                    }
                })

                .setAnimation("plane.json")
                .build();
        dialog.show();

    }
}

package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText versionname, versionnum;
    Button save, showdialogue, alert;
    androidVersion androidVersionDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        versionname = findViewById(R.id.etversionname);
        versionnum = findViewById(R.id.etversionnumber);
        save = findViewById(R.id.btnsave);
        showdialogue = findViewById(R.id.btnshow);
        alert = findViewById(R.id.btnalert);
        androidVersionDB = new androidVersion(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Versionname1 = versionname.getText().toString();
                int Versionnum1 = Integer.parseInt(versionnum.getText().toString());
                androidVersionDB.insert(Versionname1, Versionnum1);
                Toast.makeText(MainActivity.this, "sucessfully submitted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                Intent intent1 = getIntent();
                intent.putExtra("key", Versionname1);
                intent.putExtra("key", Versionnum1);
                setResult(RESULT_OK, intent);
                finish();
            }


        });
        showdialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.showdialog);
                EditText write = dialog.findViewById(R.id.add);
                Button ok = dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = write.getText().toString();
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                    }
                });
                Display display = getWindowManager().getDefaultDisplay();
                dialog.getWindow().setLayout((int) (display.getWidth() * 0.96), LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        alert.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Do you want to Delete")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, " YES DELETED", Toast.LENGTH_SHORT).show();
                            }


                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "NOT DELETED", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alertDialog.show();
                return true;
            }
        });
    }
}
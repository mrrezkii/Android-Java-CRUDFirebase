package com.example.crudfirebase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crudfirebase.model.Requests;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "YOUR-TAG-NAME";
    private DatabaseReference database;

    private EditText etNama, etEmail, etDesk;
    private Button btnSave, btnCancel;
    private ProgressDialog loading;

    private String sPid, sPnama, sPemail, spDesk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();

        sPid = getIntent().getStringExtra("id");
        sPnama = getIntent().getStringExtra("nama");
        sPemail = getIntent().getStringExtra("email");
        spDesk = getIntent().getStringExtra("desk");

        etNama = findViewById(R.id.et_Nama);
        etEmail = findViewById(R.id.et_Email);
        etDesk = findViewById(R.id.et_Desk);
        btnSave = findViewById(R.id.btn_Save);
        btnCancel = findViewById(R.id.btn_Cancel);

        etNama.setText(sPnama);
        etEmail.setText(sPemail);
        etDesk.setText(spDesk);

        if (sPid.equals("")) {
            btnSave.setText("Save");
            btnCancel.setText("Cancel");
        } else {
            btnSave.setText("Edit");
            btnCancel.setText("Delete");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNama = etNama.getText().toString();
                String sEmail = etEmail.getText().toString();
                String sDesk = etDesk.getText().toString();


                if (btnSave.getText().equals("Save")) {
                    if (sNama.equals("")) {
                        etNama.setError("Silahkan masukkan nama");
                        etNama.requestFocus();
                    } else if (sEmail.equals("")) {
                        etEmail.setError("Silahkan masukkan email");
                        etEmail.requestFocus();
                    } else if (sDesk.equals("")) {
                        etEmail.setError("Silahkan masukkan dekripsi");
                        etEmail.requestFocus();
                    } else {
                        loading = ProgressDialog.show(MainActivity.this,
                                null,
                                "Please wait....",
                                true,
                                false);
                        submitUser(new Requests(
                                sNama.toLowerCase(),
                                sEmail.toLowerCase(),
                                sDesk.toLowerCase()));
                    }
                } else {
                    if (sNama.equals("")) {
                        etNama.setError("Silahkan masukkan nama");
                        etNama.requestFocus();
                    } else if (sEmail.equals("")) {
                        etEmail.setError("Silahkan masukkan email");
                        etEmail.requestFocus();
                    } else if (sDesk.equals("")) {
                        etEmail.setError("Silahkan masukkan dekripsi");
                        etEmail.requestFocus();
                    } else {
                        loading = ProgressDialog.show(MainActivity.this,
                                null,
                                "Please wait....",
                                true,
                                false);
                        editUser(new Requests(
                                sNama.toLowerCase(),
                                sEmail.toLowerCase(),
                                sDesk.toLowerCase()), sPid);
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCancel.getText().equals("Cancel")) {
                    finish();
                } else {
                    database.child("Request")
                            .child(sPid)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this,
                                            "Data berhasil didelete",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });


    }

    private void editUser(Requests requests, String id) {
        database.child("Request")
                .child(id)
                .setValue(requests)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etNama.setText("");
                        etEmail.setText("");
                        etDesk.setText("");

                        Toast.makeText(MainActivity.this,
                                "Data berhasil di edit",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void submitUser(Requests requests) {
        database.child("Request")
                .push()
                .setValue(requests)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etNama.setText("");
                        etEmail.setText("");
                        etDesk.setText("");

                        Toast.makeText(MainActivity.this,
                                "Data berhasil di tambahkan",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
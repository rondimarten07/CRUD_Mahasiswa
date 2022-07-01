package com.example.crudmahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahActivity extends AppCompatActivity {
    EditText editNama, editNpm, editKelas;
    Button btnSimpan;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private void initUI(){
        editNama = findViewById(R.id.edit_nama);
        editNpm = findViewById(R.id.edit_npm);
        editKelas = findViewById(R.id.edit_kelas);
        btnSimpan = findViewById(R.id.btn_simpan);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        initUI();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNama = editNama.getText().toString();
                String getNpm = editNpm.getText().toString();
                String getKelas = editKelas.getText().toString();

                if (getNama.isEmpty()){
                    editNama.setError("Masukan Nama..");
                }else if (getNpm.isEmpty()){
                    editNpm.setError("Npm masih kosong");
                }else if (getKelas.isEmpty()){
                    editKelas.setError("Kelas masih kosong");
                }else{
                    database.child("Mahasiswa").push().setValue(new modelMahasiswa(getNama, getNpm, getKelas)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(TambahActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TambahActivity.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TambahActivity.this, "Data Gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
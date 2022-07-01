package com.example.crudmahasiswa;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment {
    String nama, npm, kelas, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String nama, String npm, String kelas, String key, String pilih) {
        this.nama = nama;
        this.npm = npm;
        this.kelas = kelas;
        this.key = key;
        this.pilih = pilih;
    }
    TextView tNama, tNpm, tKelas;
    Button btnSimpan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tambah, container, false);
        tNama = view.findViewById(R.id.edit_nama);
        tNpm = view.findViewById(R.id.edit_npm);
        tKelas = view.findViewById(R.id.edit_kelas);
        btnSimpan = view.findViewById(R.id.btn_simpan);

        tNama.setText(nama);
        tNpm.setText(npm);
        tKelas.setText(kelas);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = tNama.getText().toString();
                String npm = tNpm.getText().toString();
                String kelas = tKelas.getText().toString();

                if (pilih.equals("Ubah")){
                    database.child("Mahasiswa").child(key).setValue(new modelMahasiswa(nama, npm, kelas)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Data Berhasil di Update", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Data gagal di Update", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}

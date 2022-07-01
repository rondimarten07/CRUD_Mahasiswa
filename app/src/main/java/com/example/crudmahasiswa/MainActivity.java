package com.example.crudmahasiswa;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton tambah;
   AdapterMahasiswa adapterMahasiswa;
   DatabaseReference database = FirebaseDatabase.getInstance().getReference();
   ArrayList <modelMahasiswa> listMhs;
   RecyclerView tvTampil;


    private void initUI() {
        tambah = findViewById(R.id.btn_tambah);
        tvTampil = findViewById(R.id.tv_tampil);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        tvTampil.setLayoutManager(mLayout);
        tvTampil.setItemAnimator(new DefaultItemAnimator());

        tampilData();

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });



    }
    private void tampilData(){
        database.child("Mahasiswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMhs = new ArrayList<>();
                for (DataSnapshot item :snapshot.getChildren()){
                    modelMahasiswa mhs = item.getValue(modelMahasiswa.class);
                    mhs.setKey(item.getKey());
                    listMhs.add(mhs);
                }
                adapterMahasiswa = new AdapterMahasiswa(listMhs, MainActivity.this);
                tvTampil.setAdapter(adapterMahasiswa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
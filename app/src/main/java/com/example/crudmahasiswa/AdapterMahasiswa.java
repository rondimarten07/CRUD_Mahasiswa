package com.example.crudmahasiswa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterMahasiswa extends RecyclerView.Adapter<AdapterMahasiswa.mhsViewHolder> {
    private List<modelMahasiswa> mList;
    private Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    public  AdapterMahasiswa(List<modelMahasiswa>mList, Activity activity){
        this.mList = mList;
        this.activity = activity;
    }
    @NonNull
    @Override
    public mhsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.mhs_item, parent, false);
        return new mhsViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull mhsViewHolder holder, int position) {
        final modelMahasiswa dataMhs = mList.get(position);
        holder.tvNama.setText("Nama : " + dataMhs.getNama());
        holder.tvNpm.setText("Npm  : " + dataMhs.getNpm());
        holder.tvKelas.setText("Kelas : " + dataMhs.getKelas());
        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       database.child("Mahasiswa").child(dataMhs.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               Toast.makeText(activity, "Data berhasil di hapus", Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(activity, "Data gagal di hapus", Toast.LENGTH_SHORT).show();
                           }
                       });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah yakin ingin menghapus ?" + dataMhs.getNama());
                builder.show();
            }
        });

        holder.cardHasil.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogForm dialog =  new DialogForm(
                        dataMhs.getNama(),
                        dataMhs.getNpm(),
                        dataMhs.getKelas(),
                        dataMhs.getKey(),
                        "Ubah"
                );
                dialog.show(manager, "form");
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class mhsViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvNpm, tvKelas;
        CardView cardHasil;
        ImageView btnHapus;
        public mhsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvNpm = itemView.findViewById(R.id.tv_npm);
            tvKelas = itemView.findViewById(R.id.tv_kelas);
            cardHasil = itemView.findViewById(R.id.card_hasil);
            btnHapus = itemView.findViewById(R.id.hapus);
        }
    }
}

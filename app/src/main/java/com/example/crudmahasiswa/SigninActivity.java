package com.example.crudmahasiswa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity  extends AppCompatActivity {
    private String TAG = SigninActivity.class.getSimpleName();
    private EditText editUsername;
    private EditText editPassword;
    private Button btnSignin;
    private TextView txtSignup;

    private String key;
    private String username;
    private String password;

    private void initUI (){
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        btnSignin = findViewById(R.id.btn_signin);
        txtSignup = findViewById(R.id.txt_signup);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initUI();


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignAccount();
            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SigninActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SignAccount(){
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        Query query = FirebaseUtils.getReference(FirebaseUtils.ACCOUNT_PATH).orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Account account = snapshot.getValue(Account.class);
                        if (account.getUsername().equals(username) && account.getPassword().equals(password)){
                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                     }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getDetails() + " | " + error.getMessage());
            }
        });
    }

}

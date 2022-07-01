package com.example.crudmahasiswa;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private String TAG = SigninActivity.class.getSimpleName();
    private EditText editUsername;
    private EditText editPassword;
    private Button btnSignup;
    private TextView txtSignin;


    private String key;
    private String username;
    private String password;

    private void initUI (){
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        btnSignup = findViewById(R.id.btn_signup);
        txtSignin = findViewById(R.id.txt_signin);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        initUI();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });

        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private Boolean isValid (EditText editText, String data){
        if (!TextUtils.isEmpty(data) && !data.equals("")) {
            return true;
        }else {
            editText.setError("This field cannot be empty.");
            return false;
        }
    }

    private void addAccount (){
        key = FirebaseUtils.getReference(FirebaseUtils.ACCOUNT_PATH).push().getKey();
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        if (isValid(editUsername, username) && isValid(editPassword, password)){
            Account account = new Account(key, username, password);
            FirebaseUtils.getReference(FirebaseUtils.ACCOUNT_PATH).child(key).setValue(account);
            Toast.makeText(SignupActivity.this, "Account has been added", Toast.LENGTH_LONG);
            onBackPressed();
        }

    }



}

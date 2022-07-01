package com.example.crudmahasiswa;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    public static final String ACCOUNT_PATH = "accounts";

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static DatabaseReference getReference (String path){
        return database.getReference();
    }
}

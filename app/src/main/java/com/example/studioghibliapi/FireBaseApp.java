package com.example.studioghibliapi;

import com.google.firebase.database.FirebaseDatabase;

public class FireBaseApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //enable offline data on firebase database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}

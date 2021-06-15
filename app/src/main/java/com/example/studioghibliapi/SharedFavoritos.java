package com.example.studioghibliapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SharedFavoritos extends AppCompatActivity {

/*
    public SharedFavoritos() {

        SharedPreferences sharedPreferences;

        public SharedFavoritos(Context context){
            sharedPreferences = getApplicationContext().getSharedPreferences("filmes_favoritos"Context.MODE_PRIVATE);
        }

        public boolean isFavorite(String id){
            return SharedPreferences.getBoolean("idFilms" + id, false);
        }

        public void setFavorite(String id, boolean favorite){
            sharedPreferences.edit().putBoolean("idFilms" + id, favorite).apply();
        }

        /*Films filme = new Films();
        filme.setId(Integer.parseInt(c.getString(0)));
    }*/
}

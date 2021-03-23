package com.example.studioghibliapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ghibli";
    public static final int DATABASE_VERSION = 1;

    //TABELA FILMS
    public static final String FILMS_TABLE_NAME = "TBfilms";
    public static final String FILMS_COLUMN_ID = "idFilms";
    public static final String FILMS_COLUMN_TITLE = "titleFilms";
    public static final String FILMS_COLUMN_ORIGINAL_TITLE = "originalTitleFilms";
    public static final String FILMS_COLUMN_ORIGINAL_TITLE_ROMANISED = "originalTitleRomanisedFilms";
    public static final String FILMS_COLUMN_DESCRIPTION = "descriptionFilms";
    public static final String FILMS_COLUMN_DIRECTOR = "directorFilms";
    public static final String FILMS_COLUMN_RELEASE_DATE = "releaseDateFilms";
    public static final String FILMS_COLUMN_RUNNING_TIME = "runningTimeFilms";
    public static final String FILMS_COLUMN_RT_SCORE = "rtScoreFilms";

    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_FILMS = "CREATE TABLE " + FILMS_TABLE_NAME + "( " +
                FILMS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                FILMS_COLUMN_TITLE + " TEXT, " +
                FILMS_COLUMN_ORIGINAL_TITLE + " TEXT, " +
                FILMS_COLUMN_ORIGINAL_TITLE_ROMANISED + " TEXT, " +
                FILMS_COLUMN_DESCRIPTION + " TEXT, " +
                FILMS_COLUMN_DIRECTOR + " TEXT, " +
                FILMS_COLUMN_RELEASE_DATE  + " TEXT, "+
                FILMS_COLUMN_RUNNING_TIME +" TEXT, " +
                FILMS_COLUMN_RT_SCORE +" TEXT " + ");";

        db.execSQL(QUERY_FILMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + FILMS_TABLE_NAME + ";" );
        onCreate(db);
    }
    void addFilms (Films films){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FILMS_COLUMN_TITLE, films.getTitle());
        values.put(FILMS_COLUMN_ORIGINAL_TITLE, films.getOriginal());
        values.put(FILMS_COLUMN_ORIGINAL_TITLE_ROMANISED, films.getTitle_romanised());
        values.put(FILMS_COLUMN_DESCRIPTION, films.getDescription());
        values.put(FILMS_COLUMN_DIRECTOR, films.getDirector());
        values.put(FILMS_COLUMN_RELEASE_DATE, films.getYear());
        values.put(FILMS_COLUMN_RUNNING_TIME, films.getTime());
        values.put(FILMS_COLUMN_RT_SCORE, films.getScore());

        db.insert(FILMS_TABLE_NAME, null, values);
        db.close();
    }
}

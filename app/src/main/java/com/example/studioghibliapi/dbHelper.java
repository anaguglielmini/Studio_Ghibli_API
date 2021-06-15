package com.example.studioghibliapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ghiblistudio";
    public static final int DATABASE_VERSION = 5;

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

    //TABELA FAVORITOS
    public static final String FAVORITO_TABLE_NAME = "TBfavoritos";
    public static final String FAVORITO_COLUMN_ID = "idfavorito";


    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_FILMS = "CREATE TABLE " + FILMS_TABLE_NAME + "( " +
                FILMS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                FILMS_COLUMN_TITLE + " TEXT UNIQUE, " +
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
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM TBfilms where idfilms ="+id+"", null );
        return res;
    }
    public List<Films> listaTodosFilmes (){
        List<Films> listarFilmes = new ArrayList();
        String query = "SELECT * FROM " + FILMS_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Films filme = new Films();
                filme.setId(Integer.parseInt(c.getString(0)));
                filme.setTitle(c.getString(1));
                filme.setOriginal(c.getString(2));
                filme.setTitle_romanised(c.getString(3));
                filme.setDescription(c.getString(4));
                filme.setDirector(c.getString(5));
                filme.setYear(c.getString(6));
                filme.setTime(c.getString(7));
                filme.setScore(c.getString(8));

                listarFilmes.add(filme);
            }while(c.moveToNext());
        }return listarFilmes;
    }

    //FAVORITOS


    /*CRUD*/


    public void addToFavoritos (String idfavortio){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO TBfavoritos(IdFavoritos) VALUES('%s');", idfavortio);
        db.execSQL(query);
    }

    public void RemoveFromFavoritos (String idfavortio){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM TBfavoritos WHERE IdFavoritos='%s';", idfavortio);
        db.execSQL(query);
    }

    public boolean isFavorito (String idfavortio){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM TBfavoritos WHERE IdFavoritos='$s';", idfavortio);
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}

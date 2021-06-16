package com.example.studioghibliapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.net.URL;

public class Filme<FireBase> extends AppCompatActivity {

    TextView txtTitulo2, txtOriginal2, txtRomanizado2, txtDesc, txtDiretor2, txtAno2, txtTempo2, txtScore2;
    ImageButton favaz;//apenas a borda do coração, favorito vazio
    ImageButton favc;//coração preenchido, favorito cheio
    int id_To_Update = 0;

    Films films;
    dbHelper db = new dbHelper(this);

    //Favorites
    dbHelper localDB;
    FirebaseDatabase database;
    DatabaseReference filme;
    private Query adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);
        getSupportActionBar().hide();

        txtTitulo2 = findViewById(R.id.txtTitulo2);
        txtOriginal2 = findViewById(R.id.txtOriginal2);
        txtRomanizado2 = findViewById(R.id.txtRomanizado2);
        txtDesc = findViewById(R.id.txtDesc);
        txtDiretor2 = findViewById(R.id.txtDiretor2);
        txtAno2 = findViewById(R.id.txtAno2);
        txtTempo2 = findViewById(R.id.txtTempo2);
        txtScore2 = findViewById(R.id.txtScore2);

        favaz = findViewById(R.id.btnFav);
        favc = findViewById(R.id.btnDesfav);

/*

        favaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    db.addToFavoritos(new Favoritos());
                    Toast.makeText(Filme.this, "Filme adicionado aos favoritos", Toast.LENGTH_SHORT).show();

        });**/

        //RESULTADO DA PESQUISA DE FILME
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int Value = extras.getInt("id");
            films = new Films();

            if(Value > 0){
                Cursor rs  = db.getData(Value);
                id_To_Update = Value;
                if(rs.moveToFirst()){
                    //Código original
                    /*
                    films.setTitle(rs.getString(rs.getColumnIndex(db.FILMS_COLUMN_TITLE)));
                    films.setOriginal(rs.getString(rs.getColumnIndex(db.FILMS_COLUMN_ORIGINAL_TITLE)));
                    films.setTitle_romanised(rs.getString(rs.getColumnIndex(db.FILMS_COLUMN_ORIGINAL_TITLE_ROMANISED)));
                    films.setDescription(rs.getString(rs.getColumnIndex(db.FILMS_COLUMN_DESCRIPTION)));
                    films.setDirector(rs.getString(rs.getColumnIndex(db.FILMS_COLUMN_DIRECTOR)));
                    films.setYear(rs.getString(rs.getColumnIndex(db.FILMS_COLUMN_RELEASE_DATE)));
                    films.setTime(rs.getString(rs.getColumnIndex(db.FILMS_COLUMN_RUNNING_TIME)));
                    films.setScore(rs.getString(rs.getColumnIndex(db.FILMS_COLUMN_RT_SCORE)));*/

                    //código "limpo" segundo o Android Studio, apenas para não ter mais warnings de static member accessed via instance reference
                    films.setId(rs.getInt(rs.getColumnIndex(dbHelper.FILMS_COLUMN_ID)));
                    films.setTitle(rs.getString(rs.getColumnIndex(dbHelper.FILMS_COLUMN_TITLE)));
                    films.setOriginal(rs.getString(rs.getColumnIndex(dbHelper.FILMS_COLUMN_ORIGINAL_TITLE)));
                    films.setTitle_romanised(rs.getString(rs.getColumnIndex(dbHelper.FILMS_COLUMN_ORIGINAL_TITLE_ROMANISED)));
                    films.setDescription(rs.getString(rs.getColumnIndex(dbHelper.FILMS_COLUMN_DESCRIPTION)));
                    films.setDirector(rs.getString(rs.getColumnIndex(dbHelper.FILMS_COLUMN_DIRECTOR)));
                    films.setYear(rs.getString(rs.getColumnIndex(dbHelper.FILMS_COLUMN_RELEASE_DATE)));
                    films.setTime(rs.getString(rs.getColumnIndex(dbHelper.FILMS_COLUMN_RUNNING_TIME)));
                    films.setScore(rs.getString(rs.getColumnIndex(dbHelper.FILMS_COLUMN_RT_SCORE)));
                }
            } else{
                Toast.makeText(Filme.this,"Ops! Ocorreu um erro.", Toast.LENGTH_SHORT).show();
            }
            txtTitulo2.setText(films.getTitle());
            txtOriginal2.setText(films.getOriginal());
            txtRomanizado2.setText(films.getTitle_romanised());
            txtDesc.setText(films.getDescription());
            txtDiretor2.setText(films.getDirector());
            txtAno2.setText(films.getYear());
            txtTempo2.setText(films.getTime());
            txtScore2.setText(films.getScore());
        }
    }
    /*public void favoritar(View view){
        db.addFavorito(films);

        favaz.setVisibility(View.GONE);
        favc.setVisibility(View.VISIBLE);


        favaz.setVisibility(View.GONE);
        favc.setVisibility(View.VISIBLE);

    }

    public void desfavoritar(View view){
        favaz.setVisibility(View.VISIBLE);
        favc.setVisibility(View.GONE);
    }

    public void backButton(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }*/


}
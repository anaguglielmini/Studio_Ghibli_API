package com.example.studioghibliapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText nmFilme;
    private TextView nmTitulo;
    ListView listViewPesquisa;
    dbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nmFilme = findViewById(R.id.ghibli_input);
        nmTitulo = findViewById(R.id.txt_nome);
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
        getSupportActionBar().hide();
        listViewPesquisa = (ListView) findViewById(R.id.listViewPesquisa);
    }

    public void buscaGhibli(View view) {
        // Recupera a string de busca
        String queryString = nmFilme.getText().toString();
        // esconde o teclado quando clica no botão
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Verifica o status da conexão de rede
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        /* Se a rede estiver disponivel e o campo de busca não estiver vazio
         iniciar o Loader LoadGhibli */
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            nmTitulo.setText(R.string.loading);
        }
        // atualiza a textview para informar que não há conexão ou termo de busca
        else {
            if (queryString.length() == 0) {
                nmTitulo.setText(R.string.no_search_term);
            } else {
                nmTitulo.setText(R.string.no_network);
            }
        }
    }
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new LoadGhibli(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            // Obtem o JSONArray dos itens de films
            JSONArray itemsArray = new JSONArray(data);
            // inicializa o contador
            int i = 0;
            String titulo = null;
            String tituloOriginal = null;
            String tituloRomanisado= null;
            String descricao = null;
            String diretor = null;
            String ano = null;
            String duracao = null;
            String score = null;
            // Procura pro resultados nos itens do array
            while (i < itemsArray.length() &&
                    titulo == null && tituloOriginal == null && tituloRomanisado == null && descricao == null && diretor == null && ano == null && duracao == null && score == null) {
                // Obtem a informação
                JSONObject ghibli = itemsArray.getJSONObject(i);
                //  Obter autor e titulo para o item,
                // erro se o campo estiver vazio
                try {
                    titulo = ghibli.getString("title");
                    tituloOriginal = ghibli.getString("original_title");
                    tituloRomanisado = ghibli.getString("original_title_romanised");
                    descricao = ghibli.getString("description");
                    diretor = ghibli.getString("director");
                    ano = ghibli.getString("release_date");
                    duracao = ghibli.getString("running_time");
                    score = ghibli.getString("rt_score");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // move para a proxima linha
                i++;
                try{
                    db.addFilms(new Films(titulo, tituloOriginal, tituloRomanisado, descricao, diretor, ano, duracao, score));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //mostra o resultado quando possivel.
            if (titulo != null) {
                nmTitulo.setText(descricao);
            } else {
                // If none are found, update the UI to show failed results.
                nmTitulo.setText(R.string.no_results);
            }
        } catch (Exception e) {
            // Se não receber um JSOn valido, informa ao usuário
            nmTitulo.setText(R.string.no_results);
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // obrigatório implementar, nenhuma ação executada
    }

}
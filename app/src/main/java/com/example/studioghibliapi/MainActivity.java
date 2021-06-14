package com.example.studioghibliapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText nmFilme;
    private TextView nmTitulo;
    ListView listViewPesquisa;
    List<Films> films;
    dbHelper db = new dbHelper(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    Drawable mClearButtonImage;

    public ghibli_input(Context context) {
        super(context);
        init();
    }

    public ghibli_input(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ghibli_input(Context context,
                             AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //metodo para inicialização do componente
    private void init() {
        // Inicializa o Drawable
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_clear_opaque_24, null);

        // define a ação do clique do botãoclique do botão.
        setOnTouchListener(new View.OnTouchListener() {


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // getCompoundDrawables()[2] :retorna um array de Drawables contendo start, top, end e bottom
                // Se o Drawabale estiver no final do texto: [2].

                if ((getCompoundDrawablesRelative()[2] != null)) {
                    float clearButtonStart; // Linguagens LTR
                    float clearButtonEnd;  //Linguagens RTL
                    //um boolean que pode ser atualizado dinamicamente
                    AtomicBoolean isClearButtonClicked = new AtomicBoolean(false);
                    // detecta a direção do toque
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        // Se RTL, cola o botão na esquerda
                        clearButtonEnd = mClearButtonImage
                                .getIntrinsicWidth() + getPaddingStart();
                        // se o toque ocorrer antes do fim do botão
                        //  isClearButtonClicked definido como true.
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked.set(true);
                        }
                    } else {
                        // Se o layout é LTR.

                        clearButtonStart = (getWidth() - getPaddingEnd()
                                - mClearButtonImage.getIntrinsicWidth());
                        // Se o toque ocorrer depois do inicio do botão
                        // isClearButtonClicked = true.
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked.set(true);
                        }

                        // verifica o clique do botão
                        if (isClearButtonClicked.get()) {
                            // Verifica o ACTION_DOWN (sempre ocorre antes do ACTION_UP).
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                // troca a versão do botão
                                mClearButtonImage =
                                        ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.ic_baseline_clear_24, null);
                                showClearButton();
                            }
                            // Verifica o  ACTION_UP.
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                // Troca pela versão opaca
                                mClearButtonImage =
                                        ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.ic_baseline_clear_opaque_24, null);
                                // limpa o texto
                                getText().clear();
                                //esconde o botão
                                hideClearButton();
                                return true;
                            }
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }
        });

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

        ListarFilmes();

        listViewPesquisa.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
                int id_To_Search = films.get(position).id;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), Filme.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
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
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    this.finish();
                    db.addFilms(new Films(titulo, tituloOriginal, tituloRomanisado, descricao, diretor, ano, duracao, score));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //mostra o resultado quando possivel.
            if (titulo != null) {
                nmTitulo.setText("Nós encontramos: " + titulo);
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
    private void ListarFilmes(){
        films = db.listaTodosFilmes();
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        listViewPesquisa.setAdapter(adapter);

        for(Films c: films){
            arrayList.add(c.getTitle() + " - " + c.getYear());
            adapter.notifyDataSetChanged();
        }
    }

}
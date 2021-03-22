package com.example.studioghibliapi;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    // Constantes utilizadas pela API
    // URL para a API do Studio Ghibli.
    private static final String GHIBLI_URL = "https://ghibliapi.herokuapp.com/#tag/";
    // Parametros da string de Busca
    private static final String QUERY_PARAM = "q";
    // Limitador da qtde de resultados
    private static final String MAX_RESULTS = "maxResults";
    // Parametro do tipo de impressão
    private static final String TIPO_IMPRESSAO = "printType";
    static String buscaInfosGhibli(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String ghibliJSONString = null;
        try {
            // Construção da URI de Busca
            Uri builtURI = Uri.parse(GHIBLI_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(TIPO_IMPRESSAO, "ghibli")
                    .build();
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Cria o buffer para o input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // Usa o StringBuilder para receber a resposta.
            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Adiciona a linha a string.
                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                // se o stream estiver vazio não faz nada
                return null;
            }
            ghibliJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // escreve o Json no log
        Log.d(LOG_TAG, ghibliJSONString);
        return ghibliJSONString;
    }
}

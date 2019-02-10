package com.example.androidprotocolbuffers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase que realizará una petición tipo GET.
 */
public class GetRequestTask extends AsyncTask<Void, Void, InputStream> {

    // Referencia de la clase ejecutora.
    private WeakReference<IRequest> weakReference;
    // Url del servidor.
    private String url;

    /**
     * Constructor de la Tarea.
     * @param request Intancia de la clase ejecutora.
     * @param url Url hacia donde haremos la petición.
     */
    GetRequestTask(IRequest request, String url) {
        this.weakReference = new WeakReference<>(request);
        this.url = url;
    }

    /**
     * Hilo ejecutado en background.
     * @param voids Parametros.
     * @return InputStream Resultado de la petición.
     */
    protected InputStream doInBackground(Void... voids) {
        InputStream inputStream = null;
        try {
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            } finally {
                urlConnection.disconnect();
            }
        }
        catch(Exception e){
            Log.e("doInBackground", e.toString());
        }

        return inputStream;
    }

    /**
     * Método que se ejecuta al terminar doInBackground.
     * @param inputStream Resultado de la petición.
     */
    protected void onPostExecute(InputStream inputStream) {

        if (this.weakReference != null) {
            this.weakReference.get().GetRequest(inputStream);
            this.weakReference.clear();
            this.weakReference = null;
        }
    }
}
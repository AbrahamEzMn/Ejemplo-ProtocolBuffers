package com.example.androidprotocolbuffers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase que realizará una petición tipo POST.
 */
public class PostRequestTask extends AsyncTask<byte[], Void, InputStream> {

    // Referencia de la clase ejecutora.
    private WeakReference<IRequest> weakReference;
    // Url del servidor.
    private String url;

    /**
     * Constructor de la Tarea.
     * @param request Intancia de la clase ejecutora.
     * @param url Url hacia donde haremos la petición.
     */
    PostRequestTask(IRequest request, String url)
    {
        this.weakReference = new WeakReference<>(request);
        this.url = url;
    }

    /**
     * Hilo ejecutado en background.
     * @param messages Lista de messages.
     * @return InputStream Resultado de la petición.
     */
    protected InputStream doInBackground(byte[]... messages) {
        BufferedInputStream inputStream = null;

        try {
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-protobuf");

                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());

                DataOutputStream wr = new DataOutputStream(out);
                wr.write(messages[0]);
                wr.flush();
                wr.close();

                inputStream = new BufferedInputStream(urlConnection.getInputStream());

            } finally {
                urlConnection.disconnect();
            }
        }
        catch(Exception e){
            Log.e("app", e.toString());
        }

        return inputStream;
    }

    /**
     * Método que se ejecuta al terminar doInBackground.
     * @param inputStream Resultado de la petición.
     */
    protected void onPostExecute(InputStream inputStream) {
        if (this.weakReference != null) {
            this.weakReference.get().PostRequest(inputStream);
            this.weakReference.clear();
            this.weakReference = null;
        }
    }
}
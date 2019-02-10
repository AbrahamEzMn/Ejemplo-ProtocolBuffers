package com.example.androidprotocolbuffers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import messages.PersonOuterClass;

public class MainActivity extends AppCompatActivity implements IRequest {

    public static final String URL = "http://192.168.1.79:3000/message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetRequestTask(this, URL).execute();

        PersonOuterClass.Person per = PersonOuterClass.Person.newBuilder()
                .setName("abraham")
                .build();
        new PostRequestTask(this, URL).execute(per.toByteArray());
    }

    /**
     * Implementación de una petición GET.
     * @param inputStream Respuesta de la petición.
     */
    @Override
    public void GetRequest(InputStream inputStream) {
        PersonOuterClass.Person persona;
        try {
            persona = PersonOuterClass.Person.parseFrom(inputStream);
            Log.d("GetRequest", persona.toString());
        }
        catch(Exception e){
            Log.e("GetRequest", e.toString());
        }
    }


    /**
     * Implementación de una petición POST.
     * @param inputStream Respuesta de la petición.
     */
    @Override
    public void PostRequest(InputStream inputStream) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String str;
            while ((str = bufferedReader.readLine()) != null)
                stringBuilder.append(str);

            bufferedReader.close();
        }
        catch(Exception e) {
            Log.e("PostRequest", e.toString());
        }

        Log.d("PostRequest", stringBuilder.toString());
    }

}

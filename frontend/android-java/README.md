# Protocol-Buffers con Android (Java)

Versión

* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3

## Creando el proyecto

Para este ejercicio agregaremos la libreria Protocol-Buffers en un proyecto de Android y para esto tendremos que crear algunsa carpetas dentro de nuestro proyecto, dentro la carpeta `app/src/` crearemos la carpeta `messages` donde pondremos nuestros prototipos de esquemas y  otra con el mismo nombre en la carpeta `app/src/main/java`, esta nos servirá para poder colocar nuestros esquemas generados en Java:

```bash
app
 └─src
    ├─main
    │   └─java
    │       └─messages
    └─messages
```

A parte de eso necesitaremos el permiso para poder conectarnos a Internet desde nuestro dispositivo y para agregaremos la siguiente linea dentro de nuestro `AndroidManifest.xml`:

```xml
<manifest>
    ...
    <uses-permission android:name="android.permission.INTERNET" />
</manifest>
```

## Instalando la librerias de  Google-Protobuf

Para poder utilizar las clases que vamos a generar tendremos que agregar dentro de del archivo `build.grade` presente en la carpeta `app` la siguiente implementación dentro de la seccion de `dependencies`: 

```bash
dependencies {
    ...
    implementation 'com.google.protobuf:protobuf-java:3.6.1'
}
```

## Creando los esquemas

En la carpeta de `app/src/messages` generaremos tres archivos con los siguientes datos:
El archivo llamado `person.proto` que contendrá la clase Persona, donde especificaremos los datos que va a tener así como del nombre del paquete.

```bas
syntax = "proto3";
import "phoneNumber.proto";
package messages;

message Person {
  string name = 1;
  int32 id = 2;
  string email = 3;
  repeated PhoneNumber phone = 4;
}
```

Otro archivo llamado `phoneNumber.proto`, en el pondremos el estructura de la clase de Teléfono.

```bas
syntax = "proto3";
import "phoneType.proto";
package messages;


message PhoneNumber {
    string number = 1;
    PhoneType type = 2;
}
```

Y por último `phoneType.proto` que solo tendrá los tipos de teléfonos.

```bas
syntax = "proto3";
package messages;

enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
}
```

Y para generar las clases en Java usaremos el siguiente comando:

```bash
protoc --proto_path=./app/src/messages/ --java_out=./app/src/main/java/ ./app/src/messages/*.proto
```

Esto nos generará tres archivos en la carpeta de `app/src/main/java/messages/`.

## Creando las clases para realizar una peitición al servidor.

Para poder usar nuestras clases tendremos que crear una estructura para poder realizar llamadas asincronas a un servidor Http y primero crearemos una interfaz para poder usar los métodos en cualquier clase, esta interfaz se llamará `IRequest` con la siguiente estructura:

```java
import java.io.InputStream;

/**
 * Interfaz que implementará las peticiones de un Request.
 */
public interface IRequest {
    /**
     * Hace referencia a la implementación de una petición GET.
     * @param inputStream Respuesta de la petición.
     */
    void GetRequest(InputStream inputStream);

    /**
     * Hace referencia a la implementación de una petición POST.
     * @param inputStream Respuesta de la petición.
     */
    void PostRequest(InputStream inputStream);
}
```

El siguiente paso será crear la clase que realizará las peticiones tipo `GET` al servidor y esta estará basada en la clase `AsyncTask`, esta se llamará `GetRequestTask` y tendrá una referecia débil a la clase que la ejecuto, el código seria el siguiente:

```java
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
```

Tomando de base esta clase crearemos otra que se llamará `PostRequestTask` que realize peticiones tipo `POST` de la misma manera:

```java
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
```

Estas clases retornarán objetos del tipo `InputStream` a las clases ejecutoras, el cual nos permitira manipular la respuesta como lo necesitemos.

## Usando los esquemas en la aplicación

Ya teniendo las todas las clases creadas procederemos a implementarlas en un `Activity` para ver su completo funcionamiento.

Lo primero a realizar sera implementar la Interfaz `IRequest` a nuestro activity y lo haremos de la siguiente forma:

```java
public class MainActivity extends AppCompatActivity implements IRequest {
```
Esto nos pedira implementar los métodos `GetRequest` y `PostRequest` dentro de nuestro activity pero los crearemos hasta el final.

Ademas de eso agregaremos una constante donde guardaremos la URL a donde realizaremos la petición de nuestro servidor:

```java
public class MainActivity extends AppCompatActivity implements IRequest {
    
    public static final String URL = "http://<HOST>:<PUERTO>/message";
    
    ...

}
```

Y dentro del método `onCreate` ejecutaremos las peticiones tipo `GET` y `POST` de la siguiente manera:

```java
public class MainActivity extends AppCompatActivity implements IRequest {
    
    ...
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        ...

        // Ejecutamos una petición tipo GET.
        new GetRequestTask(this, URL).execute();

        //Creamos una persona para enviarla en el body de una petición tipo POST.
        PersonOuterClass.Person per = PersonOuterClass.Person.newBuilder()
                .setName("Nombre de Usuario")
                .setEmail("correo@electronico.com")
                .setId(1)
                .addPhone(
                        PhoneNumberOuterClass.PhoneNumber.newBuilder()
                        .setType(PhoneTypeOuterClass.PhoneType.HOME)
                        .setNumber("1234567890")
                        .build()
                )
                .build();

        // Ejecutamos la petición tipo POST y enviamos a la persona como parametro.
        new PostRequestTask(this, URL).execute(per.toByteArray());
    }

    ...
}
```

### Recibiendo un message serializado.

El primer método a crear dentro de nuestro activity será el `GetRequest` donde recibiremos una persona dentro de un `ImputStream`, por lo tanto agregaremos a la clase de la siguiente forma:

```java
public class MainActivity extends AppCompatActivity implements IRequest {

    ...

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
}
```
 
### Enviado datos serializados

Y por último método a implementar será el `PostRequest` donde enviamos a una persona y reibiremos el nombre de ella dentro de un `ImputStream`, por lo tanto funcionará de la siguiente forma:

```java
public class MainActivity extends AppCompatActivity implements IRequest {

    ...

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
```

Para este ejemplo se utilizó un servidor creado en NodeJs como ejemplo que describo en esta [liga](https://github.com/AbrahamEzMn/Example-ProtocolBuffers/tree/master/backend/nodejs).  

## Referencias
[https://docs.oracle.com/javase/7/docs/api/java/lang/ref/WeakReference.html](https://docs.oracle.com/javase/7/docs/api/java/lang/ref/WeakReference.html) \
[https://developer.android.com/reference/android/os/AsyncTask](https://developer.android.com/reference/android/os/AsyncTask) \
[https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html](https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html) \
[https://developer.android.com/reference/java/net/HttpURLConnection](https://developer.android.com/reference/java/net/HttpURLConnection) \
[https://github.com/protocolbuffers/protobuf/tree/master/java](https://github.com/protocolbuffers/protobuf/tree/master/java)

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

Esto nos generará tres archivos por clase en la carpeta de `app/src/main/java/messages/`.

## Usando los esquemas en la aplicación



## Referencias
[https://docs.oracle.com/javase/7/docs/api/java/lang/ref/WeakReference.html](https://docs.oracle.com/javase/7/docs/api/java/lang/ref/WeakReference.html) \
[https://developer.android.com/reference/android/os/AsyncTask](https://developer.android.com/reference/android/os/AsyncTask) \
[https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html](https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html) \
[https://developer.android.com/reference/java/net/HttpURLConnection](https://developer.android.com/reference/java/net/HttpURLConnection) \
[https://github.com/protocolbuffers/protobuf/tree/master/java](https://github.com/protocolbuffers/protobuf/tree/master/java)

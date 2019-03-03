# Protocol-Buffers con Android (Xamarin)

Versión

* Os: Windows 10
* IDE: Microsoft Visual Studio Community 2017
* Xamarin: 4.12.3.80
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3

## Creando el proyecto

Para este ejercicio agregaremos la libreria Protocol-Buffers en un proyecto de Android-Xamarin y para esto tendremos que crear algunas carpetas dentro de nuestro proyecto, en la raíz de nuestra solución crearemos la carpeta `messages` donde pondremos nuestros prototipos de esquemas y otra con el mismo nombre dentro del proyecto en la carpeta `android-xamarin/`, esta nos servirá para poder colocar nuestros esquemas generados en C#:

```bash
android-xamarin
 ├─android-xamarin
 │   └─messages       
 └─messages
```

A parte de eso necesitaremos el permiso para poder conectarnos a Internet desde nuestro dispositivo y para agregaremos la siguiente linea dentro de nuestro `AndroidManifest.xml`:

```xml
<manifest>
    ...
    <uses-permission android:name="android.permission.INTERNET" />
</manifest>
```

## Instalando la librerias de Google-Protobuf

Para poder utilizar las clases que vamos a generar tendremos que agregar el paquete de `Google.Protobuf` a nuestro proyecto, lo podremos agregar por medio del `Administrador de Paquetes NuGet` o por la `Consola del Administrador de Paquetes` ejecutando el siguiente comando:

```bash
PM> Install-Package Google.Protobuf -Version 3.6.1
```

## Creando los esquemas

En la carpeta de `/messages` ubicada en la raíz de la solución generaremos tres archivos con los siguientes datos:
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
protoc --proto_path=./messages --csharp_out=./android-xamarin/Messages ./messages/person.proto ./messages/phoneNumber.proto ./messages/phoneType.proto
```

Esto nos generará tres archivos en la carpeta de `android-xamarin/android-xamarin/messages/`.

## Referencias

[https://www.nuget.org/packages/Google.Protobuf](https://www.nuget.org/packages/Google.Protobuf) \
[https://developers.google.com/protocol-buffers/docs/csharptutorial](https://developers.google.com/protocol-buffers/docs/csharptutorial) \
[https://developers.google.com/protocol-buffers/docs/reference/csharp-generated](https://developers.google.com/protocol-buffers/docs/reference/csharp-generated) \
[https://github.com/protocolbuffers/protobuf/tree/master/csharp](https://github.com/protocolbuffers/protobuf/tree/master/csharp) \
(https://docs.microsoft.com/en-us/xamarin/xamarin-forms/data-cloud/consuming/rest) [https://docs.microsoft.com/en-us/xamarin/xamarin-forms/data-cloud/consuming/rest]\
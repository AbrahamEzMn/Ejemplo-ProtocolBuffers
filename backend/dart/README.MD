# Protocol-Buffers en DART

Versión

* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3
* Dart: 2.1.0

## Creación del proyecto

Para crear este ejemplo necesitaremos 3 carpetas, llamadas:  

* `bin`: Aqui guardaremos las clases funcionales.
* `proto`: Aquí guardaremos los archivos generados de los esquemas.
* `messages`: Aquí guardaremos los esquemas de los `messages`.

Seguirá la siguiente estructura:

```bash
DART
  ├─bin
  │  └─proto
  └─messages
```

## Incializamos el proyecto

El primer paso será crear el archivo llamado `pubspec.yaml`, donde establecemos el nombre de la aplicación y las dependencias que vamos a usar:

```bash
name: my_app
dependencies:
    protobuf: ^0.13.0
```

Despues ejecutamos el siguiente comando para descargar las dependencias y al mismo tiempo inicializar el proyecto:

```bash
pub get
```

## Instalando el pluggin protoc_plugin

Este pluggin sera de necesario para generar los esquemas en `DART` y para poder instalarlo solo necesitamos ejecutar el siguiete comando:

```bash
pub global activate protoc_plugin
```

Y para poder ejecutarlo junto con el comando `protoc` necesitaremos usar la carpeta de `.pub-cache\bin` de manera global temporalmente y para eso lo tendremos que exportar la carpeta contenedora del plugin dentro de nuestro bash:

```bas
export PATH="$PATH":"$HOME/.pub-cache/bin"
```

## Creando los esquemas de las clases

En la carpeta de `messages` generaremos tres archivos con los siguientes datos:
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

Y para generar las clases en DART usaremos el siguiente comando:

```bash
protoc --proto_path=./messages/ --dart_out=./bin/proto ./messages/*.proto
```

Esto nos generará cuatro archivos por clase en la carpeta de `bin/protoc`, los archivos principales a utilizar sera los de extencion `.pb.dart`.

## Referencias

[https://www.dartlang.org/tutorials/server/httpserver](https://www.dartlang.org/tutorials/server/httpserver)\
[https://pub.dartlang.org/packages/protoc_plugin#-installing-tab-](https://pub.dartlang.org/packages/protoc_plugin#-installing-tab-)\
[https://pub.dartlang.org/packages/protobuf#-installing-tab-](https://pub.dartlang.org/packages/protobuf#-installing-tab-)\
[https://developers.google.com/protocol-buffers/docs/darttutorial](https://developers.google.com/protocol-buffers/docs/darttutorial)

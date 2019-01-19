# Usando Protocol-Buffers en GO

Versión
* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3
* Go: go1.10.3 linux/amd64

## Creando el proyecto base

El primer paso es crear con la siguiente estructura:
```
Go
 ├─bin
 ├─messages
 └─src
    └─proto

```

Las carpetas `bin` y `src` son parte de la estructura básica de un proyecto en `go`, mientras que `messages` es donde guardaremos los esquemas de las clases y en `src/proto` guardaremos los esquemas generados con el comando de `protoc` con salida en `go`.  

Lo que sigue es exportar la variable `GOPATH` con el siguiente comando:
```
$ export GOPATH=UBICACION_DEL-PROYECTO
```

Instalamos el plugin de `protoc` para `go` con el siguiente comando:
```
$ go get github.com/golang/protobuf/protoc-gen-go
```

## Creando el message
En la carpeta de `messages` generaremos tres archivos con los siguientes datos:

El archivo llamado `person.proto` que contendrá la clase Persona donde especificaremos los datos que va a tener, así como del nombre del paquete.
```
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
```
syntax = "proto3";
import "phoneType.proto";
package messages;


message PhoneNumber {
    string number = 1;
    PhoneType type = 2;
}
```

Y por último `phoneType.proto` que solo tendrá los tipos de teléfonos.
```
syntax = "proto3";
package messages;

enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
}
```

```
$ protoc --proto_path=./messages/ --go_out=./src/proto/messages ./messages/*.proto
```

## Referencias

[https://golang.org/doc/code.html](https://golang.org/doc/code.html)<br />
[https://github.com/golang/protobuf/](https://github.com/golang/protobuf/)<br />
[https://developers.google.com/protocol-buffers/docs/reference/go-generated]
(https://developers.google.com/protocol-buffers/docs/reference/go-generated)<br />
[http://dlintw.github.io/gobyexample/public/http-client.html](http://dlintw.github.io/gobyexample/public/http-client.html)
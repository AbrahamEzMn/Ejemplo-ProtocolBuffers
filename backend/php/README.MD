# Protocol-Buffers con PHP

Versión
* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3
* PHP: 7.2.10
* Composer: 1.6.5

## Creando las carpetas
Crearemos las carpetas de `messages` donde guardaremos los esquemas de las clases y otra llamada `proto`, en ella guardaremos los esquemas generados en PHP
```
PHP
 ├─messages
 └─proto
```

## Inicializando el proyecto

Para usar la libreria de `google/protobuf` necesitaremos usar el administrador de dependencias [composer](https://getcomposer.org/download/) y ejecutar el siguiente comando:
```
$ composer init
```

Despues agregaremos la dependencia de el proyecto de `google/protobuf` en el archivo `composer.json` que se generó al terminar de ejecutar el comando `init` y agregaremos la siguiente linea en los corchetes del `require`:
```
"google/protobuf":"*"
```

Y entonces instalaremos la libreria ejecutando el siguiente comando:
```
$ composer update
```

## Creando los esquemas
En la carpeta de `messages` generaremos tres archivos con los siguientes datos:
El archivo llamado `person.proto` que contendrá la clase Persona, donde especificaremos los datos que va a tener así como del nombre del paquete.
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

Para generar las clases en PHP con protoc usaremos el siguiente comando:
```
$ protoc --proto_path=./messages/ --php_out=./proto/ ./messages/*.proto
```

Esto nos generará las carpetas `Messages` y `GPBMetadata` dentro de la carpeta `proto`, aqui se encontrarán nuestros esquemas.

El siguiente paso será incluir los archivos generados en esas carpetas dentro de nuestro proyecto, para eso usaremos el archivo `composer.json` y las agregaremos a su [autoload](https://getcomposer.org/doc/01-basic-usage.md#autoloading), especificando que el namespace de `Messages` se encuentra en la ruta de `./proto/Messages/` y `GPBMetadata` en `./proto/GPBMetadata/`:
```
{
    "autoload": {
        "psr-4": {
            "Messages\\":"./proto/Messages/",
            "GPBMetadata\\":"./proto/GPBMetadata/"
        }
    }
}
```
Despues volvemos a generar los archivos de autolad con el siguiente comando:
```
$ composer dump-autoload -o
```

## Referencias
[https://github.com/protocolbuffers/protobuf/tree/master/php](https://github.com/protocolbuffers/protobuf/tree/master/php)<br />
[https://developers.google.com/protocol-buffers/docs/reference/php-generated](https://developers.google.com/protocol-buffers/docs/reference/php-generated)<br />
[https://getcomposer.org/doc/01-basic-usage.md#autoloading](https://getcomposer.org/doc/01-basic-usage.md#autoloading) <br />
[http://www.jc-mouse.net/desarrollo-web/crea-un-servicio-web-rest-con-php-y-mysql-parte-2](http://www.jc-mouse.net/desarrollo-web/crea-un-servicio-web-rest-con-php-y-mysql-parte-2)
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

```bash
PHP
 ├─messages
 └─proto
```

## Inicializando el proyecto

Para usar la libreria de `google/protobuf` necesitaremos usar el administrador de dependencias [composer](https://getcomposer.org/download/) y ejecutar el siguiente comando:

```bas
$ composer init
```

Despues agregaremos la dependencia de el proyecto de `google/protobuf` en el archivo `composer.json` que se generó al terminar de ejecutar el comando `init` y agregaremos la siguiente linea en los corchetes del `require`:

```bas
"google/protobuf":"*"
```

Y entonces instalaremos la libreria ejecutando el siguiente comando:

```bas
$ composer update
```

## Creando los esquemas

En la carpeta de `messages` generaremos tres archivos con los siguientes datos:
El archivo llamado `person.proto` que contendrá la clase Persona, donde especificaremos los datos que va a tener así como del nombre del paquete.

```proto
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

```proto
syntax = "proto3";
import "phoneType.proto";
package messages;


message PhoneNumber {
    string number = 1;
    PhoneType type = 2;
}
```

Y por último `phoneType.proto` que solo tendrá los tipos de teléfonos.

```proto
syntax = "proto3";
package messages;

enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
}
```

Para generar las clases en PHP con protoc usaremos el siguiente comando:

```bash
$ protoc --proto_path=./messages/ --php_out=./proto/ ./messages/*.proto
```

Esto nos generará las carpetas `Messages` y `GPBMetadata` dentro de la carpeta `proto`, aqui se encontrarán nuestros esquemas.

El siguiente paso será incluir los archivos generados en esas carpetas dentro de nuestro proyecto, para eso usaremos el archivo `composer.json` y las agregaremos a su [autoload](https://getcomposer.org/doc/01-basic-usage.md#autoloading), especificando que el namespace de `Messages` se encuentra en la ruta de `./proto/Messages/` y `GPBMetadata` en `./proto/GPBMetadata/`:

```json
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

```proto
$ composer dump-autoload -o
```

## Usando los messages del lado del servidor

Para este ejemplo crearemos un archivo llamado `index.php` y lo primero a hacer es importar el `autoload.php` ubicado enla carpta de `vendor` por medio del siguiente código:

```php
<?php

require_once "./vendor/autoload.php";
```

Despues especificaremos que usaremos las clases de los esquemas generados llamados `Person`, `PhoneNumber` y `PhoneType` contenidos en el namespace de `Messages`:

```php
use Messages\Person;
use Messages\PhoneNumber;
use Messages\PhoneType;
```

Entonces espeficamos la respuesta a mostrar dependiendo del tipo de petición:

```php
//Especificamos que la respuesta tendrá como contenido  
header('Content-Type: application/x-protobuf');

// Obtenemos el tipo de la petición
$method = $_SERVER['REQUEST_METHOD'];
switch ($method) {
    // En caso de la petición GET ejecutaremos el método llamado get.
    case 'GET':
        echo get();
        break;
    // En caso de la petición POST ejecutaremos el método llamado post.
    case 'POST':
        echo post();
        break;
}
```

### Enviar el message serializado

El método `get` enviará los datos de un usuario serializado de la siguiente forma:

```php

/**
 * Administra las peticiones tipo GET.
 *
 * @return string Message serializado en bytes guardados en un string.
 */
function get(): string {

    // Creamos a la persona a enviar
    $person = (new Person)
            // Colocamos un nombre.
            ->setName("Nombre 1")
            // Asignamos el correo.
            ->setEmail("correo@electronico.com")
            // Creamos la lista de teléfonos.
            ->setPhone(
                [
                    // Creamos un teléfono de casa.
                    (new PhoneNumber)
                        ->setNumber("1234561230")
                        ->setType(PhoneType::HOME),
                    // Creamos un teléfono de trabajo.
                    (new PhoneNumber)
                        ->setNumber("0987654321")
                        ->setType(PhoneType::WORK)
                ]
            );

    // Enviamos la persona serializada.
    return $person->serializeToString();
}
```

### Recibiendo un message serializado

Para poder decodificar los messages serilizados usaremos una petición tipo `POST` y como respuesta regresaremos el nombre del usuario:

```php
/**
 * Administra las peticiones tipo POST.
 *
 * @return string Nombre del usuario enviado en el request serializado en bytes.
 */
function post(): string {
    // Obtenemos el contenido del request.
    $body = file_get_contents('php://input');
    // Creamos la persona en donde guardaremos la información.
    $person = new Person();
    // Vaciamos los datos de la persona.
    $person->mergeFromString($body);

    // Regresamos el nombre del usuario.
    return $person->getName();
}
```

## Creando el archivo de prueba

Crearemos un archivo en la raíz de nuestro proyecto para probar los métodos llamado `test.php` y agregaremos las siguientes lineas para hacerlo funcionar:

```php
<?php

require_once "./vendor/autoload.php";

use Messages\Person;
use Messages\PhoneNumber;
use Messages\PhoneType;
```

### Probando el método GET

Para probar el GET agregaremos las siguientes lineas al archivo `test.php`:

```php
// Hacemos una petición tipo GET  
$response = file_get_contents('http://127.0.0.1:8080/protocolbuffers/');

// Creamos una persona.
$person = new Person();
// Vaciamos los datos de la persona.
$person->mergeFromString($response);
// Mostramos los datos serialiados en fomrato json.
echo "TEST GET: {$person->serializeToJsonString()} <br>";
```

Al visualizar este archivo en el navegador nos deberia mostrar la siguiente linea:

```proto
TEST GET: {"name":"Nombre 1","email":"correo@electronico.com","phone":[{"number":"1234561230","type":"HOME"},{"number":"0987654321","type":"WORK"}]}
```  

### Probando el método POST

Para probar el metodo POST necesitamos enviar una persona serializada:

```php
// Creamos a la persona a enviar
$person = (new Person)
                // Colocamos un nombre.
                ->setName("Nombre 2")
                // Asignamos el correo.
                ->setEmail("correo2@electronico2.com")
                // Creamos la lista de teléfonos.
                ->setPhone(
                    [
                        // Creamos un teléfono de casa.
                        (new PhoneNumber)
                            ->setNumber("3216549800")
                            ->setType(PhoneType::HOME),
                    ]
                );
// Creamos las opciones de la petición.
$options = [
    'http' => [
        'header'  => "Content-type: application/x-protobuf\r\n",
        'method'  => 'POST',
        'content' => $person->serializeToString()
    ]
];
// Creamos el contexto.
$context  = stream_context_create($options);
// Ejecutamos la petición.
$result = file_get_contents('http://127.0.0.1:8080/protocolbuffers/', false, $context);
// Mostramos el resultado.
echo "TEST POST: {$result}";
```

Al visualizar este método nos deberia mostrar la siguiente linea:

```proto
TEST POST: Nombre 2
```

## Referencias

[https://github.com/protocolbuffers/protobuf/tree/master/php](https://github.com/protocolbuffers/protobuf/tree/master/php)\
[https://developers.google.com/protocol-buffers/docs/reference/php-generated](https://developers.google.com/protocol-buffers/docs/reference/php-generated)\
[https://getcomposer.org/doc/01-basic-usage.md#autoloading](https://getcomposer.org/doc/01-basic-usage.md#autoloading) \
[http://www.jc-mouse.net/desarrollo-web/crea-un-servicio-web-rest-con-php-y-mysql-parte-2](http://www.jc-mouse.net/desarrollo-web/crea-un-servicio-web-rest-con-php-y-mysql-parte-2)
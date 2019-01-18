# Uso de Protocol Buffers en un servidor NodeJS

Versión
* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3

## Agregando las libreria

Para este proyecto usaremos el administrador de paquetes `npm` y lo iniciamos con el siguiente comando:
```
$ npm init
```



Instalamos la libreria `google-protobuf` de manera local por medio del siguiente comando:
```
$ npm install -d google-protobuf
```

## Creando el esquema de prueba

Creamos un archivo llamado `person.proto` que usaremos de prueba con la siguiente estructura:

```
syntax = "proto3";

message Person {
  string name = 1;
  int32 id = 2;
  string email = 3;

  enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
  }

  message PhoneNumber {
    string number = 1;
    PhoneType type = 2;
  }

  repeated PhoneNumber phone = 4;
}
```

### Crear el message en Javascript

El siguiente paso es crear una carpeta donde ubicaremos las clases llamada `messages`, la clase en Javascript la generaremos  con el comando `protoc` especificando que la clase debe ser *comun* para que se adapte a cualquier proyecto:
```
$ protoc --proto_path=./  --js_out=import_style=commonjs,binary:./messages person.proto
```

Despues de ejecutarlo nos debe generar un archivo llamado `person_pb.js`

## Usando los messages en el servidor

Para este ejemplo usaremos la libreria de `express` y lo instalaremos con:
```
$ npm install -d express
```

Ahora crearemos la base de un servidor basico que nos servirá de prueba llamado `server.js`,  y lo primero que debemos hacer es importar la librerias de `express`, `body-parser`,`google-protobuf` y el message generado `person`, eso lo haremos con agregando las siguientes lineas:
```javascript
var express = require('express');
var bodyParser = require('body-parser');
require('google-protobuf');
require('./messages/person_pb');
```

Despues crearemos una variable de la aplicacion de `express` y usaremos el `bodyParser` especificando que para las peticiones con un `content-type` de `application/x-protobuf` se queden como datos puros:
```javascript
var app = express();
app.use(bodyParser.raw({type: 'application/x-protobuf'}))
```

Creamos el método para lanzar el servidor en el puerto `3000`:
```javascript
app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});
```

### Enviando el message serializado 

El primer método en realizar sera un ejemplo de una petición tipo `get`, en él enviaremos la información de  un usuario:
```javascript
/**
 * Método get
 *
 * @url http://localhost:3000/message
 */
app.get('/message', function (req, res) {

    // Creamos el message
    const person = new proto.Person();
    person.setName("Nombre 1");
    person.setEmail("correo@electronico.com");
    person.setId(1);
    person.getPhoneList().push( new proto.Person.PhoneNumber(["123 456 78 90", proto.Person.PhoneType.HOME]));

    // Especificamos que la peticion llevará un protobuf como tipo de contenido
    res.contentType('application/x-protobuf');

    // Creamos un buffer para enviar el message serializado.
    var buffer = new Buffer(person.serializeBinary());

    // Lanzamos el response
    res.send(buffer);
});
```

### Recibiendo el message serializado

El otro método a crear sera uno tipo `post`, en él veremos como se decodifica los datos serializados y regresaremos el nombre de la persona para verificar el correcto funcionamiento:
```javascript
/**
 * Método de ejemplo.
 * 
 * @url http://localhost:3000/message
 */
app.post('/message', function (req, res, next) {

    //Creamos la persona a partir de la descerizalacion de los datos enviados en el body.
    const person = proto.Person.deserializeBinary(req.body);

    // Visualizamos la persona.
    console.log(person.toObject())

    // Rergesamos el nombre de la persona al cliente.
    res.send(person.getName());
});
``` 

Podremos lanzar el servidor con el siguiente comando:
```
$ node server.js
```

## Haciendo el archivo de prueba.

Para probar nuestro servidor utilizaremos la libreria de `request`, la instalaremos por medio del siguiente comando:
```
$ npm install -D request
``` 


## Referencias

[https://developers.google.com/protocol-buffers/docs/reference/javascript-generated](https://developers.google.com/protocol-buffers/docs/reference/javascript-generated)<br />
[https://github.com/protocolbuffers/protobuf/tree/master/js](https://github.com/protocolbuffers/protobuf/tree/master/js)<br />
[https://nodejs.org/en/docs/guides/getting-started-guide/](https://nodejs.org/en/docs/guides/getting-started-guide/)<br />
[https://expressjs.com/es/starter/hello-world.html](https://expressjs.com/es/starter/hello-world.html)
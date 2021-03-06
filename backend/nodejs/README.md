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
Ademas de eso configuraremos las cabeceras para no tener problemas con el acceso CORS agregando las siguientes lineas:

```javascript
// Configurar cabeceras y cors
app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', 'Authorization, X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Allow-Request-Method');
    res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, DELETE');
    res.header('Allow', 'GET, POST, OPTIONS, PUT, DELETE');
    next();
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

Y luego crearemos un archivo llamado `server_test.js`  e importaremos la libreria `request`, `google-protobuf` y `person_pb` por medio de las siguientes lineas:
```javascript
var request = require('request');

require('google-protobuf');
require('./messages/person_pb');
``` 
### Probando el método GET
El primer método a testear sera el método `GET`, en él recibiremos una persona y mostraremos por completo todos los valores en consola con el siguiente código:
```javascript
request('http://127.0.0.1:3000/message', function (error, response, body) {

    console.log('test get http://127.0.0.1:3000/message');

    console.log('error:', error);
    console.log('statusCode:', response && response.statusCode);
    console.log('body:', body);

    const person = proto.Person.deserializeBinary(new Buffer(body));
    console.log('persona:', person.toObject());
});
```

#### Resultado
Habiendo previamente lanzado el archivo `server.js` ejecutamos el archivo `server_test.js` con el siguiente comando:
```
$ node server_test.js
``` 

Y nos debe mostrar el siguiente resultado:
```
test get http://127.0.0.1:3000/message
error: null
statusCode: 200
body: 
Nombre 1correo@electronico.com"
123 456 78 90
persona: { name: 'Nombre 1',
  id: 1,
  email: 'correo@electronico.com',
  phoneList: [ { number: '123 456 78 90', type: 1 } ] }

```

### Probando el método POST

El siguiente método a testear es el `POST`, en él enviaremos a una persona y obtendremos el nombre que de ella por medio de las siguientes lineas a nuestro archivo `server_test.js`:
```javascript
// Creación de la instancia del message
const person = new proto.Person();

// Asignación de valores
person.setName("Nombre 2");
person.setEmail("correo2@electronico2.com");
person.getPhoneList().push( new proto.Person.PhoneNumber(["449 123 45 67", proto.Person.PhoneType.HOME]))

request.post({
  headers:  {'content-type': 'application/x-protobuf'},
  url:     'http://127.0.0.1:3000/message',
  body:    person.serializeBinary()
}, function(error, response, body){
  console.log('\n\rtest post http://127.0.0.1:3000/message');

  console.log('error:', error);
  console.log('statusCode:', response && response.statusCode);
  console.log('body:', body);
});
```

#### Resultado
Igual que el método anterior, habiendo previamente lanzado el archivo `server.js` ejecutamos el archivo `server_test.js` con el siguiente comando:
```
$ node server_test.js
``` 

Y nos debe mostrar el siguiente resultado:
```
test post http://127.0.0.1:3000/message
error: null
statusCode: 200
body: Nombre 2
```
Mientras que del lado del servidor nos mostrará esto:
```
Example app listening on port 3000!
{ name: 'Nombre 2',
  id: 0,
  email: 'correo2@electronico2.com',
  phoneList: [ { number: '449 123 45 67', type: 1 } ] }

```

## Referencias

[https://developers.google.com/protocol-buffers/docs/reference/javascript-generated](https://developers.google.com/protocol-buffers/docs/reference/javascript-generated) \
[https://github.com/protocolbuffers/protobuf/tree/master/js](https://github.com/protocolbuffers/protobuf/tree/master/js) \
[https://nodejs.org/en/docs/guides/getting-started-guide/](https://nodejs.org/en/docs/guides/getting-started-guide/) \
[https://expressjs.com/es/starter/hello-world.html](https://expressjs.com/es/starter/hello-world.html) \
[https://victorroblesweb.es/2018/01/31/configurar-acceso-cors-en-nodejs/](https://victorroblesweb.es/2018/01/31/configurar-acceso-cors-en-nodejs/)

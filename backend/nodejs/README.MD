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

Creamos la base de un servidor que nos servirá de prueba llamado `app.js`: 

```javascript

});
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

### Crear la clase de Javascript

El siguiente paso es crear una carpeta donde ubicaremos las clases llamada `messages`, la clase en Javascript la generaremos  con el comando `protoc` especificando que la clase debe ser *comun* para que se adapte a cualquier proyecto:
```
$ protoc --proto_path=./  --js_out=import_style=commonjs,binary:./messages person.proto
```

Despues de ejecutarlo nos debe generar un archivo llamado `person_pb.js`

## Usando los menssages en el servidor

Lo primero que debemos hacer es importar la libreria de protobuf y la clase generada, eso lo haremos con agregando las  siguientes lineas a nuestro archivo `app.js`.
```javascript
...

require('google-protobuf');
require('./messages/person_pb');

...
```

Para crear una instacia de nuestro menssage `Person` lo podremos hacer con la siguiente linea:
```javascript
const person = new proto.Person();
```

Y para asignarle los valores los podremos hacer a travez de sus métodos Setters
```javascript
person.setName("Nombre1");
person.setEmail("correo@electronico.com");
person.getPhoneList().push( new proto.Person.PhoneNumber(["449 123 45 67", proto.Person.PhoneType.HOME]))
```

Para poder visualizar la información la persona lo podremos hacer por medio de la consola, agregando la siguiente linea al método `server.listen` de nuestro servidor:
```javascript
console.log(person.toObject());
```

Al ejecutar el comando 
```
$ node app.js
```

Nos debe mostrar el siguiente texto en la consola
```
Server running at http://127.0.0.1:3000/
{ name: 'Nombre1',
  id: 0,
  email: 'correo@electronico.com',
  phoneList: [ { number: '449 123 45 67', type: 1 } ] }
```


## Referencias

[https://nodejs.org/en/docs/guides/getting-started-guide/](https://nodejs.org/en/docs/guides/getting-started-guide/)

[https://expressjs.com/es/starter/hello-world.html](https://expressjs.com/es/starter/hello-world.html)
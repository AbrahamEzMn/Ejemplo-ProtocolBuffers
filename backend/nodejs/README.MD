# Uso de Protocol Buffers en un servidor NodeJS

Versión
* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3

## Creación de proyecto de prueba

Iniciamos el proyecto con el `npm`:
```
$ npm init
```

Creamos la base de un servidor que nos servirá de prueba llamado `app.js`: 

```javascript
const http = require('http');

const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello World\n');
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});
```

## Agregando las libreria

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




## Referencias

[https://nodejs.org/en/docs/guides/getting-started-guide/](https://nodejs.org/en/docs/guides/getting-started-guide/)
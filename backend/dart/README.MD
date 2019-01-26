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

Seguira la siguiente estructura:

```bash
DART
  ├─bin
  │  └─proto
  └─messages
```

```bash
$ protoc --dart_out=bin/proto --proto_path=./messages/ messages/*.proto
```

```bash
$ export PATH="$PATH":"$HOME/.pub-cache/bin"
```

# Referencias
https://www.dartlang.org/tutorials/server/httpserver <br />
https://pub.dartlang.org/packages/protoc_plugin#-installing-tab-<br />
https://pub.dartlang.org/packages/protobuf#-installing-tab-<br />
https://developers.google.com/protocol-buffers/docs/darttutorial<br />

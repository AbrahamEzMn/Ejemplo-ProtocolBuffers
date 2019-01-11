# Protocol Buffers - Instalación y ejecución de Protobuf

Versión
-------
Os: Ubuntu 18.04.1 LTS
Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
Protobuf: libprotoc 3.6.1
Sintaxis: Proto3


## Instalando protoc

Esta guía describe los pasos para instalar ejecutar <b>protoc</b> y generar algunos Messages en la versión de [proto3](https://developers.google.com/protocol-buffers/docs/proto3).

1.- Descargar el ejecutable del compilador de protocol buffer para sistemas operativos linux en su versión 3.6.1 (protoc), para ver la lista de versiones mas recientes o para diferentes sistemas operativos hacer [click aqui](https://github.com/protocolbuffers/protobuf/releases)   
```
$ wget https://github.com/protocolbuffers/protobuf/releases/download/v3.6.1/protoc-3.6.1-linux-x86_64.zip
```

2.- Descomprimivos el zip en una carpeta llamada "protoc3.6.1"
```
$ unzip protoc-3.6.1-linux-x86_64.zip -d protoc3.6.1
```

3.- Entramos a la capeta de creada
```
$ cd protoc3.6.1
```

4.- Movemos el ejecutable(protoc) de la carpeta bin a la carpeta ubicada en /usr/local/bin/
```
$ mv bin/protoc /usr/local/bin/
```

5.- Asi mismo con el contenido de la carpeta include a la carpeta ubicada en /usr/local/include/
```
$ mv include/google/ /usr/local/include/
```

Ya con esto verificamos el comando protoc con la siguiente sentencia
```
$ protoc --version
```

Nos lanzar el siguiente resultado:
```
libprotoc 3.6.1
```


##Generando el archivo .proto

Este archivo nos ayudará a colocar la estructura de la información que queremos serializar escrito en archivos con extención `.proto`. 

Para servir de guía utilizaré el ejemplo que utiliza la [página oficial](https://developers.google.com/protocol-buffers/docs/overview#how-do-they-work) donde se colocan diferentes tipos de situaciones que se nos pueden presentar. 

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

Este archivo llamado person.proto contiene la infomarción de un perfil que podría tener un sistema:
* Un nombre.
* Un id.
* Un email.
* Una lista de números telefónicos con una estructura específica y una lista de tipos que pueden tener.


## Crear las clases a partir del archivo .proto 


Para esto necesitaremos el comando `protoc` que previamente mencione, como ejemplo crearemos el proto en `java` con el siguiente comando
```
$ protoc --proto_path=./ --java_out=./java/ person.proto
```

Explicación 
* `--proto_path`: Es la dirección donde estan los archivos `proto`.  
* `--java_out`: Bandera que especifica la carpeta donde se van a generar las clases en java. 
* `person.proto`: Nombre del archivo proto que se va a tomar para generar las clases. Tambien es valido colocar `*.proto` para espeficiar que todos los archivos con terminación .proto se tomen en cuenta para generar las clases.


### Comandos de ejemplos para generar archivos en diferentes lenguajes.
*C++*
```
$ protoc --proto_path=./ --cpp_out=./java/ person.proto
```



Referencias
-----------

https://developers.google.com/protocol-buffers/


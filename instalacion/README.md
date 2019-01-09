# Instalacion de ProtocolBuffers
Protocol Buffers - Instalación y ejecución de Protobuf 
======================================================

Versión
-------
Os: Ubuntu 18.04.1 LTS
Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
Protobuf: libprotoc 3.6.1
Sintaxis: Proto3


Procedimiento
-------------
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


Referencias
-----------

https://developers.google.com/protocol-buffers/

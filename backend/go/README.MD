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

Y exportamos la variable `GOPATH` con el siguiente comando:
```
$ export GOPATH=UBICACION_DEL-PROYECTO
```

Instalamos el plugin de `protoc` para `go` con el siguiente comando:
```
$ go get github.com/golang/protobuf/protoc-gen-go
```

## Creando el message


```
$ protoc --proto_path=./messages/ --go_out=./src/proto/messages ./messages/*.proto
```

## Referencias

[https://golang.org/doc/code.html](https://golang.org/doc/code.html)<br />
[https://github.com/golang/protobuf/](https://github.com/golang/protobuf/)<br />
[https://developers.google.com/protocol-buffers/docs/reference/go-generated]
(https://developers.google.com/protocol-buffers/docs/reference/go-generated)<br />
[http://dlintw.github.io/gobyexample/public/http-client.html](http://dlintw.github.io/gobyexample/public/http-client.html)
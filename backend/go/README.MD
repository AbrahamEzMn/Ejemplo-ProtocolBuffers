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
 └─src
```

Y exportamos la variable `GOPATH` con el siguiente comando:
```
$ export GOPATH=UBICACION_DEL-PROYECTO
```

Instalamos el plugin de `protoc` para `go` con el siguiente comando:
```
$ go get github.com/golang/protobuf/protoc-gen-go
```


## Referencias

[https://golang.org/doc/code.html](https://golang.org/doc/code.html)
[https://developers.google.com/protocol-buffers/docs/reference/go-generated](https://developers.google.com/protocol-buffers/docs/reference/go-generated)
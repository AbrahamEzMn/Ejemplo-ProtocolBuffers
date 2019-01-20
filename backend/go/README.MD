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

Las carpetas `bin` y `src` son parte de la estructura básica de un proyecto en `go`, mientras que `messages` es donde guardaremos los esquemas de las clases y en `src/proto` guardaremos los esquemas generados con el comando de `protoc` con salida en `go`.  

Lo que sigue es exportar la variable `GOPATH` con el siguiente comando:
```
$ export GOPATH=UBICACION_DEL-PROYECTO
```

Instalamos el plugin de `protoc` para `go` con el siguiente comando:
```
$ go get github.com/golang/protobuf/protoc-gen-go
```

## Creando los message
En la carpeta de `messages` generaremos tres archivos con los siguientes datos:

El archivo llamado `person.proto` que contendrá la clase Persona, donde especificaremos los datos que va a tener así como del nombre del paquete.
```
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
```
syntax = "proto3";
import "phoneType.proto";
package messages;


message PhoneNumber {
    string number = 1;
    PhoneType type = 2;
}
```

Y por último `phoneType.proto` que solo tendrá los tipos de teléfonos.
```
syntax = "proto3";
package messages;

enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
}
```

Tras generar aquellos archivos lo siguiente sera los messages en el lenguaje de `go`, eso lo haremos con el siguiente comando usando `protoc`.
```
$ protoc --proto_path=./messages/ --go_out=./src/proto/messages ./messages/*.proto
```

Tras esto nos deberá generar una carpeta `messages`  con tres archivos con extension `.pd.go` en la carpeta de `src/proto`.

## Usando los messages
Para este ejemplo crearemos un servidor en el puerto 3000 donde reciba una petición y dependiendo del tipo enviará o recibirá un `message`.

Este archivo se llamará `main.go` y estará ubicado en la carpeta `src`, lo primero será especificar el paquete e importar todas las librerias que vamos a usar:
```go
package main

import (
    "log"
    "net/http"
    "io/ioutil"
    "fmt"

    "github.com/golang/protobuf/proto"
    "proto/messages"
)
```

Ahora crearemos la función principal donde lanzaremos el servidor y otro donde manejaremos las peticiones:
```go
// Método principal
func main() {
    // Manejaremos la ruta /message con establecido en el método handler
    http.HandleFunc("/message", handler)
    // Lanzamos el servidor en el puerto 3000
    log.Fatal(http.ListenAndServe(":3000", nil))
}

// Controlador de la petición
func handler(w http.ResponseWriter, r *http.Request) {
    // Si nos llega una peticion GET ejecutaremos el método enviarPersona
    if r.Method == "GET" {
        enviarPersona(w)
    }
    // Si nos llega una peticion POST ejecutaremos el método recibirPersona
    if r.Method == "POST" { 
        recibirPersona(w, r)
    }
}
```

Despues crearemos la función `enviarPersona` como la siguiente
```go
//Método para enviar los datos serializados de una persona
func enviarPersona(w http.ResponseWriter){

    //Creamos la persona
    person := &messages.Person{
        //Le asignamos un nombre
        Name: "Nombre1",
        //Un ID
        Id: 1,
        //Un correo electrónico
        Email: "correo@electronico.com",
        //La lista de teléfonos
        Phone: []*messages.PhoneNumber{ 
            //Teléfono de casa
            &messages.PhoneNumber {
                Number: "123 456 78 90",
                Type: messages.PhoneType_HOME,
            },
        },
    }

    // Mostramos los datos de la persona
    log.Print(person.String())

    // Serializamos los datos de la persona
    data, err := proto.Marshal(person)
    if err != nil {
        log.Fatal("marshaling error: ", err)
    }

    //Enviamos los datos de la persona a través de la respuesta
    w.Write(data)
}
```

Y para el método `recibirPersona`  seria el siguiente
```go
// Método para decodificar a las personas que envian
func recibirPersona(w http.ResponseWriter, r *http.Request) {
    // Creamos una instancia de la persona
    newPerson := &messages.Person{}

    // Leemos el body y lo transformamos a un arreglo de bytes
    bodyInBytes, _ := ioutil.ReadAll(r.Body)
    err := proto.Unmarshal(bodyInBytes, newPerson)
    if err != nil {
        log.Fatal("unmarshaling error: ", err)
    }

    //Mostramos los datos de la persona
    log.Print(newPerson.String())

    //Enviamos el nombre de la persona como respuesta
    fmt.Fprintf(w, newPerson.Name )
}
```

Para lanzar el servidor necesitaremos el siguiente comando:
```
$ go run src/main.go
```

## Creando el archivo de prueba

Crearemos un archivo llamado `maintest.go`, en él importaremos los paquetes de `log`, `net/log`,`io/ioutil`, `bytes`, `protobuf/proto` y las clases que generamos `proto/messages`:
```go
package main

import (
    "log"

    "net/http"
    "io/ioutil"
    "bytes"

    "github.com/golang/protobuf/proto"
    "proto/messages"
)
``` 

Y en el método `main` delcaramos los dos métodos de prueba:
```go
func main() {
    // Probamos el método GET
    testGet()
    // Probamos el método POST
    testPost()
}
```

### Método para probar la petición GET

El método `testGet` tendria el siguiente código:
```go
// Método para probar la petición GET
func testGet(){

    // Creamos el cliente y configuramos la petición GET
    client := &http.Client{}
    req, err := http.NewRequest("GET", "http://127.0.0.1:3000/message", nil)
    req.Header.Add("Content-Type", "application/x-protobuf")
    resp, err := client.Do(req)
    if err != nil {
        panic(err) 
    }
    defer resp.Body.Close()

    // Leemos el body en bytes
    bodyBytes, err := ioutil.ReadAll(resp.Body)
    if err != nil {
        panic(err)
    }

    // Declaramos una instancia de la Persona.
    person := &messages.Person{}

    //Decodificamos el body
    err = proto.Unmarshal(bodyBytes, person)
    if err != nil {
        log.Fatal("unmarshaling error: ", err)
    }

    //Mostramos los datos de la persona
    log.Print(person.String())
}
```

Al ejecutar este método nos debe lanzar el siguiente resultado en la consola del cliente
```
name:"Nombre1" id:1 email:"correo@electronico.com" phone:<number:"123 456 78 90" type:HOME >
```
Y por lado del servidor nos debe mostrar lo siguiente:
```
name:"Nombre1" id:1 email:"correo@electronico.com" phone:<number:"123 456 78 90" type:HOME > 
```

### Método para probar la petición POST

Mientras que el cógido POST será el siguiente:
```go
func testPost() {
    //Creamos la persona
    person := &messages.Person{
        //Le asignamos un nombre
        Name: "Nombre2",
        //Un ID
        Id: 1,
        //Un correo electrónico
        Email: "correo2@electronico2.com",
        //La lista de teléfonos
        Phone: []*messages.PhoneNumber{

            //Teléfono de trabajo
            &messages.PhoneNumber {
                Number: "987 654 32 10",
                Type: messages.PhoneType_WORK,
            },
        },
    }

    // Mostramos los datos de la persona
    log.Print(person.String())

    // Serializamos los datos de la persona.
    data, err := proto.Marshal(person)
    if err != nil {
        log.Fatal("marshaling error: ", err)
    }

    // Creamos el cliente y configuramos la petición POST.
    client := &http.Client{}
    req, err := http.NewRequest("POST", "http://127.0.0.1:3000/message", bytes.NewBuffer(data))
    req.Header.Add("Content-Type", "application/x-protobuf")
    res, err := client.Do(req)
    if err != nil {
        panic(err)
    }

    // Decodificamos el body.
    bodyBytes, err := ioutil.ReadAll(res.Body)
    if err != nil {
        panic(err)
    }

    // Mostramos el resultado de la petición.
    log.Print(string(bodyBytes))
}
``` 

Al ejecutar este método nos debe lanzar el siguiente resultado en la consola del cliente
```
name:"Nombre2" id:1 email:"correo2@electronico2.com" phone:<number:"987 654 32 10" type:WORK >
Nombre2
```

Y por lado del servidor nos debe mostrar lo siguiente:
```
name:"Nombre2" id:1 email:"correo2@electronico2.com" phone:<number:"987 654 32 10" type:WORK > 
```

Para ejecutar este archivo usaremos el siguiente comando:
```
$ go run src/maintest.go
```

## Referencias

[https://golang.org/doc/code.html](https://golang.org/doc/code.html)<br />
[https://github.com/golang/protobuf/](https://github.com/golang/protobuf/)<br />
https://developers.google.com/protocol-buffers/docs/reference/go-generated<br />
[http://dlintw.github.io/gobyexample/public/http-client.html](http://dlintw.github.io/gobyexample/public/http-client.html)<br />
[http://moazzam-khan.com/blog/golang-make-http-requests/](http://moazzam-khan.com/blog/golang-make-http-requests/)
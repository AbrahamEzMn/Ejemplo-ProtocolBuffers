# Protocol-Buffers con Java

Versión

* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3
* Maven: 3.5.2
* Java: OpenJDK 10.0.2
* Spring: 2.0.5

## Creando el proyecto

Para hacer este ejemplo usaremos `Maven` para gestionar nuestro proyecto y ademas el framework de `Spring` para crear la estructura de nuestro servidor.

Lo primero a hacer será crear una carpeta llamada `messages` dentro de `src` y ademas otra dentro `src/main/java` para guardar nuestras clases generadas:

```bash
src
 ├─main
 │   └─java
 │       └─messages
 └─messages
```

## Instalando la librerias de Spring y Protobuf

El primer paso será importar las librerias de `Spring` y `protobuf-java` a nuestro proyecto, y para eso necesitaremos agregar las siguientes lineas a nuestro archivo `pon.xml`

```xml
<project ...>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.5.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf-java</artifactId>
            <version>3.6.1</version>
        </dependency>
    </dependencies>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### Creando el lanzador del servidor

Para poder lanzar nuestro servidor necesitaremos crear una clase, esta clase se llamará `Aplication` y estara ubicado dentro de un paquete llamado `prueba`, esta clase tendra la siguiente estructura:

```java
package prueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

## Creando los esquemas

En la carpeta de `src/messages` generaremos tres archivos con los siguientes datos:
El archivo llamado `person.proto` que contendrá la clase Persona, donde especificaremos los datos que va a tener así como del nombre del paquete.

```bas
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

```bas
syntax = "proto3";
import "phoneType.proto";
package messages;


message PhoneNumber {
    string number = 1;
    PhoneType type = 2;
}
```

Y por último `phoneType.proto` que solo tendrá los tipos de teléfonos.

```bas
syntax = "proto3";
package messages;

enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
}
```

Y para generar las clases en C# usaremos el siguiente comando:

```bash
protoc --proto_path=./src/messages/ --java_out=./src/main/java/ ./src/messages/*.proto
```

Esto nos generará tres archivos por clase en la carpeta de `src/main/java/messages/`.

## Usando los esquemas en el servidor

Para usar nuestras clases generadas tendremos que crear un `controller` llamado `PersonController`, en este caso lo crearé dentro del paquete `prueba` y tendra la siguiente estructura.

```java
package prueba;

import com.google.protobuf.InvalidProtocolBufferException;
import messages.PersonOuterClass;
import messages.PhoneNumberOuterClass;
import messages.PhoneTypeOuterClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
}
```

Para acceder a este controlador redireccionaremos los métodos a la url `/message`:

```bas
http://127.0.0.1:<port>/message
```

### Enviando el message serializado

Para ejemplificar la serialización de los messages enviaremos en la petición `GET` los datos de una persona, eso lo haremos agregando el método `Get` a la clase `PersonController`:

```java
/**
 * Método que maneja las peticiones tipo Get.
 *
 * @return byte[] Datos de una persona.
 */
@GetMapping(value = "/message")
public byte[] get() { 
    // Crea los datos de la persona.
    PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder()
            .setId(1)
            .setEmail("correo@electronico.com")
            .setName("Nombre")
            .addPhone(PhoneNumberOuterClass.PhoneNumber.newBuilder()
                    .setNumber("449 123 45 67")
                    .setType(PhoneTypeOuterClass.PhoneType.WORK)
                    .build())
            .addPhone( PhoneNumberOuterClass.PhoneNumber.newBuilder()
                    .setNumber("449 321 65 47")
                    .setType(PhoneTypeOuterClass.PhoneType.MOBILE)
                    .build())
            .build();
    // Regresa los datos cargados en forma de un arreglo de bytes.
    return person.toByteArray();
}
```

### Recibiendo un message serializado

En caso contrario en el método `POST`, recibiremos los datos serilizados y procederemos a decoficarlo para enviar el nombre de la persona como respuesta:

```java
/**
 * Método que maneja las peticiones tipo Post.
 *
 * @param bodyByte byte[] Datos de una persona.
 * @return String Nombre de la persona.
 */
@PostMapping(value = "/message")
public String post(@RequestBody byte[] bodyByte) {
    // Creamos la variable de la persona.
    PersonOuterClass.Person person = null;
    try {
        // Creamos la persona a partir del cuerpo enviado en la petición.
        person = PersonOuterClass.Person.parseFrom(bodyByte).toBuilder().build();
    } catch (InvalidProtocolBufferException e) {
        e.printStackTrace();
    }
    // Regresamos el nombre de la persona.
    return person.getName();
}
```

## Referencias

[https://spring.io/guides/gs/rest-service/](https://spring.io/guides/gs/rest-service/) \
[https://spring.io/guides/tutorials/bookmarks/](https://spring.io/guides/tutorials/bookmarks/) 
[https://developers.google.com/protocol-buffers/docs/javatutorial](https://developers.google.com/protocol-buffers/docs/javatutorial) \
[https://github.com/protocolbuffers/protobuf/tree/master/java](https://github.com/protocolbuffers/protobuf/tree/master/java)
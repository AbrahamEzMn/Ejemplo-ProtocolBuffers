# Protocol-Buffers con C#

Versión

* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3
* Framework: .NET Framework 4.5
* Ide: MonoDevelop

## Creando el proyecto

Para este ejemplo crearemos un proyecto de `ASP.NET` incluyendo el framework de `Web API` y dentro del proyecto crearemos la carpeta `src` con la siguiente estructura:

```bash
src
 ├─messages
 └─proto
```

## Instalando la libreria de protoc

Para que nuestras clases puedan funcionar necesitaremos agregar el paquete llamado `Google.Protobuf`, para esto podremos descargarlo directamente con el adminsitrador NuGet o desde su consola con el siguiente comando:

```bash
PM> Install-Package Google.Protobuf -Version 3.6.1
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
protoc --proto_path=./src/messages/ --csharp_out=./src/proto ./src/messages/*.proto
```

Esto nos generará cuatro archivos por clase en la carpeta de `src/protoc`.

## Usando los esquemas en el servidor

Para usar nuestras clases generadas tendremos que crear un `controller` dentro de la carpeta llamada `Controllers` llamado `PersonController`, en caso de no contar con esta carpeta solo se deberá crear en la raíz del proyecto.

```csharp
using System;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web.Http;
using Messages;
using Google.Protobuf;

namespace csharp.Controllers
{

    public class PersonController : ApiController
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="T:csharp.Controllers.PersonController"/> class.
        /// </summary>
        public PersonController() { }
    }
}
```

Para acceder a este controlador entraremos a la siguiente ruta:

```bas
http://127.0.0.1:<port>/api/person/
```

### Enviando el message serializado

Para ejemplificar la serialización de los messages enviaremos en la petición `GET` los datos de una persona, eso lo haremos agregando el método `Get` a la clase `PersonController`:

```csharp
/// <summary>
/// Crea una persona.
/// </summary>
/// <returns>Persona serializada.</returns>
[HttpGet]
public HttpResponseMessage Get()
{
    // Creamos a la persona.
    Person person = new Person
    {
        Name = "Nombre de la persona",
        Email = "correo@electronico.com",
        Id = 1
    };
    person.Phone.Add(new PhoneNumber { Number = "4491234657", Type = PhoneType.Mobile });
    person.Phone.Add(new PhoneNumber { Number = "3216549871", Type = PhoneType.Work });

    // Creamos el mensaje de respuesta.
    HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.OK);
    // Asignamos como contenido los datos de la persona.
    response.Content = new ByteArrayContent(person.ToByteArray());
    // Asignamos el tipo de contenido a 'application/x-protobuf'.
    response.Content.Headers.ContentType = new MediaTypeHeaderValue ("application/x-protobuf");

    // Retornamos la respuesta.
    return response;
}

```

### Recibiendo un message serializado

En caso contrario en el método `POST`, recibiremos los datos serilizados y procederemos a decoficarlo para enviar el nombre de la persona como respuesta:

```csharp
/// <summary>
/// Recibe una persona.
/// </summary>
/// <returns>El nombre de una persona.</returns>
[HttpPost]
public async Task<HttpResponseMessage> Post()
{
    // Recibimos los bytes de la persona.
    byte[] bodyBytes = await Request.Content.ReadAsByteArrayAsync();
    // Creamos la pesona a partir del body.
    Person person = Person.Parser.ParseFrom(bodyBytes);

    // Creamos la respuesta con el nombre de la persona.
    HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.OK);
    response.Content = new StringContent(person.Name);

    return response;
}
```

## Referencias

[https://docs.microsoft.com/en-us/aspnet/web-api/overview/getting-started-with-aspnet-web-api/tutorial-your-first-web-api](https://docs.microsoft.com/en-us/aspnet/web-api/overview/getting-started-with-aspnet-web-api/tutorial-your-first-web-api) \
[https://developers.google.com/protocol-buffers/docs/csharptutorial](https://developers.google.com/protocol-buffers/docs/csharptutorial) \
[https://github.com/protocolbuffers/protobuf/tree/master/csharp](https://github.com/protocolbuffers/protobuf/tree/master/csharp) \
[https://www.nuget.org/packages/Google.Protobuf/](https://www.nuget.org/packages/Google.Protobuf/)
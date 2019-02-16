#  Protocol-Buffers con Angular

Versión

* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3
* Angular CLI: 7.3.1
* Node: 10.15.1

## Preparado el proyecto

Para este ejercicio agregaremos la libreria Protocol-Buffers en un proyecto de Angular y para esto tendremos que crear algunas carpetas dentro de nuestro proyecto, en la raíz del proyecto crearemos la carpeta `messages` donde pondremos nuestros prototipos de esquemas y  otra con el mismo nombre en la carpeta `src`, esta nos servirá para poder colocar nuestros esquemas generados en Java:

```bash
 /
 ├─messages
 └─src
    └─messages
```
## Instalando la librerias de Google-Protobuf

Instalamos la libreria `google-protobuf` de manera local por medio del siguiente comando:

```bash
npm install --save google-protobuf
```

Ademas de eso ocuparemos instalar la definición en Typescript de `google-protobuf`:

```bash
npm install --save @types/google-protobuf
```

## Creando los esquemas

En la carpeta de `/messages` generaremos tres archivos con los siguientes datos:
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

### Instalando el plugin de TypeScript

Para generar nuestras definiciones en TypeScript usaremos el pluggin llamado `ts-protoc-gen` y lo instalaremos por medio de la siguiente manera:

```bash
npm install --save ts-protoc-gen
```

### Generando los messages en JavaScript y TypeScript

Y para generar las clases en JavaScript y con su definición en TypeScript usaremos el siguiente comando:

```bash
protoc --plugin=protoc-gen-ts=./node_modules/.bin/protoc-gen-ts --proto_path=./messages --js_out=import_style=commonjs,binary:./src/messages/ --ts_out=./src/messages ./messages/*.proto
```

Esto nos generará nuestros archivos en la carpeta de `src/messages/`.

## Usando los esquemas 

Para este ejemplo necesitaremos usar el modulo `HttpClientModule` para realizar peticiones `http` a nuestra api, y lo importaremos en la clase `app.module.ts` de la siguiente manera:

```javascript

...

import { HttpClientModule } from '@angular/common/http';


@NgModule({
  
  ...

  imports: [
    
    ...
    
    HttpClientModule
  ],
  
  ...

})

...

```

Lo siguiente será crear un componente llamado `messages` con el siguiente comando:

```bash
ng generate component messages
```

Este componente lo tendremos que agregar dentro de nuestro archivo `app-routing.module.ts` para que se lanze cuando culoquemos la ruta `/messages`, y eso lo haremos de la siguiente forma:

```javascript

...

import {MessagesComponent} from './messages/messages.component'

const routes: Routes = [
  { path: 'messages', component: MessagesComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  
  ...
  
})

...

```


## Referencias

[https://github.com/protocolbuffers/protobuf/tree/master/js](https://github.com/protocolbuffers/protobuf/tree/master/js) \
[https://github.com/improbable-eng/ts-protoc-gen](https://github.com/improbable-eng/ts-protoc-gen) \
[https://www.npmjs.com/package/@types/google-protobuf](https://www.npmjs.com/package/@types/google-protobuf) \
[https://angular.io/tutorial/toh-pt6#heroes-and-http](https://angular.io/tutorial/toh-pt6#heroes-and-http) \
[https://stackoverflow.com/questions/50260391/open-pdf-from-bytes-array-in-angular-5](https://stackoverflow.com/questions/50260391/open-pdf-from-bytes-array-in-angular-5) \ 
[https://stackoverflow.com/questions/47431584/converting-array-buffer-in-string](https://stackoverflow.com/questions/47431584/converting-array-buffer-in-string) \
[https://developers.google.com/protocol-buffers/docs/reference/javascript-generated](https://developers.google.com/protocol-buffers/docs/reference/javascript-generated)


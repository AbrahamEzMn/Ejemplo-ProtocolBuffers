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

## Preparando el proyecto

Para este ejemplo necesitaremos usar el modulo `HttpClientModule` para realizar peticiones `http` a nuestra api, y lo importaremos en la clase `app.module.ts` de la siguiente manera:

```typescript

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

Este componente lo tendremos que agregar dentro del modulo de `routing` ubicado en el archivo `app-routing.module.ts`, con el cometido de que se lanze el componente cuando coloquemos la ruta `/messages`, y para eso lo haremos de la siguiente forma:

```typescript

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

### Adaptando el componente

Para mostrar los datos a usar modificaremos el archivo html del componente llamado `messages.component.html` dejandolo de la siguiente manera:

```html
<h1>Messages</h1>

<h2>Persona obtenida desde el servidor</h2>

<ul>
  <li><span class="badge">Id:</span> {{personaGet.getId()}}</li>
  <li><span class="badge">Nombre:</span> {{personaGet.getName()}}</li>
  <li><span class="badge">Email:</span> {{personaGet.getEmail()}}</li>
  <li *ngFor="let numeroTelefonico of personaGet.getPhoneList()">
    <span class="badge">Número Telefonico:</span> {{numeroTelefonico.getNumber()}} <span class="badge">Tipo:</span> 
      <span [ngSwitch]="numeroTelefonico.getType()">
        <span *ngSwitchCase="0">Celular</span>
        <span *ngSwitchCase="1">Casa</span>
        <span *ngSwitchCase="2">Trabajo</span>
        <span *ngSwitchDefault>No especifico</span>
      </span>
  </li>
</ul>

<h2>Nombre de la persona enviada al servidor</h2>

<span class="badge">Nombre:</span> {{nombrePersona}}
```
Y posteriormente agregaremos un estilo a nuestro archivo `CSS`:

```css
.badge {
    background: #000 1px;
    border-radius: 2px;
    color: white;
    padding: 1px
}
``` 

Ademas de eso modificaremos el archivo `messages.component.ts` para que se adapte a lo utilizado en la vista e importaremos todas las dependencias que vamos a utilizar:

```typescript

...

import { Person } from '../../messages/person_pb' ;
import { PhoneNumber } from '../../messages/phoneNumber_pb';
import { PhoneType } from '../../messages/phoneType_pb';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

...
export class MessagesComponent implements OnInit {

  // Persona a usar en la vista.
  personaGet : Person;
  // Nombre a usar en a vista.
  nombrePersona : String;

  /**
   * Constructor de la clase.
   * @param http Cliente Http.
   */
  constructor( private http: HttpClient ) { 
    // Inicializamos la persona. 
    this.personaGet = new Person();
  }

  /**
   * Método que se ejecuta al finalizar el DOM en la vista.
   */
  ngOnInit() {
    
  }
}
```

## Usando los esquemas 

Para este ejemplo mandaremos a llamar un servidor de ejemplo en `NodeJs` especificado en la siguiente [liga](https://github.com/AbrahamEzMn/Example-ProtocolBuffers/tree/master/backend/nodejs).

### Pidiendo una persona desde un servidor.

Para solicitar la persona y mostrarlo en la vista crearemos un método llamado `getPerson()` en el archivo `messages.component.ts` y lo mandaremos a llamar en el método `ngOnInit()` donde lo deserializamos en la propiedad `personaGet`, dejandolo de la siguiente manera:

```typescript
...

export class MessagesComponent implements OnInit {

  ...

  /**
   * Método que se ejecuta al finalizar el DOM en la vista.
   */
  ngOnInit() {

    // Realizamos una petición tipo GET.
    let getRequest = this.getPerson();
    getRequest.subscribe(data => {
      // Deserializamos los datos enviados desde el servidor. 
       this.personaGet = Person.deserializeBinary(new Uint8Array(data));
    } );
  }

  /**
   * Obtiene una persona desde una petición http tipo GET.
   * 
   * @returns Observable<ArrayBuffer> Persona serializada en un arreglo de bytes.   
   */
  getPerson () : Observable<ArrayBuffer>  {
    // Creamos las opciones para la petición.
    let options= {
      // Cargamos el encabezado.
      headers: new HttpHeaders({ 'Content-Type': 'application/x-protobuf' }),
      // Especificamos que la respuesta sera un ArrayBuffer en lugar de json.
      'responseType'  : 'arraybuffer' as 'json'
    };
    return this.http.get<ArrayBuffer>('http://HOST:<PORT>/PATH', options);
  }
}
```

### Enviando una persona a un servidor.

Para enviar una persona y recibir su nombre crearemos un método llamado `sendPerson()` en el archivo el mismo archivo y lo mandaremos a llamar en el método `ngOnInit()` dejandolo de la siguiente manera:


```typescript

...

export class MessagesComponent implements OnInit {

  ...

  /**
   * Método que se ejecuta al finalizar el DOM en la vista.
   */
  ngOnInit() {

    ...

    // Creamos la persona a enviar.
    let personaPOST = new Person();
    let numero =new PhoneNumber();
    numero.setNumber('321321654');
    numero.setType(PhoneType.WORK);

    personaPOST.addPhone(numero);
    personaPOST.setName("Nombre 2");
    personaPOST.setId(2);
    personaPOST.setEmail('correo@electronico.com');

    // Realizamos una petición tipo POST.
    let requestName = this.sendPerson(personaPOST);
    requestName.subscribe(name => {
      this.nombrePersona =  new String(name);
    });
    
  }

  ...

  /**
   * Envia una persona serializada en una petición tipo POST.
   * 
   * @param person 
   * @returns Observable<ArrayBuffer> Persona serializada en un arreglo de bytes.  
   */
  sendPerson (person: Person) : Observable<Text>  {
    // Creamos las opciones para la petición.
    let options= {
      // Cargamos el encabezado.
      headers: new HttpHeaders({ 'Content-Type': 'application/x-protobuf' }),
      // Especificamos que la respuesta sera un Text en lugar de json.
      'responseType'  : 'text' as 'json'
    };
    return this.http.post<Text>('http://HOST:<PORT>/PATH', String.fromCharCode.apply(null,person.serializeBinary()), options);
  }
}
```

## Referencias

[https://github.com/protocolbuffers/protobuf/tree/master/js](https://github.com/protocolbuffers/protobuf/tree/master/js) \
[https://github.com/improbable-eng/ts-protoc-gen](https://github.com/improbable-eng/ts-protoc-gen) \
[https://www.npmjs.com/package/@types/google-protobuf](https://www.npmjs.com/package/@types/google-protobuf) \
[https://angular.io/tutorial/toh-pt6#heroes-and-http](https://angular.io/tutorial/toh-pt6#heroes-and-http) \
[https://stackoverflow.com/questions/50260391/open-pdf-from-bytes-array-in-angular-5](https://stackoverflow.com/questions/50260391/open-pdf-from-bytes-array-in-angular-5) \ 
[https://stackoverflow.com/questions/47431584/converting-array-buffer-in-string](https://stackoverflow.com/questions/47431584/converting-array-buffer-in-string) \
[https://developers.google.com/protocol-buffers/docs/reference/javascript-generated](https://developers.google.com/protocol-buffers/docs/reference/javascript-generated)


import { Component, OnInit } from '@angular/core';
import { Person } from '../../messages/person_pb' ;
import { PhoneNumber } from '../../messages/phoneNumber_pb';
import { PhoneType } from '../../messages/phoneType_pb';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.less']
})
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

    // Realizamos una petición tipo GET.
    let getRequest = this.getPerson();
    getRequest.subscribe(data => {
      // Deserializamos los datos enviados desde el servidor. 
       this.personaGet = Person.deserializeBinary(new Uint8Array(data));
    } );

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
    return this.http.get<ArrayBuffer>('http://127.0.0.1:3000/message', options);
  }

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
    return this.http.post<Text>('http://127.0.0.1:3000/message', String.fromCharCode.apply(null,person.serializeBinary()), options);
  }
}

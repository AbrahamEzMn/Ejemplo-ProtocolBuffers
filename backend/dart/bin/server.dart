
import 'dart:io';
import 'dart:convert';
import 'dart:async';
import 'proto/person.pb.dart';
import 'proto/phoneNumber.pb.dart';
import 'proto/phoneType.pb.dart';


// Método principal
Future main() async {

    // Declaramos la configuación de nuestro servidor.
    var server = await HttpServer.bind(InternetAddress.loopbackIPv4,4040,);
    print('Listening on localhost:${server.port}');

    // Ejecutamos el hilo del servidor.
    await for (HttpRequest request in server) {
        // Cuando recibamos una petición tipo GET ejecutamos el método `handleGet`
        if (request.method == 'GET') {
            handleGet(request);
        } 
        // Cuando recibamos una petición tipo POST ejecutamos el método `handlePost`
        else if (request.method == 'POST') {
            handlePost(request);
        }
    }
}

/**
 * Maneja la peticiones tipo GET.
 * 
 * @param request Peticion HTTP.
 */
void handleGet(HttpRequest request) {
    // Creamos una persona.
    Person p = new Person()
        ..name = 'Nombre'
        ..email = 'correo@electronico.com'
        ..id = 2
        ..phone.add ( 
            new PhoneNumber()
                ..type = PhoneType.WORK
                ..number = '1234567891') 
        ..phone.add (
            new PhoneNumber()
                ..number = '321659871'
                ..type = PhoneType.HOME);

    // Enviamos la persona serializada como respuesta.
    request.response
        ..headers.contentType = ContentType('application', 'x-protobuf')
        ..add(p.writeToBuffer())
        ..close();
}

/**
 * Maneja la peticiones de manera asincrona tipo POST.
 * 
 * @param request Peticion HTTP.
 */
void handlePost(HttpRequest request) async {
    // Obtenemos los datos del body y la convertimos a una lista de bytes.
    var bodyBytes =  utf8.encode(await request.transform(utf8.decoder).join());
    // Creamos la persona a partir del body.
    Person person = Person.fromBuffer(bodyBytes);

    // Retornamos el nombre de la persona como respuesta.
    request.response
        ..writeln(person.name)
        ..close();
}
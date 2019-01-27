
import 'dart:io';
import 'dart:convert';
import 'dart:async';
import 'proto/person.pb.dart';
import 'proto/phoneNumber.pb.dart';
import 'proto/phoneType.pb.dart';

// Dirección del servidor.
String _host = InternetAddress.loopbackIPv4.host;

// Función proncipal
Future main() async {
    print (await getTest());
    print (await getPost());
}

/**
 * Prueba el método GET en el servidor.
 * 
 * @return Estructura de la persona. 
 */
getTest() async {
    // Creamos la petición tipo POST.
    HttpClientRequest request = await HttpClient().get(_host, 4040, '/'); 
    // Obtenemos la respuesta del servidor.
    HttpClientResponse response = await request.close();
    // Decodificamos la información recibida.
    var bodyBytes =  utf8.encode(await response.transform(utf8.decoder).join());
    // Creamos la persona a partir de la respuesta. 
    Person person = Person.fromBuffer(bodyBytes);
    // Retornamos los datos de la persona  en forma de string.
    return person.toString();
}

/**
 * Prueba el método POST en el servidor.
 * 
 * @return Respuesta del servidor. 
 */
getPost() async {
    // Creamos la persona.
    Person person = new Person()
        ..name = 'Nombre 2'
        ..email = 'correo2@electronico2.com'
        ..id = 2
        ..phone.add ( 
            new PhoneNumber()
                ..type = PhoneType.WORK
                ..number = '0132456789') 
        ..phone.add (
            new PhoneNumber()
                ..number = '111111111'
                ..type = PhoneType.HOME);

    // Hacemos la petición tipo POST y agregamos la persona serializada
    HttpClientRequest request = await HttpClient().post(_host, 4040, '/') 
        ..headers.contentType = ContentType("application", "x-protobuf")
        ..add(person.writeToBuffer()); 

    // Obtenemos la respuesta del servidor.
    HttpClientResponse response = await request.close();
    
    // Retornamos la respuesta del servidor.
    return await response.transform(utf8.decoder).join();
}
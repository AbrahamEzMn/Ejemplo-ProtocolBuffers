<?php

require_once "./vendor/autoload.php";

use Messages\Person;
use Messages\PhoneNumber;
use Messages\PhoneType;

//Especificamos que la respuesta tendrá como contenido  
header('Content-Type: application/x-protobuf');

// Obtenemos el tipo de la petición
$method = $_SERVER['REQUEST_METHOD'];
switch ($method) {
    // En caso de la petición GET ejecutaremos el método llamado get.
    case 'GET':
        echo get();
        break; 
    // En caso de la petición POST ejecutaremos el método llamado post.   
    case 'POST':
        echo post();
        break;               
}

/**
 * Administra las peticiones tipo GET.
 * 
 * @return string Message serializado en bytes guardados en un string.
 */
function get(): string {

    // Creamos a la persona a enviar
    $person = (new Person)
            // Colocamos un nombre.
            ->setName("Nombre 1")
            // Asignamos el correo.
            ->setEmail("correo@electronico.com")
            // Creamos la lista de teléfonos.
            ->setPhone(
                [
                    // Creamos un teléfono de casa.
                    (new PhoneNumber)
                        ->setNumber("1234561230")
                        ->setType(PhoneType::HOME),
                    // Creamos un teléfono de trabajo.
                    (new PhoneNumber)
                        ->setNumber("0987654321")
                        ->setType(PhoneType::WORK)
                ]
            );

    // Enviamos la persona serializada.
    return $person->serializeToString();
}

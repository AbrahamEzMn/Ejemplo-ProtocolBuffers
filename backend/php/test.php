<?php

require_once "./vendor/autoload.php";

use Messages\Person;
use Messages\PhoneNumber;
use Messages\PhoneType;

// Hacemos una petición tipo GET  
$response = file_get_contents('http://127.0.0.1:8080/protocolbuffers/');

// Creamos una persona.
$person = new Person();
// Vaciamos los datos de la persona. 
$person->mergeFromString($response);
// Mostramos los datos serialiados en fomrato json.
echo "TEST GET: {$person->serializeToJsonString()} <br>";


// Creamos a la persona a enviar
$person = (new Person)
                // Colocamos un nombre.
                ->setName("Nombre 2")
                // Asignamos el correo.
                ->setEmail("correo2@electronico2.com")
                // Creamos la lista de teléfonos.
                ->setPhone(
                    [
                        // Creamos un teléfono de casa.
                        (new PhoneNumber)
                            ->setNumber("3216549800")
                            ->setType(PhoneType::HOME),
                    ]
                );

// Creamos las opciones de la petición.
$options = [
    'http' => [
        'header'  => "Content-type: application/x-protobuf\r\n",
        'method'  => 'POST',
        'content' => $person->serializeToString()
    ]
];

// Creamos el contexto.
$context  = stream_context_create($options);

// Ejecutamos la petición.
$result = file_get_contents('http://127.0.0.1:8080/protocolbuffers/', false, $context);

// Mostramos el resultado.
echo "TEST POST: {$result}";
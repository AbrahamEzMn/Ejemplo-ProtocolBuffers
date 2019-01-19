package main

import (
    "log"
	"net/http"
	"io/ioutil"
	"fmt"

	"github.com/golang/protobuf/proto"
	"proto/messages"
)

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

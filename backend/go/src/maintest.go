package main
 
import (
    "log"

    "net/http"
    "io/ioutil"
    "bytes"
    
    "github.com/golang/protobuf/proto"
	"proto/messages"
)
 
func main() {
    // Probamos el método GET
    testGet()
    // Probamos el método POST
    testPost()
}

// Método para probar la petición GET
func testGet(){

    // Creamos el cliente y configuramos la petición GET
    client := &http.Client{}
    req, err := http.NewRequest("GET", "http://127.0.0.1:3000/message", nil)
    req.Header.Add("Content-Type", "application/x-protobuf")
    resp, err := client.Do(req)
    if err != nil {
        panic(err) 
    }
    defer resp.Body.Close()
 
    // Leemos el body en bytes 
    bodyBytes, err := ioutil.ReadAll(resp.Body)
    if err != nil {
        panic(err)
    }
    
    // Declaramos una instancia de la Persona.
    person := &messages.Person{}
    
    //Decodificamos el body
    err = proto.Unmarshal(bodyBytes, person)
	if err != nil {
		log.Fatal("unmarshaling error: ", err)
	}

	//Mostramos los datos de la persona
	log.Print(person.String())
}

func testPost() {
    //Creamos la persona
	person := &messages.Person{
		//Le asignamos un nombre
		Name: "Nombre2",
		//Un ID
		Id: 1,
		//Un correo electrónico
		Email: "correo2@electronico2.com",
		//La lista de teléfonos
		Phone: []*messages.PhoneNumber{

			//Teléfono de trabajo
			&messages.PhoneNumber {
				Number: "987 654 32 10",
				Type: messages.PhoneType_WORK,
			},
		},
	}

	// Mostramos los datos de la persona
    log.Print(person.String())

    // Serializamos los datos de la persona.
	data, err := proto.Marshal(person)
	if err != nil {
		log.Fatal("marshaling error: ", err)
	}

    // Creamos el cliente y configuramos la petición POST.
    client := &http.Client{}
    req, err := http.NewRequest("POST", "http://127.0.0.1:3000/message", bytes.NewBuffer(data))
    req.Header.Add("Content-Type", "application/x-protobuf")
    res, err := client.Do(req)
    if err != nil {
        panic(err) 
    }
    
    // Decodificamos el body.
    bodyBytes, err := ioutil.ReadAll(res.Body)
    if err != nil {
        panic(err)
    }

    // Mostramos el resultado de la petición.
    log.Print(string(bodyBytes))
}
package main
 
import (
    "net/http"
    "io/ioutil"
    "bytes"
    "log"
    "github.com/golang/protobuf/proto"
	"proto/messages"
)
 
func main() {
    testGet()
    //testPost()
}

func testGet(){
    client := &http.Client{}
    req, err := http.NewRequest("GET", "http://127.0.0.1:3000/message", nil)
    req.Header.Add("Content-Type", "application/x-protobuf")
    resp, err := client.Do(req)
    if err != nil {
        panic(err) 
    }
    defer resp.Body.Close()
 
    bodyBytes, err := ioutil.ReadAll(resp.Body)
    if err != nil {
        panic(err)
    }
 
    person := &messages.Person{}
	
    err = proto.Unmarshal(bodyBytes, person)
	if err != nil {
		log.Fatal("unmarshaling error: ", err)
	}

	//Mostramos los datos de la persona
	log.Print(person.Name)
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

			//Teléfono de casa
			&messages.PhoneNumber {
				Number: "987 654 32 10",
				Type: messages.PhoneType_WORK,
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

    client := &http.Client{}
    req, err := http.NewRequest("POST", "http://127.0.0.1:3000/message", bytes.NewBuffer(data))
    req.Header.Add("Content-Type", "application/x-protobuf")
    res, err := client.Do(req)
    if err != nil {
        panic(err) 
    }
 
    bodyBytes, err := ioutil.ReadAll(res.Body)
    if err != nil {
        panic(err)
    }

    log.Print(string(bodyBytes))
}
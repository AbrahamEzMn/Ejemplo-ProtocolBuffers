const http = require('http');

require('google-protobuf');
require('./messages/person_pb');

const hostname = '127.0.0.1';
const port = 3000;


// Creación de la instancia del message
const person = new proto.Person();

// Asignación de valores
person.setName("Nombre");
person.setEmail("correo@electronico.com");
person.getPhoneList().push( new proto.Person.PhoneNumber(["449 123 45 67", proto.Person.PhoneType.HOME]))

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello World\n');
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
  console.log(person.toObject());
});
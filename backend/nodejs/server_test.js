var request = require('request');

require('google-protobuf');
require('./messages/person_pb');

request('http://127.0.0.1:3000/message', function (error, response, body) {

    console.log('test get http://127.0.0.1:3000/message');

    console.log('error:', error);
    console.log('statusCode:', response && response.statusCode);
    console.log('body:', body);

    const person = proto.Person.deserializeBinary(new Buffer(body));
    console.log('persona:', person.toObject());
});

// Creación de la instancia del message
const person = new proto.Person();

// Asignación de valores
person.setName("Nombre 2");
person.setEmail("correo2@electronico2.com");
person.getPhoneList().push( new proto.Person.PhoneNumber(["449 123 45 67", proto.Person.PhoneType.HOME]))

request.post({
  headers:  {'content-type': 'application/x-protobuf'},
  url:     'http://127.0.0.1:3000/message',
  body:    person.serializeBinary()
}, function(error, response, body){
  console.log('\n\rtest post http://127.0.0.1:3000/message');

  console.log('error:', error); // Print the error if one occurred
  console.log('statusCode:', response && response.statusCode); // Print the response status code if a response was received
  console.log('body:', body); // Print the HTML for the Google homepage.
});
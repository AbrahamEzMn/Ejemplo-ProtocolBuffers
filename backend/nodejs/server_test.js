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
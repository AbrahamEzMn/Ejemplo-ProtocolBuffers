var express = require('express');
var bodyParser = require('body-parser');
require('google-protobuf');
require('./messages/person_pb');


var app = express();
app.use(bodyParser.raw({type: 'application/x-protobuf'}))

/**
 * Lanzamos el servidor.
 */
app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});

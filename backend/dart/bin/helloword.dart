
// Copyright (c) 2014, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

// Replies "Hello, world!" to all requests.
// Use the URL localhost:4040 in your browser.
import 'dart:io';
import 'dart:async';
import 'proto/person.pb.dart';
import 'proto/phoneNumber.pb.dart';
import 'proto/phoneType.pb.dart';

Future main() async {
  var server = await HttpServer.bind(
    InternetAddress.loopbackIPv4,
    4040,
  );
  print('Listening on localhost:${server.port}');

  await for (HttpRequest request in server) {

    Person p = new Person()
        ..name = 'Abraham'
        ..email = 'abraham.esparza@gmail.com'
        ..id = 2
        ..phone.add ( 
            new PhoneNumber()
                ..type = PhoneType.WORK
                ..number = '1234567891') 
        ..phone.add (
            new PhoneNumber()
                ..number = '321659871'
                ..type = PhoneType.HOME);

    request.response
        ..headers.contentType = ContentType('application', 'x-protobuf')
        ..add(p.writeToBuffer())
        ..close();
  }
}
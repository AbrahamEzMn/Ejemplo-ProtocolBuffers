#  Protocol-Buffers con Angular

protoc --plugin=protoc-gen-ts=./node_modules/.bin/protoc-gen-ts --proto_path=./messages --js_out=import_style=commonjs,binary:./src/messages/ --ts_out=./src/messages_ts ./messages/*.proto



## Referencias

https://angular.io/tutorial/toh-pt6#heroes-and-http
https://stackoverflow.com/questions/50260391/open-pdf-from-bytes-array-in-angular-5
https://stackoverflow.com/questions/47431584/converting-array-buffer-in-string
https://developers.google.com/protocol-buffers/docs/reference/javascript-generated

https://github.com/improbable-eng/ts-protoc-gen
https://www.npmjs.com/package/@types/google-protobuf
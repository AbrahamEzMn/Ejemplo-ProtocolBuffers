# Protocol-Buffers con Android (Java)


implementation 'com.google.protobuf:protobuf-java:3.6.1'

cd ./app/
protoc --proto_path=./src/messages/ --java_out=./src/main/java/ ./src/messages/*.proto


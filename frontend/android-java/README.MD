# Protocol-Buffers con Android (Java)


implementation 'com.google.protobuf:protobuf-java:3.6.1'

cd ./app/
protoc --proto_path=./src/messages/ --java_out=./src/main/java/ ./src/messages/*.proto

<uses-permission android:name="android.permission.INTERNET" />

## Referencias
[https://docs.oracle.com/javase/7/docs/api/java/lang/ref/WeakReference.html](https://docs.oracle.com/javase/7/docs/api/java/lang/ref/WeakReference.html) \
[https://developer.android.com/reference/android/os/AsyncTask](https://developer.android.com/reference/android/os/AsyncTask) \
[https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html](https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html) \
[https://developer.android.com/reference/java/net/HttpURLConnection](https://developer.android.com/reference/java/net/HttpURLConnection) \
[https://github.com/protocolbuffers/protobuf/tree/master/java](https://github.com/protocolbuffers/protobuf/tree/master/java)

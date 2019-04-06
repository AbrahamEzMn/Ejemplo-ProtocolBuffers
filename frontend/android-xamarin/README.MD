# Protocol-Buffers con Android (Xamarin)

Versión

* Os: Windows 10
* IDE: Microsoft Visual Studio Community 2017
* Xamarin: 4.12.3.80
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3

## Creando el proyecto

Para este ejercicio agregaremos la libreria Protocol-Buffers en un proyecto de Android-Xamarin y para esto tendremos que crear algunas carpetas dentro de nuestro proyecto, en la raíz de nuestra solución crearemos la carpeta `messages` donde pondremos nuestros prototipos de esquemas y otra con el mismo nombre dentro del proyecto en la carpeta `android-xamarin/`, esta nos servirá para poder colocar nuestros esquemas generados en C#:

```bash
android-xamarin
 ├─android-xamarin
 │   └─messages       
 └─messages
```

## Instalando la librerias de Google-Protobuf

Para poder utilizar las clases que vamos a generar tendremos que agregar el paquete de `Google.Protobuf` a nuestro proyecto, lo podremos agregar por medio del `Administrador de Paquetes NuGet` o por la `Consola del Administrador de Paquetes` ejecutando el siguiente comando:

```bash
PM> Install-Package Google.Protobuf -Version 3.6.1
```

## Creando los esquemas

En la carpeta de `/messages` ubicada en la raíz de la solución generaremos tres archivos con los siguientes datos:
El archivo llamado `person.proto` que contendrá la clase Persona, donde especificaremos los datos que va a tener así como del nombre del paquete.

```bas
syntax = "proto3";
import "phoneNumber.proto";
package messages;

message Person {
  string name = 1;
  int32 id = 2;
  string email = 3;
  repeated PhoneNumber phone = 4;
}
```

Otro archivo llamado `phoneNumber.proto`, en el pondremos el estructura de la clase de Teléfono.

```bas
syntax = "proto3";
import "phoneType.proto";
package messages;


message PhoneNumber {
    string number = 1;
    PhoneType type = 2;
}
```

Y por último `phoneType.proto` que solo tendrá los tipos de teléfonos.

```bas
syntax = "proto3";
package messages;

enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
}
```

Y para generar las clases en Java usaremos el siguiente comando:

```bash
protoc --proto_path=./messages --csharp_out=./android-xamarin/Messages ./messages/person.proto ./messages/phoneNumber.proto ./messages/phoneType.proto
```

Esto nos generará tres archivos en la carpeta de `android-xamarin/android-xamarin/messages/`.

## Realizando la aplicación

Lo primero será permitir conectarnos a Internet desde nuestro dispositivo y para agregaremos la siguiente linea dentro de nuestro `AndroidManifest.xml`:

```xml
<manifest>
    ...
    <uses-permission android:name="android.permission.INTERNET" />
</manifest>
```

Del lado de la vista cambiaremos la vista por defecto llamado `activity_main.axml` y  tendremos que dejarla de la isguiente manera:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
	<ScrollView
		android:minWidth="25px"
		android:minHeight="25px"
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:id="@+id/scrollView1" >
		<LinearLayout
			android:orientation="vertical"
			android:minWidth="25px"
			android:minHeight="25px"
			android:layout_width="match_parent"
			android:layout_height="match_parent"
			android:id="@+id/linearLayout1" >
			<TextView
				android:text="Datos recibidos en la petición GET:"
				android:textAppearance="?android:attr/textAppearanceMedium"
				android:layout_width="match_parent"
				android:layout_height="wrap_content"/>
			<TextView
				android:textAppearance="?android:attr/textAppearanceSmall"
				android:layout_width="match_parent"
				android:layout_height="wrap_content"
				android:id="@+id/respuetaGetTextView" />
			<TextView
				android:text="Datos recibidos en la Petición POST:"
				android:textAppearance="?android:attr/textAppearanceMedium"
				android:layout_width="match_parent"
				android:layout_height="wrap_content"/>
			<TextView
				android:textAppearance="?android:attr/textAppearanceSmall"
				android:layout_width="match_parent"
				android:layout_height="wrap_content"
				android:id="@+id/respuestaPostTextView" />
		</LinearLayout>
	</ScrollView>
</RelativeLayout>
```

Lo siguiente será crear una clase que nos permita deserializar los datos recibidos en las peticiones, esta clase se llamará `MessageHelper` y tendra la siguiente estructura:

```csharp
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Google.Protobuf;

namespace android_xamarin
{
    /// <summary>
    /// Ayudante para manipular los Messages.
    /// </summary>
    public static class MessageHelper
    {
        /// <summary>
        /// Deserializa una instancia de IMessage.
        /// </summary>
        /// <typeparam name="T">Tipo de la instancia de IMessage.</typeparam>
        /// <param name="stream">Stream recibido del servidor.</param>
        /// <returns>Instancia de T.</returns>
        public static T Deserialize<T>(Stream stream) where T : IMessage<T>, new()
        {
            //T t = (T)Activator.CreateInstance(typeof(T));
            T t = new T();
            Func<T> func = new Func<T>(() => { return t; });
            var p = new MessageParser<T>(func);
            return p.ParseFrom(stream);
        }
    }
}
```

Posteriormente tendremos que crear una clase que realizará las peticiones tipo `GET` y `POST`, esta clase tendrá por nombre `ApiRequest` y será como la siguiente:

```csharp
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Google.Protobuf;

namespace android_xamarin
{
    /// <summary>
    /// Realiza las peticiones al servidor.
    /// </summary>
    public class ApiRequest
    {
        /// <summary>
        /// Realiza una petición tipo GET.
        /// </summary>
        /// <typeparam name="T">Instancia con la intefaz IMessage.</typeparam>
        /// <param name="url">URL del servicio.</param>
        /// <returns>Instancia de T.</returns>
        public async Task<T> Get<T>(string url) where T : IMessage<T>, new()
        {
            HttpClient client = new HttpClient();

            var uri = new Uri(url);
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/x-protobuf"));

            using (HttpResponseMessage response = await client.GetAsync(uri))
            {
                return MessageHelper.Deserialize<T>(await response.Content.ReadAsStreamAsync());
            }
        }

        /// <summary>
        /// Realiza una petición tipo POST.
        /// </summary>
        /// <param name="url">URL del servicio.</param>
        /// <param name="message">Instancia con la intefaz IMessage</param>
        /// <returns>Resultado en un String.</returns>
        public async Task<string> Post(string url, IMessage message)
        {
            var uri = new Uri(url);

            var client = new HttpClient();
            var content = new ByteArrayContent(message.ToByteArray());
            content.Headers.ContentType = new MediaTypeHeaderValue("application/x-protobuf");

            using (HttpResponseMessage response = await client.PostAsync(uri, content))
            {
                return await response.Content.ReadAsStringAsync();
            }
        }
    }
}
```

Y para finalizar tendremos que llamar las peticiones desde nuestro `Activity` de la siguiente manera:

```csharp

...

using System.Threading.Tasks;
using System.Net;
using System;
using System.IO;
using Google.Protobuf;
using System.Net.Http;
using System.Net.Http.Headers;
using Messages;

namespace android_xamarin
{
    /// <summary>
    /// Activity principal.
    /// </summary>
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme", MainLauncher = true)]
    public class MainActivity : AppCompatActivity
    {

        ....

        /// <summary>
        /// Método que se ejecuta al crear el Acivity.
        /// </summary>
        /// <param name="savedInstanceState">Guardado de la instancia.</param>
        protected async override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.activity_main);

            ApiRequest request = new ApiRequest();

            Person person = await request.Get<Person>("http://<URL>");

            this.FindViewById<TextView>(Resource.Id.respuetaGetTextView).Text = person.ToString();

            // Creamos a la persona.
            Person personaEnviar = new Person
            {
                Name = "Nombre de la persona",
                Email = "correo@electronico.com",
                Id = 1
            };
            personaEnviar.Phone.Add(new PhoneNumber { Number = "4491234657", Type = PhoneType.Mobile });
            personaEnviar.Phone.Add(new PhoneNumber { Number = "3216549871", Type = PhoneType.Work });

            this.FindViewById<TextView>(Resource.Id.respuestaPostTextView).Text = await request.Post("http://<URL>", personaEnviar);
        }

        ...

    }
}
```
 

## Referencias

[https://www.nuget.org/packages/Google.Protobuf](https://www.nuget.org/packages/Google.Protobuf) \
[https://developers.google.com/protocol-buffers/docs/csharptutorial](https://developers.google.com/protocol-buffers/docs/csharptutorial) \
[https://developers.google.com/protocol-buffers/docs/reference/csharp-generated](https://developers.google.com/protocol-buffers/docs/reference/csharp-generated) \
[https://github.com/protocolbuffers/protobuf/tree/master/csharp](https://github.com/protocolbuffers/protobuf/tree/master/csharp) \
[https://docs.microsoft.com/en-us/xamarin/xamarin-forms/data-cloud/consuming/rest](https://docs.microsoft.com/en-us/xamarin/xamarin-forms/data-cloud/consuming/rest)\
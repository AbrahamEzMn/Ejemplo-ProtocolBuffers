using Android.App;
using Android.OS;
using Android.Support.V7.App;
using Android.Runtime;
using Android.Widget;
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

            Person person = await request.Get<Person>("http://192.168.1.82/message");

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

            this.FindViewById<TextView>(Resource.Id.respuestaPostTextView).Text = await request.Post("http://192.168.1.82/message", personaEnviar);
        }
    }
}
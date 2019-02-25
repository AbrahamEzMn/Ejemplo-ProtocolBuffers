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

namespace android_xamarin
{
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme", MainLauncher = true)]
    public class MainActivity : AppCompatActivity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.activity_main);
        }

        // Gets weather data from the passed URL.
        private async Task<byte[]> FetchWeatherAsync(string url)
        {
            HttpClient client = new HttpClient();

            var uri = new Uri(url);
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/x-protobuf"));

            // Send the request to the server and wait for the response:
            using (HttpResponseMessage response = await client.GetAsync(uri))
            {
                byte[] stream = await response.Content.ReadAsByteArrayAsync();
                return stream;
            }

        }

        public async Task<string> Get(string url)
        {
            // Creamos a la persona.
            Person person = new Person
            {
                Name = "Nombre de la persona",
                Email = "correo@electronico.com",
                Id = 1
            };
            person.Phone.Add(new PhoneNumber { Number = "4491234657", Type = PhoneType.Mobile });
            person.Phone.Add(new PhoneNumber { Number = "3216549871", Type = PhoneType.Work });


            var uri = new Uri(url);

            var client = new HttpClient();
            var content = new ByteArrayContent(person.ToByteArray());
            content.Headers.ContentType = new MediaTypeHeaderValue("application/x-protobuf");

            // Send the request to the server and wait for the response:
            using (HttpResponseMessage response = await client.PostAsync(uri, content))
            {
                string stream = await response.Content.ReadAsStringAsync();
                return stream;
            }
        }

    }
}
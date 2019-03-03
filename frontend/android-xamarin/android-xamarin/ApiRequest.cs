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
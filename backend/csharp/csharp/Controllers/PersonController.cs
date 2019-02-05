
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web.Http;
using Google.Protobuf;
using Messages;

namespace csharp.Controllers
{
    public class PersonController : ApiController
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="T:csharp.Controllers.PersonController"/> class.
        /// </summary>
        public PersonController() { }

        /// <summary>
        /// Crea una persona.
        /// </summary>
        /// <returns>Persona serializada.</returns>
        [HttpGet]
        public HttpResponseMessage Get() 
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

            // Creamos el mensaje de respuesta.
            HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.OK);
            // Asignamos como contenido los datos de la persona.
            response.Content = new ByteArrayContent(person.ToByteArray());
            // Asignamos el tipo de contenido a 'application/x-protobuf'.
            response.Content.Headers.ContentType = new MediaTypeHeaderValue("application/x-protobuf");

            // Retornamos la respuesta.
            return response;
        }

        /// <summary>
        /// Recibe una persona.
        /// </summary>
        /// <returns>El nombre de una persona.</returns>
        [HttpPost]
        public async Task<HttpResponseMessage> Post()
        {
            // Recibimos los bytes de la persona.
            byte[] bodyBytes = await Request.Content.ReadAsByteArrayAsync();
            // Creamos la pesona a partir del body.
            Person person = Person.Parser.ParseFrom(bodyBytes);

            // Creamos la respuesta con el nombre de la persona.
            HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.OK);
            response.Content = new StringContent(person.Name);

            return response;
        }
    }
}

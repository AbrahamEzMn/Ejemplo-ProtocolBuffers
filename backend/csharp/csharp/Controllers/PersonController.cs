using System;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Web.Http;
using Google.Protobuf;
using Messages;

namespace csharp.Controllers
{
    public class PersonController : ApiController
    {
        public PersonController() { }

        [HttpGet]
        public HttpResponseMessage getPerson() 
        {
            Person person = new Person
            {
                Name = "Abraham Esparza Moreno",
                Email = "abraham.esparza.m010@gmail.com",
                Id = 1
            };
            person.Phone.Add(new PhoneNumber { Number = "4491234657", Type = PhoneType.Mobile });
            person.Phone.Add(new PhoneNumber { Number = "3216549871", Type = PhoneType.Work });

            /*
            byte[] byteArray;
            using (MemoryStream stream = new MemoryStream()) {
                person.WriteTo(stream);
                byteArray = stream.ToArray();
            }*/

            HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.OK, "value");
            response.Content = new ByteArrayContent(person.ToByteArray());
            response.Content.Headers.ContentType = new MediaTypeHeaderValue ("application/x-protobuf");

            return response;
        }
    }
}

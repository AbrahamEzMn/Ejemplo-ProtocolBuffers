package prueba;

import com.google.protobuf.InvalidProtocolBufferException;
import messages.PersonOuterClass;
import messages.PhoneNumberOuterClass;
import messages.PhoneTypeOuterClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    /**
     * Método que maneja las peticiones tipo Get.
     *
     * @return byte[] Datos de una persona.
     */
    @GetMapping(value = "/message")
    public byte[] get() {
        // Crea los datos de la persona.
        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder()
                .setId(1)
                .setEmail("correo@electronico.com")
                .setName("Nombre")
                .addPhone(PhoneNumberOuterClass.PhoneNumber.newBuilder()
                        .setNumber("449 123 45 67")
                        .setType(PhoneTypeOuterClass.PhoneType.WORK)
                        .build())
                .addPhone( PhoneNumberOuterClass.PhoneNumber.newBuilder()
                        .setNumber("449 321 65 47")
                        .setType(PhoneTypeOuterClass.PhoneType.MOBILE)
                        .build())
                .build();
        // Regresa los datos cargados en forma de un arreglo de bytes.
        return person.toByteArray();
    }

    /**
     * Método que maneja las peticiones tipo Post.
     *
     * @param bodyByte byte[] Datos de una persona.
     * @return String Nombre de la persona.
     */
    @PostMapping(value = "/message")
    public String post(@RequestBody byte[] bodyByte) {
        // Creamos la variable de la persona.
        PersonOuterClass.Person person = null;
        try {
            // Creamos la persona a partir del cuerpo enviado en la petición.
            person = PersonOuterClass.Person.parseFrom(bodyByte).toBuilder().build();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        // Regresamos el nombre de la persona.
        return person.getName();
    }

}

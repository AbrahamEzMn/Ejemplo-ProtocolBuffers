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

    @GetMapping(value = "/message")
    public byte[] get() {

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

        return person.toByteArray();
    }

    @PostMapping(value = "/message")
    public String post(@RequestBody byte[] bodyByte) {

        PersonOuterClass.Person person = null;
        try {
            person = PersonOuterClass.Person.parseFrom(bodyByte).toBuilder().build();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        return person.getName();
    }

}

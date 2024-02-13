package mytelegrambot_v2.services.interfaces;
import mytelegrambot_v2.dto.PersonDto;
import java.util.List;


public interface PersonService {

    PersonDto addPerson(PersonDto personBody);

    PersonDto getPersonById(Long personId);
    List<PersonDto> getAllPerson();

    PersonDto updatePerson(PersonDto personDtoBody, Long chatId);

    PersonDto updatePersonEmail(Long chatId, String email);

    void deletePersonById(Long chatId);
}

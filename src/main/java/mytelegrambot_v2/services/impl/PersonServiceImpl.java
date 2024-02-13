package mytelegrambot_v2.services.impl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.entity.Person;
import mytelegrambot_v2.mappers.PersonMapper;
import mytelegrambot_v2.repositories.PersonRepository;
import mytelegrambot_v2.services.interfaces.PersonService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service class for managing persons.
 */
@Service
@AllArgsConstructor
public class PersonServiceImpl  implements PersonService {
    private final PersonRepository personRepository;
    /**
     * Adds a new person.
     *
     * @param personBody The DTO containing the person information.
     * @return The DTO of the added person.
     */
    public PersonDto addPerson(PersonDto personBody) {
        return PersonMapper.toDto(personRepository.save(PersonMapper.fromDto(personBody)));
    }
    /**
     * Retrieves a person by their ID.
     *
     * @param personId The ID of the person to retrieve.
     * @return The DTO of the retrieved person.
     * @throws EntityNotFoundException If the person with the specified ID is not found.
     */
    public PersonDto getPersonById(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isPresent()) {
            return PersonMapper.toDto(personOptional.get());
        } else {
            throw new EntityNotFoundException("Person with id " + personId + " not found");
        }
    }
    /**
     * Retrieves all persons.
     *
     * @return A list of DTOs representing all persons.
     */
    public List<PersonDto> getAllPerson(){
        List<Person> personsList = personRepository.findAll();
        return personsList.stream()
                .map(person -> {
                    PersonDto personDto = new PersonDto();
                    personDto.setChatId(person.getChatId());
                    personDto.setFirstName(person.getFirstName());
                    personDto.setLastName(person.getLastName());
                    personDto.setTelegramName(person.getTelegramName());
                    personDto.setEmail(person.getEmail());
                    personDto.setRegisteredAt(person.getRegisteredAt());
                    return personDto;
                })
                .collect(Collectors.toList());
    }
    /**
     * Updates an existing person.
     *
     * @param personDtoBody The DTO containing the updated person information.
     * @param chatId        The chat ID of the person to update.
     * @return The DTO of the updated person.
     */

    public PersonDto updatePerson(PersonDto personDtoBody, Long chatId) {
        PersonDto personDto = PersonMapper.toDto(personRepository.findById(chatId).orElse(null));
        if (personDto.equals(null)) {return null;}
        personDto.setFirstName(personDtoBody.getFirstName());
        personDto.setLastName(personDtoBody.getLastName());
        personDto.setTelegramName(personDto.getTelegramName());
        personDto.setEmail(personDtoBody.getEmail());
        personDto.setRegisteredAt(personDtoBody.getRegisteredAt());
        return addPerson(personDto);
    }
    /**
     * Updates the email of an existing person.
     *
     * @param chatId The chat ID of the person whose email to update.
     * @param email  The new email.
     * @return The DTO of the updated person.
     */
    public PersonDto updatePersonEmail(Long chatId, String email) {
        PersonDto personDto = PersonMapper.toDto(personRepository.findById(chatId).orElse(null));
        if (personDto.equals(null)) {return null;}
        personDto.setEmail(email);
        return addPerson(personDto);
    }
    /**
     * Deletes a person by their ID.
     *
     * @param chatId The chat ID of the person to delete.
     */
    public void deletePersonById(Long chatId) {
        personRepository.deleteById(chatId);
    }
}

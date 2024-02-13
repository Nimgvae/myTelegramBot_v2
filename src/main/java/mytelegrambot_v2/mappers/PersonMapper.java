package mytelegrambot_v2.mappers;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.entity.Person;
/**
 * Mapper class for converting between Person entities and PersonDto DTOs.
 */
public class PersonMapper {
    /**
     * Converts a Person entity to a PersonDto DTO.
     * @param person The Person entity to convert.
     * @return The corresponding PersonDto DTO.
     */
    public static PersonDto toDto(Person person){
        PersonDto pd = new PersonDto();
        pd.setChatId(person.getChatId());
        pd.setFirstName(person.getFirstName());
        pd.setLastName(person.getLastName());
        pd.setTelegramName(person.getTelegramName());
        pd.setEmail(person.getEmail());
        pd.setRegisteredAt(person.getRegisteredAt());
        return pd;
    }
    /**
     * Converts a PersonDto DTO to a Person entity.
     * @param personDto The PersonDto DTO to convert.
     * @return The corresponding Person entity.
     */
    public static Person fromDto(PersonDto personDto){
        Person p = new Person();
        p.setChatId(personDto.getChatId());
        p.setFirstName(personDto.getFirstName());
        p.setLastName(personDto.getLastName());
        p.setTelegramName(personDto.getTelegramName());
        p.setEmail(personDto.getEmail());
        p.setRegisteredAt(personDto.getRegisteredAt());
        return p;
    }
}

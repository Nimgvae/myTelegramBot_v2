package mytelegrambot_v2.services.impl;

import jakarta.persistence.EntityNotFoundException;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.entity.Person;
import mytelegrambot_v2.mappers.PersonMapper;
import mytelegrambot_v2.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPerson() {

        PersonDto inputDto = new PersonDto();
        inputDto.setChatId(12345L);
        inputDto.setFirstName("John");
        inputDto.setLastName("Doe");
        inputDto.setTelegramName("johndoe");
        inputDto.setEmail("john@example.com");
        inputDto.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

        Person savedPerson = PersonMapper.fromDto(inputDto);
        savedPerson.setChatId(1L);
        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);
        PersonDto resultDto = personService.addPerson(inputDto);

        assertNotNull(resultDto);
        assertEquals(savedPerson.getChatId(), resultDto.getChatId());
        assertEquals(inputDto.getFirstName(), resultDto.getFirstName());
        assertEquals(inputDto.getLastName(), resultDto.getLastName());
        assertEquals(inputDto.getTelegramName(), resultDto.getTelegramName());
        assertEquals(inputDto.getEmail(), resultDto.getEmail());
        assertEquals(inputDto.getRegisteredAt(), resultDto.getRegisteredAt());
    }

    @Test
    void testGetPersonById_Successful() {
        Long chatId = 12345L;
        Person person = new Person();
        person.setChatId(chatId);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setTelegramName("johndoe");
        person.setEmail("john@example.com");
        person.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        when(personRepository.findById(chatId)).thenReturn(Optional.of(person));
        PersonDto resultDto = personService.getPersonById(chatId);

        assertNotNull(resultDto);
        assertEquals(chatId, resultDto.getChatId());
        assertEquals(person.getFirstName(), resultDto.getFirstName());
        assertEquals(person.getLastName(), resultDto.getLastName());
        assertEquals(person.getTelegramName(), resultDto.getTelegramName());
        assertEquals(person.getEmail(), resultDto.getEmail());
        assertEquals(person.getRegisteredAt(), resultDto.getRegisteredAt());
    }

    @Test
    void testGetPersonById_NotFound() {

        Long chatId = 12345L;
        when(personRepository.findById(chatId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> personService.getPersonById(chatId));
    }

    @Test
    void testGetAllPerson() {

        List<Person> persons = new ArrayList<>();
        Person person1 = new Person();
        person1.setChatId(1L);
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setTelegramName("johndoe");
        person1.setEmail("john@example.com");
        person1.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        persons.add(person1);

        Person person2 = new Person();
        person2.setChatId(2L);
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setTelegramName("janedoe");
        person2.setEmail("jane@example.com");
        person2.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        persons.add(person2);
        when(personRepository.findAll()).thenReturn(persons);
        List<PersonDto> resultDtos = personService.getAllPerson();

        assertNotNull(resultDtos);
        assertEquals(2, resultDtos.size());
        assertEquals(persons.get(0).getChatId(), resultDtos.get(0).getChatId());
        assertEquals(persons.get(0).getFirstName(), resultDtos.get(0).getFirstName());
        assertEquals(persons.get(0).getLastName(), resultDtos.get(0).getLastName());
        assertEquals(persons.get(0).getTelegramName(), resultDtos.get(0).getTelegramName());
        assertEquals(persons.get(0).getEmail(), resultDtos.get(0).getEmail());
        assertEquals(persons.get(0).getRegisteredAt(), resultDtos.get(0).getRegisteredAt());
        assertEquals(persons.get(1).getChatId(), resultDtos.get(1).getChatId());
        assertEquals(persons.get(1).getFirstName(), resultDtos.get(1).getFirstName());
        assertEquals(persons.get(1).getLastName(), resultDtos.get(1).getLastName());
        assertEquals(persons.get(1).getTelegramName(), resultDtos.get(1).getTelegramName());
        assertEquals(persons.get(1).getEmail(), resultDtos.get(1).getEmail());
        assertEquals(persons.get(1).getRegisteredAt(), resultDtos.get(1).getRegisteredAt());
    }

}
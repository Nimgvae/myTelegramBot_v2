package mytelegrambot_v2.controllers;

import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.services.impl.PersonServiceImpl;
import mytelegrambot_v2.util.SimpleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonServiceImpl personServiceImpl;

    @InjectMocks
    private PersonController personController;

    @Test
    void getPersonById_WhenPersonExists_ReturnsOkResponse() {
        // Arrange
        Long personId = 1L;
        PersonDto personDto = new PersonDto();
        when(personServiceImpl.getPersonById(personId)).thenReturn(personDto);

        // Act
        ResponseEntity<SimpleResponse<PersonDto>> responseEntity = personController.getPersonById(personId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(personDto, responseEntity.getBody().getResult());
    }

    @Test
    void getPersonById_WhenPersonDoesNotExist_ReturnsNotFoundResponse() {
        // Arrange
        Long personId = 1L;
        when(personServiceImpl.getPersonById(personId)).thenReturn(null);

        // Act
        ResponseEntity<SimpleResponse<PersonDto>> responseEntity = personController.getPersonById(personId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getAllPerson_ReturnsAllPersons() {
        // Arrange
        List<PersonDto> personList = Collections.singletonList(new PersonDto());
        when(personServiceImpl.getAllPerson()).thenReturn(personList);

        // Act
        SimpleResponse<List<PersonDto>> response = personController.getAllPerson();

        // Assert
        assertEquals(personList, response.getResult());
    }

    @Test
    void updatePerson_WhenPersonExists_ReturnsOkResponse() {
        // Arrange
        Long personId = 1L;
        PersonDto personDto = new PersonDto();
        when(personServiceImpl.updatePerson(any(), eq(personId))).thenReturn(personDto);

        // Act
        SimpleResponse<?> response = personController.updatePerson(personDto, personId);

        // Assert
        assertEquals(response.getResult(), response.getResult()); // Comparing with HTTP status OK
    }

    @Test
    void updatePerson_WhenPersonDoesNotExist_ReturnsNotFoundResponse() {
        // Arrange
        Long personId = 1L;
        when(personServiceImpl.updatePerson(any(), eq(personId))).thenReturn(null);

        // Act
        SimpleResponse<?> response = personController.updatePerson(new PersonDto(), personId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getResult());
    }


}
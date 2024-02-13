package mytelegrambot_v2.controllers;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.services.impl.PersonServiceImpl;
import mytelegrambot_v2.util.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller class for managing person endpoints.
 */
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
@Validated
public class PersonController {
    private final PersonServiceImpl personServiceImpl;
    /**
     * Retrieves person by ID.
     * @param id The ID of the person to retrieve.
     * @return ResponseEntity containing the person DTO or a not found response.
     */

    @GetMapping("/{id}")
    public ResponseEntity<SimpleResponse<PersonDto>> getPersonById(@PathVariable Long id){
        PersonDto personDto = personServiceImpl.getPersonById(id);
        return (personDto == null )
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(new SimpleResponse<>(personDto));
    }
    /**
     * Retrieves all persons.
     * @return SimpleResponse containing a list of person DTOs.
     */
    @GetMapping("/all_person")
    public SimpleResponse<List<PersonDto>> getAllPerson(){
        return new SimpleResponse<>(personServiceImpl.getAllPerson());
    }
    /**
     * Adds a new person.
     * @param personDtoBody The person DTO to add.
     * @return SimpleResponse indicating success or failure.
     */
    @PostMapping("/add_person")
    @ResponseStatus(HttpStatus.OK)
    public SimpleResponse<?> addPerson(@RequestBody @NotNull PersonDto personDtoBody){
        return new SimpleResponse<>(personServiceImpl.addPerson(personDtoBody));
    }

    /**
     * Updates an existing person by ID.
     * @param personDtoBody The updated person DTO.
     * @param id The ID of the person to update.
     * @return SimpleResponse containing the updated person DTO.
     */
    @PutMapping("/{id}/update_person")
    @Transactional
    public SimpleResponse<?> updatePerson(@RequestBody @NonNull PersonDto personDtoBody, @PathVariable Long id) {
        PersonDto personDto = personServiceImpl.updatePerson(personDtoBody, id);
        if (personDto == null) {
            return new SimpleResponse<>(HttpStatus.NOT_FOUND);
        }
        return new SimpleResponse<>(personDto);
    }/**
     * Deletes a person by ID.
     * @param id The ID of the person to delete.
     * @return SimpleResponse indicating success or failure.
     */

    @DeleteMapping("/{id}/delete_person")
    @Transactional
    public SimpleResponse<?> deletePersonById(@PathVariable Long id){
        personServiceImpl.deletePersonById(id);
        return new SimpleResponse<>(HttpStatus.OK);
    }
}

package mytelegrambot_v2.controllers;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.services.impl.MessagePatternServiceImpl;
import mytelegrambot_v2.util.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controller class for managing message pattern endpoints.
 */
@RestController
@RequestMapping("/message-pattern")
@AllArgsConstructor
public class MessagePatternController {
    private final MessagePatternServiceImpl messagePatternServiceImpl;
    /**
     * Retrieves message pattern by ID.
     * @param id The ID of the message pattern to retrieve.
     * @return SimpleResponse containing the message pattern DTO or a not found response.
     */


    @GetMapping("/{id}/get_message-pattern")
    public SimpleResponse<?> getMessagePatternById(@PathVariable Long id) {
        MessagePatternDto messPatDto = messagePatternServiceImpl.getMessagePatternById(id);
        if (messPatDto == null) {
            return new SimpleResponse<>(HttpStatus.NOT_FOUND);
        }
        return new SimpleResponse<>(messPatDto);
    }
    /**
     * Retrieves all message patterns.
     * @return SimpleResponse containing a list of message pattern DTOs.
     */

    @GetMapping("/get-all_message-pattern")
    public SimpleResponse<?> getALlMessagePattern(){
        return new SimpleResponse<>(messagePatternServiceImpl.getAllMessagePattern());
    }
    /**
     * Adds new message pattern.
     * @param messagePatternDto The message pattern DTO to add.
     * @return SimpleResponse indicating success or failure.
     */
    @PostMapping("/add_message-pattern")
    public SimpleResponse<?> addMessagePattern(@RequestBody @NonNull MessagePatternDto messagePatternDto){
        return new SimpleResponse<>(messagePatternServiceImpl.addMessagePattern(messagePatternDto));
    }
    /**
     * Updates existing message pattern by ID.
     * @param messagePatternDto The updated message pattern DTO.
     * @param id The ID of the message pattern to update.
     * @return SimpleResponse containing the updated message pattern DTO.
     */

    @PutMapping("/{id}/update_message-pattern")
    public SimpleResponse<?> updateMessagePattern(@RequestBody @NonNull MessagePatternDto messagePatternDto, @PathVariable Long id){
        MessagePatternDto messagePatternDtoObj = messagePatternServiceImpl.updateMessagePattern(messagePatternDto, id);
        return new SimpleResponse<>(messagePatternDtoObj);
    }
    /**
     * Deletes message pattern by ID.
     * @param id The ID of the message pattern to delete.
     * @return ResponseEntity indicating success or failure.
     */

    @DeleteMapping("/{id}/delete_message-pattern")
    public ResponseEntity<Void> deleteMessagePattern(@PathVariable Long id) {
        messagePatternServiceImpl.deleteMessagePattern(id);
        return ResponseEntity.noContent().build();
    }

}

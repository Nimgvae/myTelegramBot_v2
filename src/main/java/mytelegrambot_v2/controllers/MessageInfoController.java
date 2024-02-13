package mytelegrambot_v2.controllers;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.services.impl.MessageInfoServiceImpl;
import mytelegrambot_v2.util.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller class for managing message information endpoints.
 */
@RestController
@RequestMapping("/messageInfo")
@AllArgsConstructor
public class MessageInfoController {
    private final MessageInfoServiceImpl messageInfoServiceImpl;
    /**
     * Retrieves message information by ID.
     * @param id The ID of the message information to retrieve.
     * @return ResponseEntity containing the message information DTO or a not found response.
     */

    @GetMapping("/{id}/get_message-info")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SimpleResponse<MessageInfoDto>> getMessageInfoById(@PathVariable Long id){
        MessageInfoDto messInfoDto = messageInfoServiceImpl.getMessInfoById(id);
        return (messInfoDto == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(new SimpleResponse<>(messInfoDto));
    }
    /**
     * Retrieves all message information.
     * @return SimpleResponse containing a list of message information DTOs.
     */
    @GetMapping("/get-all_message-info")
    @ResponseStatus(HttpStatus.OK)
    public SimpleResponse<List<MessageInfoDto>> getAllMessageInfo(){
        return new SimpleResponse<>(messageInfoServiceImpl.getAllMessInfo());
    }
    /**
     * Adds new message information.
     * @param messageInfoDto The message information DTO to add.
     * @return SimpleResponse indicating success or failure.
     */

    @PostMapping("/add_message_info")
    public SimpleResponse<?> addMessageInfo(@RequestBody @NonNull MessageInfoDto messageInfoDto) {
        messageInfoServiceImpl.addMessageInfo(messageInfoDto);
        return new SimpleResponse<>(HttpStatus.OK);
    }
    /**
     * Updates existing message information by ID.
     * @param messageInfoDto The updated message information DTO.
     * @param id The ID of the message information to update.
     * @return SimpleResponse containing the updated message information DTO or a not found response.
     */

@PutMapping("/{id}/update_message_info")
public SimpleResponse<?> updateMessageInfo(@RequestBody @NonNull MessageInfoDto messageInfoDto, @PathVariable @NonNull Long id) {
    MessageInfoDto messageInfoDtoObj = messageInfoServiceImpl.updateMessInfo(messageInfoDto, id);
    if (messageInfoDtoObj == null || messageInfoDtoObj.getPattern() == null || messageInfoDtoObj.getPattern().getId() == null) {
        return new SimpleResponse<>(HttpStatus.NOT_FOUND);
    } else {
        return new SimpleResponse<>(messageInfoDtoObj);
    }
}
    /**
     * Deletes message information by ID.
     * @param id The ID of the message information to delete.
     * @return ResponseEntity indicating success or failure.
     */


    @DeleteMapping("/{id}/delete_message_info")
    public ResponseEntity<Void> deleteMessageInfo(@PathVariable Long id) {
        messageInfoServiceImpl.deleteMessInfo(id);
        return ResponseEntity.noContent().build();
    }
}
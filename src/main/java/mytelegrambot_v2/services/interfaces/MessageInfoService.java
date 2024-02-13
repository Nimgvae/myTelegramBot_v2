package mytelegrambot_v2.services.interfaces;
import mytelegrambot_v2.dto.MessageInfoDto;
import java.time.LocalDate;
import java.util.List;

public interface MessageInfoService {


    MessageInfoDto getMessInfoById(Long idMessInfo);
    List<MessageInfoDto> getAllMessInfo();
    List<MessageInfoDto> getMessageInfosByDate(LocalDate date);
    MessageInfoDto addMessageInfo(MessageInfoDto messageInfoDto);
    void updateMessInfoStatus(boolean newStatus, Long idMessInfo);
    MessageInfoDto updateMessInfo(MessageInfoDto messageInfoDto, Long idMessInfo);
    void deleteMessInfo(Long idMessInfo);
}

package mytelegrambot_v2.repositories;

import jakarta.transaction.Transactional;
import mytelegrambot_v2.entity.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
/**
 * Repository interface for managing {@link MessageInfo} entities.
 */
@Repository
public interface MessageInfoRepository extends JpaRepository<MessageInfo, Long> {
    /**
     * Retrieves a list of {@link MessageInfo} entities with a specified date and status false.
     *
     * @param date The date to filter the message info entities.
     * @return A list of message info entities matching the specified date and status false.
     */
    List<MessageInfo> getMessageInfoByDateAndStatusFalse(LocalDate date);
    /**
     * Deletes all {@link MessageInfo} entities associated with a specified person ID.
     *
     * @param personId The ID of the person whose message info entities should be deleted.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM MessageInfo mi WHERE mi.person.chatId = :personId")
    void deleteAllMessageInfoByPersonId(Long personId);


}

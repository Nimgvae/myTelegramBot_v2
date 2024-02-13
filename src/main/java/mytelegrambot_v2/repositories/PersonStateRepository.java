package mytelegrambot_v2.repositories;
import mytelegrambot_v2.entity.entityEnum.UserState;
import mytelegrambot_v2.entity.PersonState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing PersonState entities.
 */
@Repository
public interface PersonStateRepository extends JpaRepository<PersonState, Long> {
    /**
     * Updates the state of a person with the given ID.
     *
     * @param id         The ID of the person to update.
     * @param userState  The new state of the user associated with the person.
     */
    @Modifying
    @Query("UPDATE PersonState ps SET ps.userState = :userState WHERE ps.id = :id")
    void updatePersonStateById(Long id, UserState userState);
}

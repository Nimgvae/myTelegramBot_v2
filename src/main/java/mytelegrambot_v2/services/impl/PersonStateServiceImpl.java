package mytelegrambot_v2.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mytelegrambot_v2.entity.entityEnum.UserState;
import mytelegrambot_v2.repositories.PersonStateRepository;
import mytelegrambot_v2.services.interfaces.PersonStateService;
import org.springframework.stereotype.Service;
/**
 * Service class to handle operations related to PersonState entities.
 */
@Service
@RequiredArgsConstructor
public class PersonStateServiceImpl implements PersonStateService {

    private final PersonStateRepository personStateRepository;
    /**
     * Updates the state of a person with the given ID.
     *
     * @param id         The ID of the person to update.
     * @param userState  The new state of the user associated with the person.
     */
    @Transactional
    public void updatePersonState(Long id, UserState userState){
        personStateRepository.updatePersonStateById(id, userState);
    }
}

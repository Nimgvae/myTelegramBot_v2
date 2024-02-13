package mytelegrambot_v2.services.interfaces;

import mytelegrambot_v2.entity.entityEnum.UserState;

public interface PersonStateService {

    void updatePersonState(Long id, UserState userState);
}

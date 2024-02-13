package mytelegrambot_v2.services;

import mytelegrambot_v2.entity.entityEnum.UserState;
import mytelegrambot_v2.repositories.PersonStateRepository;
import mytelegrambot_v2.services.impl.PersonStateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


class PersonStateServiceImplTest {

    @Mock
    private PersonStateRepository personStateRepository;
    @InjectMocks
    private PersonStateServiceImpl personStateServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        personStateServiceImpl = new PersonStateServiceImpl(personStateRepository);
    }

    @Test
    public void testUpdatePersonState() {
        Long id = 123L;
        UserState userState = UserState.IDLE;

        // When
        personStateServiceImpl.updatePersonState(id, userState);

        // Then
        Mockito.verify(personStateRepository).updatePersonStateById(id, userState);
    }
}
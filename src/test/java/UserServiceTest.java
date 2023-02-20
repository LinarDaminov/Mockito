
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User igor;


    @BeforeEach
    public void setUp() {
        igor = new User("Igor", "123456");
    }

    @Test
    void getAllLogins() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("Igor", "123456")));
        userRepository.addUser(igor);
        Assertions.assertEquals(List.of("Igor"), userService.getAllLogins());
    }

    @Test
    void ifLoginIsNullThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser("", "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Login and password should be defined");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenCorrectUserIsAddedThenAddUserIsCalledFromRepo() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        userService.createNewUser("Igor", "123456");
        verify(userRepository)
                .addUser(any());
    }

    @Test
    void whenPasswordIsNullThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser("Igor", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Login and password should be defined");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenExistingUserIsPassedThenServiceThrowsException() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("test", "123")));
        assertThatThrownBy(() -> userService.createNewUser("test", "123"))
                .isInstanceOf(UserNonUniqueException.class)
                .hasMessage("Such user already exists");
    }

    @Test
    void whenLoginAndPasswordMatchAutentificationIsPassed() {
        when(userRepository.getAllUsers()).thenReturn(List.of(igor));
        userService.userAutentificationByLoginAndPassword("Igor", "123456");
        Assertions.assertTrue(userService.userAutentificationByLoginAndPassword("Igor", "123456"));
    }

}

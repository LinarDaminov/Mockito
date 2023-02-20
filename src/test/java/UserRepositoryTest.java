
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;



public class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();
    User igor;
    User olga;
    User dima;

    @BeforeEach
    public void setUp() {
        igor = new User("Igor", "123456");
        olga = new User("Olga", "123456");
        dima = new User("Dima", "000000");
    }
    @Test
    public void getEmptyUsersList() {
        Assertions.assertEquals(userRepository.getAllUsers(), Collections.EMPTY_LIST);
    }
    @Test
    public void getNotEmptyUsersList() {
        userRepository.addUser(igor);
        Assertions.assertEquals(List.of(igor), userRepository.getAllUsers());
    }
    @Test
    public void findUserByLoginIfSuchUserExists() {
        userRepository.addUser(igor);
        Assertions.assertEquals(userRepository.getUserByLogin("Igor"), igor);
    }
    @Test
    public void findUserByLoginIfSuchUserDoesNotExist() {
        userRepository.addUser(igor);
        Assertions.assertNull(userRepository.getUserByLogin("Michail"));
    }
    @Test
    public void findUserByLoginAndPasswordIfSuchUserExists() {
        userRepository.addUser(igor);
        Assertions.assertEquals(userRepository.getUserByLoginAndPassword("Igor", "123456"), igor);
    }
    @Test
    public void findUserByLoginAndPasswordIfLoginMatchesWithOneExistingAndPasswordNotMatches() {
        userRepository.addUser(igor);
        userRepository.addUser(dima);
        Assertions.assertNotEquals(userRepository.getUserByLoginAndPassword("Igor", "123456"), dima);
    }
    @Test
    public void findUserByLoginAndPasswordIfPasswordMatchesWithOneExistingAndLoginNotMatches() {
        userRepository.addUser(igor);
        userRepository.addUser(olga);
        Assertions.assertNotEquals(userRepository.getUserByLoginAndPassword("Igor", "123456"), olga);
    }


}

import java.util.*;

public class UserRepository {
    List<User> userList = new ArrayList<>();

    public Collection<User> getAllUsers() {
        return userList;
    }

    public User getUserByLogin(String login) {
        for (User user : userList) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByLoginAndPassword(String login, String password) {
        for (User user : userList) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return  user;
            }
        }
        return null;
    }


    public void addUser(User user) {
        userList.add(user);
    }

}

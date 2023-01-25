package services;

import entity.User;
import storage.FileUserStorage;
import storage.UserStorage;

import java.util.List;
import java.util.Optional;

public class UserManagerService {
    UserStorage userStorage = new FileUserStorage("users.txt");

    public void create(User user) {
        userStorage.create(user);
    }

    public void update(User user) {
        userStorage.update(user);
    }

    public Optional<User> findById(long id) {
        return userStorage.findById(id);
    }

    public void removeById(long id) {
        userStorage.removeById(id);
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

}

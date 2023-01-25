package storage;

import entity.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    void create(User user);

    void update(User user);

    Optional<User> findById(long id);

    void removeById(long id);

    List<User> findAll();
}
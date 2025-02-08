package ru.itis.fisd.service;

import ru.itis.fisd.model.User;
import ru.itis.fisd.repository.UserRepository;

public class UserService {
    private final UserRepository repository;

    public UserService() {
        this.repository = new UserRepository();
    }

    public User getByName(String name) {
        return repository.getUser(name);
    }

    public boolean updateUser(int id, User user) {
        return repository.updateUser(id, user);
    }

    public void updateBill(long id, Long bill) {
        repository.updateBill(id, bill);
    }

    public void insertUser(User user) {
        repository.insert(user);
    }
}

package ru.kata.spring.boot_security.demo.service;


import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserService {

    void updateUser(User updatedUser);

    void saveUser(User user);

    void deleteUser(Long id);

    User getById(Long id);

    List<User> getAll();

    User findUserByUsername(String username);

    User getUserByEmail(String email);

}
package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserService {

    void saveUser(User user);

    void updateUser(User user, Boolean shouldEncodePassword);

    void deleteUser(Long id);

    User getById(Long id);

    List<User> getAll();

    User findUserByUsername(String username);
}
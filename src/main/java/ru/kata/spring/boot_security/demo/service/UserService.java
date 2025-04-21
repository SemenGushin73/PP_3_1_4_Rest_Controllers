package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserService {

    public void saveUser(User user);

    public void updateUser(User user);

    public void deleteUser(Long id);

    User findById(Long id);

    List<User> getAllUsers();

    User findUserByUsername(String username);
}
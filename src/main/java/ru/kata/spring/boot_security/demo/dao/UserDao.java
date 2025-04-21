package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    public void saveUser(User user);

    public void updateUser(User user);

    public void deleteUser(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();

    User findByUsername(String username);
}
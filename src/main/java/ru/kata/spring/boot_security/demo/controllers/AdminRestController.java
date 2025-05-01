package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/admin/api")
public class AdminRestController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminRestController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAdminData(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("users", userService.getAll());
        response.put("allRoles", roleRepository.findAll());

        User user = userService.getUserByEmail(principal.getName());
        if (user != null) {
            response.put("currentUser", user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {

        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> editUser(@RequestBody User user) {

        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserForEdit(@PathVariable Long id) {
        User user = userService.getById(id);
        List<Role> allRoles = roleRepository.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("allRoles", allRoles);

        return ResponseEntity.ok(response);
    }

}
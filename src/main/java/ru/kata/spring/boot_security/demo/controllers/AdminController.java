package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showAdminPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        model.addAttribute("users", userService.getAll());
        model.addAttribute("allRoles", roleRepository.findAll());

        User user = userService.findUserByUsername(principal.getName());
        if (user != null) {
            model.addAttribute("currentUser", user);
        } else {
            return "redirect:/login";
        }

        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, @RequestParam Set<Long> roles) {
        Set<Role> roleSet = new HashSet<>(roleRepository.findAllById(roles));
        user.setRoles(roleSet);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getById(id);
        List<Role> allRoles = roleRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);

        return "edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roles", required = false) Set<Long> roles) {
        Set<Role> roleSet = new HashSet<>(roleRepository.findAllById(roles));
        user.setRoles(roleSet);

        User existingUser = userService.getById(user.getId());
        user.setPassword(existingUser.getPassword());

        userService.updateUser(user);
        return "redirect:/admin";
    }
}
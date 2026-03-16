package com.fixmyroad.controller;

import org.springframework.web.bind.annotation.*;
import com.fixmyroad.repository.UserRepository;
import com.fixmyroad.model.User;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository repo;

    public AuthController(UserRepository repo) {
        this.repo = repo;
    }

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        User existing = repo.findByEmail(user.getEmail());

        if (existing != null) {
            throw new RuntimeException("Email already exists");
        }

        user.setRole("USER");
        return repo.save(user);
    }

    // LOGIN
    @PostMapping("/login")
    public User login(@RequestBody User user) {

        User dbUser = repo.findByEmail(user.getEmail());

        if (dbUser == null) {
            throw new RuntimeException("User not found");
        }

        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return dbUser;
    }
}
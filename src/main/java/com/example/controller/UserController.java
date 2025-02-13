package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;



@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@Tag(name = "User Management", description = "Endpoints for managing users")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
    if (result.hasErrors()) {
        return ResponseEntity.badRequest().body(result.getAllErrors());
    }
    User savedUser = userService.registerUser(user);
    return ResponseEntity.ok(savedUser);
}


    @GetMapping("/{email}")
    @Operation(summary = "Get user by email")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    @Operation(summary = "Login Your Account")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
    Optional<User> existingUser = userService.findByEmail(user.getEmail());
    if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
        return ResponseEntity.ok(existingUser.get());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
}

}

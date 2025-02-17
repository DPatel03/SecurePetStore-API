package com.example.pet_store.controllers;

import com.example.pet_store.models.User;
import com.example.pet_store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/createWithList")
//    public List<User> createUsersWithList(@RequestBody List<User> users) {
//        return userService.createUsersWithList(users);
//    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @PutMapping("/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        return userService.updateUser(username, user);
    }

    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "User deleted successfully";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user){

        return userService.loginUser(user);
    }

    @GetMapping("/logout")
    public String logoutUser(@RequestParam String username) {
        return userService.logoutUser(username);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}

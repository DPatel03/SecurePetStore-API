package com.example.pet_store.controllers;

import com.example.pet_store.models.User;
import com.example.pet_store.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/createWithList")
    public List<User> createUsersWithList(@RequestBody List<User> users) {
        return userService.createUsersWithList(users);
    }
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

//    @PutMapping("/{username}")
//    public User updateUser(@PathVariable String username, @RequestBody User user) {
//        return userService.updateUser(username, user);
//    }

    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "User deleted successfully";
    }

//    @GetMapping("/login")
//    public String loginUser(@RequestParam String username, @RequestParam String password) {
//        return userService.loginUser(username, password);
//    }

    @PostMapping("/logout")
    public String logoutUser(@RequestParam String username) {
        return userService.logoutUser(username);
    }
}

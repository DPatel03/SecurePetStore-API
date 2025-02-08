package com.example.pet_store;

import com.example.pet_store.models.User;
import com.example.pet_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Store logged-in users (Simple session handling)
    private Map<String, Boolean> loggedInUsers = new HashMap<>();

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    public User register(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> createUsersWithList(List<User> users) {
        return userRepository.saveAll(users);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

//    public User updateUser(String username, User userDetails) {
//        // Find the user by username, which is now the primary key
//        User existingUser = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Update the user fields
//        existingUser.setFirstName(userDetails.getFirstName());
//        existingUser.setLastName(userDetails.getLastName());
//        existingUser.setEmail(userDetails.getEmail());
//        existingUser.setPassword(userDetails.getPassword());
//        existingUser.setPhone(userDetails.getPhone());
//        existingUser.setUserStatus(userDetails.getUserStatus());
//
//        // Save and return the updated user
//        return userRepository.save(existingUser);
//    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

//    public String loginUser(String username, String password) {
//        Optional User user = userRepository.findByUsername(username);
//        if (user.isPresent() && user.get().getPassword().equals(password)) {
//            loggedInUsers.put(username, true);  // Store user as logged in
//            return "Login successful";
//        }
//        return "Invalid username/password";
//    }

    public String logoutUser(String username) {
        if (loggedInUsers.containsKey(username)) {
            loggedInUsers.remove(username);
            return "User logged out successfully";
        }
        return "User is not logged in";
    }
}

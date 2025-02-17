package com.example.pet_store.service;

import com.example.pet_store.models.User;
import com.example.pet_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;


    // Store logged-in users (Simple session handling)
//    private Map<String, Boolean> loggedInUsers = new HashMap<>();

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("CUSTOMER"); // Default role
        }
        return userRepository.save(user);
    }


//    public User createUser(User user) {
//        return userRepository.save(user);
//    }

//    public List<User> createUsersWithList(List<User> users) {
//        users.setPassword(encoder.encode(users.getPassword()));
//        return userRepository.saveAll(users);
//    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(String username, User userDetails) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhone(userDetails.getPhone());
        existingUser.setUserStatus(userDetails.getUserStatus());

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(encoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(String username) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        userRepository.delete(existingUser);
    }

    public String loginUser(User user) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "Failed";


    }

    public String logoutUser(String username) {
        return "Logout successful for user: " + username + ". Please remove the token from the client.";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}

package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public String createUser(User user) {
        // Validate mobile number
        if (user.getMobNum().length() == 10 || user.getMobNum().startsWith("0") || user.getMobNum().startsWith("+91")) {
            user.setMobNum(user.getMobNum().replaceAll("^(\\+91|0)", ""));
        } else {
            throw new IllegalArgumentException("Invalid mobile number");
        }

        // Validate PAN number
        user.setPanNum(user.getPanNum().toUpperCase());
        if (!user.getPanNum().matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
            throw new IllegalArgumentException("Invalid PAN number");
        }

        // Validate manager_id if present
        if (user.getManagerId() != null) {
            managerRepository.findByIdAndIsActiveTrue(user.getManagerId())
                .orElseThrow(() -> new IllegalArgumentException("Manager not found or inactive"));
        }

        userRepository.save(user);
        return "User created successfully!";
    }

    public List<User> getUsers(UUID userId, String mobNum, UUID managerId) {
        if (userId != null) {
            return userRepository.findById(userId).map(List::of).orElse(List.of());
        } else if (mobNum != null) {
            return userRepository.findByMobNum(mobNum).map(List::of).orElse(List.of());
        } else if (managerId != null) {
            return userRepository.findByManagerId(managerId);
        } else {
            return userRepository.findAll();
        }
    }

    public String deleteUser(UUID userId, String mobNum) {
        Optional<User> user = (userId != null) ? userRepository.findById(userId) : userRepository.findByMobNum(mobNum);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return "User deleted successfully!";
        } else {
            return "User not found!";
        }
    }

    public String updateUser(UUID userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setFullName(updatedUser.getFullName());
            user.setMobNum(updatedUser.getMobNum());
            user.setPanNum(updatedUser.getPanNum());
            user.setManagerId(updatedUser.getManagerId());
            userRepository.save(user);
            return "User updated successfully!";
        }).orElse("User not found!");
    }
}
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/get")
    public List<User> getUsers(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String mobNum,
            @RequestParam(required = false) UUID managerId) {
        return userService.getUsers(userId, mobNum, managerId);
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(required = false) UUID userId, @RequestParam(required = false) String mobNum) {
        return userService.deleteUser(userId, mobNum);
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam UUID userId, @RequestBody User updatedUser) {
        return userService.updateUser(userId, updatedUser);
    }
}
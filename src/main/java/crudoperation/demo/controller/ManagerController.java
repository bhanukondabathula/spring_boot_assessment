package com.example.demo.controller;

import com.example.demo.model.Manager;
import com.example.demo.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/create")
    public String createManager(@RequestBody Manager manager) {
        return managerService.createManager(manager);
    }

    @GetMapping("/get")
    public Manager getManagerById(@RequestParam UUID managerId) {
        return managerService.getManagerById(managerId);
    }

    @GetMapping("/get_all")
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    @PostMapping("/update")
    public String updateManager(@RequestParam UUID managerId, @RequestBody Manager updatedManager) {
        return managerService.updateManager(managerId, updatedManager);
    }

    @PostMapping("/delete")
    public String deleteManager(@RequestParam UUID managerId) {
        return managerService.deleteManager(managerId);
    }
}
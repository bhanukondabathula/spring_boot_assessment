package com.example.demo.service;

import com.example.demo.model.Manager;
import com.example.demo.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    public String createManager(Manager manager) {
        managerRepository.save(manager);
        return "Manager created successfully!";
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Manager getManagerById(UUID managerId) {
        return managerRepository.findByIdAndIsActiveTrue(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found or inactive"));
    }

    public String updateManager(UUID managerId, Manager updatedManager) {
        return managerRepository.findById(managerId).map(manager -> {
            manager.setName(updatedManager.getName());
            managerRepository.save(manager);
            return "Manager updated successfully!";
        }).orElse("Manager not found!");
    }

    public String deleteManager(UUID managerId) {
        return managerRepository.findById(managerId).map(manager -> {
            managerRepository.delete(manager);
            return "Manager deleted successfully!";
        }).orElse("Manager not found!");
    }
}
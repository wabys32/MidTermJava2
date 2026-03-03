package com.arthurtokarev.bankmanagement.service;

import com.arthurtokarev.bankmanagement.entity.BankUser;
import com.arthurtokarev.bankmanagement.entity.Building;
import com.arthurtokarev.bankmanagement.entity.UserProfile;
import com.arthurtokarev.bankmanagement.repository.DepartmentRepository;
import com.arthurtokarev.bankmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public BankUser createUser(BankUser bankUser) {
        if (bankUser.getUserProfile() != null) {
            bankUser.getUserProfile().setBankUser(bankUser);
        }
        return userRepository.save(bankUser);
    }
    @Transactional
    public void assignUserToBuilding(Long userId, Long buildingId) {
        BankUser bankUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Building building = departmentRepository.findById(buildingId).orElseThrow(() -> new RuntimeException("Building not found"));
        bankUser.getBuildings().add(building);
        building.getBankUsers().add(bankUser);
        userRepository.save(bankUser);
    }
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<BankUser> getAllUsers() {
        return userRepository.findAll();
    }
    @Transactional(readOnly = true)
    public BankUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    @Transactional
    public BankUser updateUser(Long id, BankUser updatedBankUser) {
        BankUser existingBankUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        existingBankUser.setFirstName(updatedBankUser.getFirstName());
        existingBankUser.setLastName(updatedBankUser.getLastName());
        existingBankUser.setEmail(updatedBankUser.getEmail());
        // update profile safely
        if (updatedBankUser.getUserProfile() != null) {
            UserProfile profile = updatedBankUser.getUserProfile();
            profile.setBankUser(existingBankUser);
            existingBankUser.setUserProfile(profile);
        }
        return userRepository.save(existingBankUser);
    }

    @Transactional
    public void removeUsersFromBuilding(Long userId, Long buildingId) {
        BankUser bankUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Building building = departmentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Building not found"));

        bankUser.getBuildings().remove(building);
        building.getBankUsers().remove(bankUser);
    }

    @Transactional(readOnly = true)
    public UserProfile getUserProfile(Long userId) {
        BankUser bankUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bankUser.getUserProfile();
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}


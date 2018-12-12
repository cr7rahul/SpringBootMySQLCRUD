package com.example.resttest.testrest.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.resttest.testrest.ecxeption.ResourceNotFoundException;
import com.example.resttest.testrest.model.UserModel;
import com.example.resttest.testrest.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    /**
     * Create
     */
    @PostMapping("/users")
    public UserModel saveUsers(@Valid @RequestBody UserModel userModel) {
        return userRepository.save(userModel);
    }

    /**
     * update
     */
    @PutMapping("/users/{id}")
    public UserModel updateUsers(@PathVariable(value = "id") Integer userID, @Valid @RequestBody UserModel userModel) {
        UserModel mUserModel = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
        mUserModel.setUsername(userModel.getUsername());
        mUserModel.setFirst_name(userModel.getFirst_name());
        mUserModel.setLast_name(userModel.getLast_name());
        mUserModel.setEmail(userModel.getEmail());
        UserModel updatedUserModel = userRepository.save(mUserModel);
        return updatedUserModel;
    }

    /**
     * Retrieve all
     */
    @GetMapping("/users")
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * retrieve one
     */
    @GetMapping("/users/{id}")
    public UserModel getSingleUser(@PathVariable(value = "id") Integer userID) {
        return userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User Details", "id", userID));
    }

    /**
     * delete
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Integer userID) {
        UserModel mUserModel = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
        userRepository.delete(mUserModel);
        return ResponseEntity.ok().build();
    }

    /**
     * Duplicate
     */
    @GetMapping("/duplicateusers/{id}")
    public int checkDuplicate(@PathVariable(value = "id") Integer userID) {
        if(userRepository.existsById(userID)){
            return 1;
        }else{
            return 0;
        }
    }
}
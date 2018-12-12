package com.example.resttest.testrest.repository;

import com.example.resttest.testrest.model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

}
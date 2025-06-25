package com.smarthealth.controller;

import com.smarthealth.model.entity.User;
import com.smarthealth.model.entity.Role;
import com.smarthealth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("")
    public ResponseEntity<List<User>> getAllDoctors() {
        List<User> doctors = userRepository.findByRole(Role.DOCTOR);
        return ResponseEntity.ok(doctors);
    }


}

package com.smarthealth.repository;

import com.smarthealth.model.entity.Prescription;
import com.smarthealth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByAppointment_Patient(User patient);
}

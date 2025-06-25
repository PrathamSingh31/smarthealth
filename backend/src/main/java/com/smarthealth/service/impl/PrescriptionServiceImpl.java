package com.smarthealth.service.impl;

import com.smarthealth.model.entity.*;
import com.smarthealth.repository.AppointmentRepository;
import com.smarthealth.repository.PrescriptionRepository;
import com.smarthealth.repository.UserRepository;
import com.smarthealth.service.impl.PrescriptionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   AppointmentRepository appointmentRepository,
                                   UserRepository userRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Prescription createPrescription(Long appointmentId, String content) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setContent(content);
        prescription.setCreatedAt(LocalDateTime.now());

        return prescriptionRepository.save(prescription);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return prescriptionRepository.findByAppointment_Patient(patient);
    }

    @Override
    public Prescription getById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

}

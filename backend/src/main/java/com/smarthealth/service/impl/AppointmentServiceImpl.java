package com.smarthealth.service.impl;

import com.smarthealth.model.entity.*;
import com.smarthealth.repository.AppointmentRepository;
import com.smarthealth.repository.UserRepository;
import com.smarthealth.service.impl.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime dateTime) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(dateTime);
        appointment.setStatus(AppointmentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateStatus(Long appointmentId, String statusStr) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        AppointmentStatus status = AppointmentStatus.valueOf(statusStr.toUpperCase());
        appointment.setStatus(status);

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsForUser(User user) {
        if (user.getRole() == Role.DOCTOR) {
            return appointmentRepository.findByDoctor(user);
        } else if (user.getRole() == Role.PATIENT) {
            return appointmentRepository.findByPatient(user);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(String email) {
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return appointmentRepository.findByPatient(patient);
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(String email) {
        User doctor = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentRepository.findByDoctor(doctor);
    }

}

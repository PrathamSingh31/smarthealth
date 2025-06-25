package com.smarthealth.service.impl;

import com.smarthealth.model.entity.Appointment;
import com.smarthealth.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime dateTime);
    Appointment updateStatus(Long appointmentId, String status);
    List<Appointment> getAppointmentsForUser(User user);
    List<Appointment> getAppointmentsForPatient(String email);
    List<Appointment> getAppointmentsForDoctor(String email);




}
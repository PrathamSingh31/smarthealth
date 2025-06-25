package com.smarthealth.controller;

import com.smarthealth.model.entity.Appointment;
import com.smarthealth.model.entity.User;
import com.smarthealth.security.UserPrincipal;
import com.smarthealth.service.impl.AppointmentService;
import com.smarthealth.service.impl.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserService userService;

    public AppointmentController(AppointmentService appointmentService,
                                 UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime
    ) {
        Appointment appointment = appointmentService.bookAppointment(principal.getId(), doctorId, dateTime);
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(
            @PathVariable Long id,
            @RequestParam String status // e.g., "CONFIRMED" or "REJECTED"
    ) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments(@AuthenticationPrincipal UserPrincipal principal) {
        User user = userService.getUserById(principal.getId());
        return ResponseEntity.ok(appointmentService.getAppointmentsForUser(user));
    }

    @GetMapping("/patient")
    public ResponseEntity<List<Appointment>> getAppointmentsForPatient(Principal principal) {
        List<Appointment> appointments = appointmentService.getAppointmentsForPatient(principal.getName());
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor")
    public ResponseEntity<List<Appointment>> getAppointmentsForDoctor(Principal principal) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(principal.getName());
        return ResponseEntity.ok(appointments);
    }

}

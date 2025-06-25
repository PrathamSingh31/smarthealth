package com.smarthealth.controller;

import com.smarthealth.model.entity.Prescription;
import com.smarthealth.security.UserPrincipal;
import com.smarthealth.service.impl.PrescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.smarthealth.utils.PdfGenerator;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Prescription> createPrescription(
            @RequestParam Long appointmentId,
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        // Optionally check if doctor owns this appointment
        String content = body.get("content");
        return ResponseEntity.ok(prescriptionService.createPrescription(appointmentId, content));
    }

    @GetMapping("/patient")
    public ResponseEntity<List<Prescription>> getMyPrescriptions(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatientId(principal.getId()));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPrescriptionPdf(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getById(id); // create this method if missing

        byte[] pdfData = PdfGenerator.generatePrescriptionPdf(prescription);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=prescription_" + id + ".pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfData);
    }



}

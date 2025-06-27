package com.smarthealth.controller;

import com.smarthealth.model.entity.Prescription;
import com.smarthealth.security.UserPrincipal;
import com.smarthealth.service.impl.PrescriptionService;
import com.smarthealth.utils.PdfGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    // ✅ Only DOCTOR can create prescriptions
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/create")
    public ResponseEntity<Prescription> createPrescription(
            @RequestParam Long appointmentId,
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        String content = body.get("content");
        return ResponseEntity.ok(
                prescriptionService.createPrescription(appointmentId, principal.getId(), content)
        );
    }



    // ✅ Only PATIENT can view their prescriptions
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patient")
    public ResponseEntity<List<Prescription>> getMyPrescriptions(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByPatientId(principal.getId())
        );
    }

    // ✅ Accessible to both roles (or restrict as needed)
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPrescriptionPdf(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getById(id); // Ensure this method exists

        byte[] pdfData = PdfGenerator.generatePrescriptionPdf(prescription);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=prescription_" + id + ".pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfData);
    }
}

// File: com.smarthealth.service.impl.PrescriptionService.java

package com.smarthealth.service.impl;

import com.smarthealth.model.entity.Prescription;

import java.util.List;

public interface PrescriptionService {
    Prescription createPrescription(Long appointmentId, Long doctorId, String content); // ✅ updated
    List<Prescription> getPrescriptionsByPatientId(Long patientId);
    Prescription getById(Long id);
}

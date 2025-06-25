package com.smarthealth.service.impl;

import com.smarthealth.model.entity.Prescription;
import java.util.List;

public interface PrescriptionService {
    Prescription createPrescription(Long appointmentId, String content);
    List<Prescription> getPrescriptionsByPatientId(Long patientId);
    Prescription getById(Long id);

}
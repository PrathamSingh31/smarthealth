package com.smarthealth.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.smarthealth.model.entity.Prescription;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generatePrescriptionPdf(Prescription prescription) {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("SmartHealth Prescription", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Date: " + prescription.getCreatedAt(), bodyFont));
            document.add(new Paragraph("Doctor: " + prescription.getAppointment().getDoctor().getName(), bodyFont));
            document.add(new Paragraph("Patient: " + prescription.getAppointment().getPatient().getName(), bodyFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Prescription:", bodyFont));
            document.add(new Paragraph(prescription.getContent(), bodyFont));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        }
    }
}

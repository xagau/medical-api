package com.xagau.medical.controller;

import com.xagau.medical.model.PdfMedicalFile;
import com.xagau.medical.model.Patient;
import com.xagau.medical.repository.PdfMedicalFileRepository;
import com.xagau.medical.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-files")
public class MedicalFileController {

    @Autowired
    private PdfMedicalFileRepository pdfMedicalFileRepository;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/upload")
    public ResponseEntity<PdfMedicalFile> uploadMedicalFile(
            @io.swagger.v3.oas.annotations.Parameter(description = "Patient ID")
            @RequestParam Long patientId,
            @io.swagger.v3.oas.annotations.Parameter(description = "PDF file", schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", format = "binary"))
            @RequestParam MultipartFile file,
            @io.swagger.v3.oas.annotations.Parameter(description = "Description of the file")
            @RequestParam(required = false) String description) {
        
        // Check if patient exists
        if (!patientRepository.existsById(patientId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            // Get the patient
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            // Create and configure the medical file
            PdfMedicalFile medicalFile = new PdfMedicalFile();
            medicalFile.setDescription(description);
            medicalFile.setContentType(file.getContentType());
            medicalFile.setFileName(file.getOriginalFilename());
            medicalFile.setData(file.getBytes());
            
            // Set the relationship
            patient.addMedicalFile(medicalFile);
            
            PdfMedicalFile savedFile = pdfMedicalFileRepository.save(medicalFile);
            return new ResponseEntity<>(savedFile, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PdfMedicalFile>> getMedicalFilesByPatient(@PathVariable Long patientId) {
        // If patient is not found, return 404. Reminder: create the patient first if not found.
        return patientRepository.findById(patientId)
                .map(patient -> {
                    List<PdfMedicalFile> files = pdfMedicalFileRepository.findByPatient_Id(patient.getId());
                    return ResponseEntity.ok(files);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteMedicalFile(@PathVariable Long fileId) {
        if (pdfMedicalFileRepository.existsById(fileId)) {
            PdfMedicalFile file = pdfMedicalFileRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("File not found"));
            Patient patient = file.getPatient();
            patient.removeMedicalFile(file);
            pdfMedicalFileRepository.deleteById(fileId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

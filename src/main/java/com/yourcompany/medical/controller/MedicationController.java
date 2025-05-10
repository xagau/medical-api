package com.yourcompany.medical.controller;

import com.yourcompany.medical.model.Medication;
import com.yourcompany.medical.repository.MedicationRepository;
import com.yourcompany.medical.repository.PatientRepository;
import com.yourcompany.medical.model.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/medications")
public class MedicationController {

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<Medication> addMedication(
            @PathVariable Long patientId,
            @RequestBody Medication medication) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (!optionalPatient.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        medication.setPatient(optionalPatient.get());
        return ResponseEntity.ok(medicationRepository.save(medication));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Medication>> getMedicationsForPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicationRepository.findByPatientId(patientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        if (!medicationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

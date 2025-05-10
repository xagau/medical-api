package com.yourcompany.medical.controller;

import com.yourcompany.medical.model.Patient;
import com.yourcompany.medical.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // Create patient
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    // List all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Get patient by ID
    @GetMapping("/{id}")
    public Optional<Patient> getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id);
    }

    // Update patient
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient updated) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setEmail(updated.getEmail());
                    patient.setPassword(updated.getPassword());
                    patient.setPreferences(updated.getPreferences());
                    return patientRepository.save(patient);
                })
                .orElseGet(() -> {
                    updated.setId(id);
                    return patientRepository.save(updated);
                });
    }

    // Delete patient
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
    }
}


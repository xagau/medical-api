package com.xagau.medical.controller;

import com.xagau.medical.model.Diagnosis;
import com.xagau.medical.model.Patient;
import com.xagau.medical.repository.DiagnosisRepository;
import com.xagau.medical.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/diagnoses")
public class DiagnosisController {
    @Autowired
    private DiagnosisRepository diagnosisRepository;
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Diagnosis>> getDiagnosesByPatient(@PathVariable Long patientId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (!patientOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Diagnosis> diagnoses = diagnosisRepository.findByPatient_Id(patientId);
        return ResponseEntity.ok(diagnoses);
    }

    @PostMapping
    public ResponseEntity<Diagnosis> createDiagnosis(@RequestBody Diagnosis diagnosis) {
        if (diagnosis.getPatient() == null || diagnosis.getPatient().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Patient> patientOpt = patientRepository.findById(diagnosis.getPatient().getId());
        if (!patientOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        diagnosis.setPatient(patientOpt.get());
        Diagnosis saved = diagnosisRepository.save(diagnosis);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diagnosis> getDiagnosis(@PathVariable Long id) {
        return diagnosisRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Diagnosis> updateDiagnosis(@PathVariable Long id, @RequestBody Diagnosis updatedDiagnosis) {
        return diagnosisRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedDiagnosis.getName());
                    existing.setDescription(updatedDiagnosis.getDescription());
                    existing.setDateDiagnosed(updatedDiagnosis.getDateDiagnosed());
                    Diagnosis saved = diagnosisRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        if (!diagnosisRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        diagnosisRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

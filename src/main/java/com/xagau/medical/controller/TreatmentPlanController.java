package com.xagau.medical.controller;

import com.xagau.medical.model.TreatmentPlan;
import com.xagau.medical.model.Patient;
import com.xagau.medical.repository.TreatmentPlanRepository;
import com.xagau.medical.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/treatment-plans")
public class TreatmentPlanController {
    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public List<TreatmentPlan> getAllTreatmentPlans() {
        return treatmentPlanRepository.findAll();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<TreatmentPlan>> getPlansByPatient(@PathVariable Long patientId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (!patientOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<TreatmentPlan> plans = treatmentPlanRepository.findByPatient_Id(patientId);
        return ResponseEntity.ok(plans);
    }

    @PostMapping
    public ResponseEntity<TreatmentPlan> createTreatmentPlan(@RequestBody TreatmentPlan plan) {
        if (plan.getPatient() == null || plan.getPatient().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Patient> patientOpt = patientRepository.findById(plan.getPatient().getId());
        if (!patientOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        plan.setPatient(patientOpt.get());
        TreatmentPlan saved = treatmentPlanRepository.save(plan);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentPlan> getTreatmentPlan(@PathVariable Long id) {
        return treatmentPlanRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentPlan> updateTreatmentPlan(@PathVariable Long id, @RequestBody TreatmentPlan updatedPlan) {
        return treatmentPlanRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedPlan.getName());
                    existing.setDescription(updatedPlan.getDescription());
                    existing.setStartDate(updatedPlan.getStartDate());
                    existing.setEndDate(updatedPlan.getEndDate());
                    TreatmentPlan saved = treatmentPlanRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatmentPlan(@PathVariable Long id) {
        if (!treatmentPlanRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        treatmentPlanRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

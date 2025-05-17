package com.xagau.medical.controller;

import com.xagau.medical.model.Condition;
import com.xagau.medical.model.Patient;
import com.xagau.medical.repository.ConditionRepository;
import com.xagau.medical.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/conditions")
public class ConditionController {

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/add")
    public Condition addCondition(@RequestParam Long patientId,
                                  @RequestParam String name,
                                  @RequestParam(required = false) String notes) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            throw new RuntimeException("Patient not found");
        }

        Condition condition = new Condition();
        condition.setPatient(patientOpt.get());
        condition.setName(name);
        condition.setNotes(notes);

        return conditionRepository.save(condition);
    }

    @GetMapping("/patient/{patientId}")
    public List<Condition> getConditionsByPatient(@PathVariable Long patientId) {
        return conditionRepository.findAll()
                .stream()
                .filter(c -> c.getPatient().getId().equals(patientId))
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteCondition(@PathVariable Long id) {
        conditionRepository.deleteById(id);
    }
}

package com.yourcompany.medical.controller;

import com.yourcompany.medical.model.Patient;
import com.yourcompany.medical.repository.PatientRepository;
import com.yourcompany.medical.service.OpenAIClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientQueryController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private OpenAIClientService openAIClientService;

    @PostMapping("/{id}/query")
    public Mono<ResponseEntity<String>> queryPatientRecords(@PathVariable Long id, @RequestBody String question) {
        return Mono.fromCallable(() -> patientRepository.findById(id).orElse(null))
                .flatMap(patient -> {
                    if (patient == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }

                    // Extract PDF-like text content from all medical files
                    List<String> fileTexts = patient.getMedicalFiles().stream()
                            .map(file -> file.getContent())
                            .toList();

                    return openAIClientService.summarizeMedicalRecords(fileTexts)
                            .map(response -> ResponseEntity.ok().body(response));
                });
    }
}

package com.xagau.medical.controller;

import com.xagau.medical.service.LLMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/assistant")
public class LLMController {
    private static final Logger logger = LoggerFactory.getLogger(LLMController.class);

    private final LLMService llmService;
    private final com.xagau.medical.repository.PdfMedicalFileRepository pdfMedicalFileRepository;
    private final com.xagau.medical.service.PdfTextExtractor pdfTextExtractor;

    public LLMController(LLMService llmService,
                         com.xagau.medical.repository.PdfMedicalFileRepository pdfMedicalFileRepository,
                         com.xagau.medical.service.PdfTextExtractor pdfTextExtractor) {
        this.llmService = llmService;
        this.pdfMedicalFileRepository = pdfMedicalFileRepository;
        this.pdfTextExtractor = pdfTextExtractor;
    }

    @PostMapping("/query")
    public ResponseEntity<Map<String, Object>> queryAssistant(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "LLM Query Request") @RequestBody com.xagau.medical.model.LLMQueryRequest request) {
        boolean usedContext = false;
        Map<String, Object> context = null;

        if (Boolean.TRUE.equals(request.getIncludeContext()) && request.getPatientId() != null && !request.getPatientId().isEmpty()) {
            try {
                Long patientId = Long.valueOf(request.getPatientId());
                java.util.List<com.xagau.medical.model.PdfMedicalFile> pdfFiles = pdfMedicalFileRepository.findByPatient(patientId);
                StringBuilder sb = new StringBuilder();
                for (com.xagau.medical.model.PdfMedicalFile file : pdfFiles) {
                    String text = pdfTextExtractor.extractText(file.getData());
                    sb.append("\n--- File: ").append(file.getFileName()).append(" ---\n");
                    sb.append(text).append("\n");
                }
                if (sb.length() > 0) {
                    context = new HashMap<>();
                    context.put("pdfContext", sb.toString());
                    usedContext = true;
                }
            } catch (Exception e) {
                // Log and proceed without context
                org.slf4j.LoggerFactory.getLogger(LLMController.class).error("Error extracting PDF context", e);
            }
        }

        String response = llmService.queryLLM(request.getQuery(), context);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("response", response);
        responseBody.put("usedContext", usedContext);

        return ResponseEntity.ok(responseBody);
    }


}

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

    public LLMController(LLMService llmService) {
        this.llmService = llmService;
    }

    @PostMapping("/query")
    public ResponseEntity<Map<String, Object>> queryAssistant(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "LLM Query Request") @RequestBody com.xagau.medical.model.LLMQueryRequest request) {
        boolean usedContext = false;
        Map<String, Object> context = null;

        if (Boolean.TRUE.equals(request.getIncludeContext()) && request.getPatientId() != null) {
            context = getMockPatientContext(request.getPatientId());
            usedContext = true;
        }

        String response = llmService.queryLLM(request.getQuery(), context);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("response", response);
        responseBody.put("usedContext", usedContext);

        return ResponseEntity.ok(responseBody);
    }

    // Simulate fetching patient context
    private Map<String, Object> getMockPatientContext(String patientId) {
        Map<String, Object> mock = new HashMap<>();
        mock.put("patientId", patientId);
        mock.put("name", "John Doe");
        mock.put("age", 42);
        mock.put("medicalHistory", "Hypertension, Diabetes");
        return mock;
    }

}

package com.xagau.medical.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class LLMService {
    private static final Logger logger = LoggerFactory.getLogger(LLMService.class);

    @Value("${llm.api.key}")
    private String apiKey;

    @Value("${llm.api.url:https://api.openai.com/v1/chat/completions}")
    private String llmApiUrl;

    private final WebClient webClient = WebClient.builder().build();

    public String queryLLM(String userQuery, Map<String, Object> context) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");
            // Compose the prompt with context if provided
            String prompt = userQuery;
            if (context != null && !context.isEmpty()) {
                prompt = "Patient context: " + context.toString() + "\nUser query: " + userQuery;
            }
            requestBody.put("messages", new Object[]{
                Map.of("role", "user", "content", prompt)
            });

            return webClient.post()
                    .uri(llmApiUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(resp -> {
                        // Parse OpenAI response format
                        try {
                            var choices = (java.util.List<Map<String, Object>>) resp.get("choices");
                            if (choices != null && !choices.isEmpty()) {
                                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                                return message != null ? message.get("content").toString() : "No response";
                            }
                        } catch (Exception e) {
                            logger.error("Error parsing LLM response", e);
                        }
                        return "No response";
                    })
                    .block();
        } catch (Exception ex) {
            logger.error("Error querying LLM", ex);
            return "LLM service error: " + ex.getMessage();
        }
    }
}

package com.yourcompany.medical.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIClientService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    public OpenAIClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public Mono<String> summarizeMedicalRecords(List<String> pdfTexts) {
        String joinedText = String.join("\n\n", pdfTexts);

        Map<String, Object> request = Map.of(
            "model", "gpt-4",
            "messages", List.of(
                Map.of("role", "system", "content", "You are a medical assistant helping users understand lab reports."),
                Map.of("role", "user", "content", "Here are the records:\n" + joinedText + "\n\nSummarize any concerns.")
            )
        );

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + openAiApiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                    return choices.get(0).get("message").toString();
                });
    }
}

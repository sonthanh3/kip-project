package io.kip.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KipperService {

    private final RestTemplate restTemplate;

    @Value("${kipper.claude.api-key}")
    private String apiKey;

    @Value("${kipper.claude.url}")
    private String claudeUrl;

    public KipperService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String getFeedback(BigDecimal totalSpent, int transactionCount, 
                           Map<String, BigDecimal> categoryBreakdown,
                           BigDecimal previousWeekTotal, int anomalyCount) {
        try {
            // 1. Build prompt string
            String prompt = "You are Kipper, a calm and neutral spending coach. " +
            "This week the user spent $" + totalSpent + " across " + transactionCount + " transactions. " +
            "Last week they spent $" + previousWeekTotal + ". " +
            "Category breakdown: " + categoryBreakdown + ". " +
            (anomalyCount > 0 ? "There were " + anomalyCount + " unusually large transactions this week. " : "") +
            "Give brief, neutral, shame-free feedback in 2-3 sentences.";

            // Build request body
            Map<String, Object> message = Map.of(
                "role", "user",
                "content", prompt);
            Map<String, Object> requestBody = Map.of(
                "model", "claude-sonnet-4-6",
                "max_tokens", 256,
                "messages", List.of(message));

            // Set headers
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.set("anthropic-version", "2023-06-01");
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);   
            
            org.springframework.http.HttpEntity<Map<String, Object>> entity = new org.springframework.http.HttpEntity<>(requestBody, headers);

            // 3. Call API
            Map<String, Object> response = restTemplate.postForObject(claudeUrl, entity, Map.class);

            // 4. Extract and return response text
            List<Map> content = (List<Map>) response.get("content");
                return (String) content.get(0).get("text");

        } catch (Exception e) {
            System.out.println("Kipper AI failed: " + e.getMessage());
            return getRuleBasedFeedback(totalSpent);
        }
    }

    private String getRuleBasedFeedback(BigDecimal totalSpent) {
        if (totalSpent.compareTo(new BigDecimal("500")) > 0) {
            return "Your spending this week was high. Consider reviewing your largest transactions.";
        } else if (totalSpent.compareTo(new BigDecimal("200")) > 0) {
            return "Your spending this week was moderate. You are on a reasonable track.";
        } else {
            return "Great week. Your spending was low and well controlled.";
        }
    
    }
    
}

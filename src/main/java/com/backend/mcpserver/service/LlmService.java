package com.backend.mcpserver.service;

import com.backend.mcpserver.model.GroqConfig;
import com.backend.mcpserver.prompts.PromptTemplates;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
public class LlmService {

    private static final Logger log = LoggerFactory.getLogger(LlmService.class);

    @Autowired
    private GroqConfig groqConfig;

    @Autowired
    private ObjectMapper objectMapper;


    public String ask(String question, String context) {
        log.info("Received question: {}", question);
        String prompt = buildPrompt(context, question);
        String rawResponse = callLlm(prompt);
        return parseResponse(rawResponse);
    }


    private String buildPrompt(String context, String question) {
        return String.format(PromptTemplates.HR_ASSISTANT, context, question);
    }

    private String callLlm(String prompt) {
        try {
            HttpURLConnection conn = createConnection();
            String jsonBody = buildJsonBody(prompt);

            log.debug("Calling Groq API with model: {}", groqConfig.getModel());
            sendRequest(conn, jsonBody);

            int responseCode = conn.getResponseCode();
            log.info("Groq response code: {}", responseCode);

            InputStream is = responseCode == 200
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            return new String(is.readAllBytes());

        } catch (Exception e) {
            log.error("Failed to call Groq API: {}", e.getMessage());
            throw new RuntimeException("LLM call failed: " + e.getMessage());
        }
    }

    private String parseResponse(String rawResponse) {
        JSONObject json = new JSONObject(rawResponse);
        return json.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }

    private HttpURLConnection createConnection() throws Exception {
        URL url = new URL(groqConfig.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + groqConfig.getApi().getKey());
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        return conn;
    }

    private String buildJsonBody(String prompt) {
        try {
            Map<String, Object> body = Map.of(
                    "model", groqConfig.getModel(),
                    "max_tokens", groqConfig.getMaxTokens(),
                    "messages", List.of(
                            Map.of("role", "user", "content", prompt)
                    )
            );
            return objectMapper.writeValueAsString(body);
        } catch (Exception e) {
            throw new RuntimeException("Failed to build JSON: " + e.getMessage());
        }
    }

    private void sendRequest(HttpURLConnection conn, String jsonBody) throws Exception {
        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes());
        }
    }
}
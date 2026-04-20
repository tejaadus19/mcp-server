package com.backend.mcpserver.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "groq")
public class GroqConfig {
    private String url;
    private String model;
    private int maxTokens;
    private Api api = new Api();

    @Data
    public static class Api {
        private String key;
    }
}
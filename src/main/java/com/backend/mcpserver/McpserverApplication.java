package com.backend.mcpserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class McpserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(McpserverApplication.class, args);
    }
}

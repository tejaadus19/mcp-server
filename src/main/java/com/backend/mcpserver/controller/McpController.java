package com.backend.mcpserver.controller;

import com.backend.mcpserver.service.EmployeeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mcp")
public class McpController {

    @Autowired
    private EmployeeTools employeeTools;

    @PostMapping("/ask")
    public Map<String, String> ask(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        String answer = employeeTools.ask(question);
        return Map.of("answer", answer);
    }
}
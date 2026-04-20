package com.backend.mcpserver.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class McpTool {

    private  String name;
    private  String description;
    private Map<String, String> parameters;
}

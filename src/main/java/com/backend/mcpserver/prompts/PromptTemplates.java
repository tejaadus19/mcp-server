package com.backend.mcpserver.prompts;

public class PromptTemplates {

    public static final String HR_ASSISTANT =
            "You are an HR assistant. Here is the employee data:\n\n"
                    + "%s"
                    + "\n\nQuestion: %s"
                    + "\n\nUse the employee data above to answer HR questions. "
                    + "For other questions like news, time, or general knowledge, "
                    + "use your own training knowledge.";

    private PromptTemplates() {}
}
package com.backend.mcpserver.Cli;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.json.JSONObject;

public class ChatCli {

    private static final String MCP_URL = "http://localhost:8080/mcp/ask";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("  Welcome to MCP Chat!");
        System.out.println("  Type 'exit' to quit");
        System.out.println("=================================");

        while (true) {
            System.out.print("\nYou: ");
            String question = scanner.nextLine();

            if (question.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            try {
                String answer = askMcpServer(question);
                System.out.println("\nAssistant: " + answer);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static String askMcpServer(String question) throws Exception {
        URL url = new URL(MCP_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonBody = "{\"question\": \"" + question + "\"}";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes());
        }

        InputStream is = conn.getResponseCode() == 200
                ? conn.getInputStream()
                : conn.getErrorStream();

        String response = new String(is.readAllBytes());
        JSONObject json = new JSONObject(response);
        return json.getString("answer");
    }
}
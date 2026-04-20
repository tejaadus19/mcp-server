
# MCP Server  AI-Powered Chat Application

A Model Context Protocol (MCP) Server built with Java Spring Boot that connects an LLM (Groq/Llama3) to multiple data sources including CSV files, REST APIs, Kafka streams, and Google Drive.

## Architecture

```
User Question
↓
McpController        → receives HTTP request
↓
EmployeeTools        → builds context from data sources
↓
LlmService           → calls Groq API with context + question
↓
Groq (Llama3)        → reasons over data and returns answer
↓
Response
```

## Tech Stack

- Java 21
- Spring Boot 3.3.5
- Groq API (Llama 3.3 70B)
- OpenCSV
- Lombok
- Jackson
- Apache Kafka
- Google Drive API
- Railway (deployment)

## API Endpoints

### Ask a question

```
POST /mcp/ask
Content-Type: application/json

{
"question": "how many engineers do we have?"
}
```

### Response

```json
{
  "answer": "There are 3 engineers: Alice Johnson, Carol White, and Eva Martinez."
}
```

## Data Sources

| Source | Description |
|--------|-------------|
| CSV Files | Employee data loaded from resources |
| REST APIs | Live weather and news data |
| Apache Kafka | Real time streaming data |
| Google Drive | Documents and spreadsheets |

## Setup

### Prerequisites

- Java 21
- Maven
- Groq API key — free at console.groq.com

### Local Setup

1. Clone the repo

```bash
git clone https://github.com/tejaadus19/mcp-server.git
cd mcp-server
```

2. Set environment variable

```bash
export GROQ_API_KEY=your_groq_api_key
```

3. Run the app

```bash
./mvnw spring-boot:run
```

4. Test with curl

```bash
curl -X POST http://localhost:8080/mcp/ask \
  -H "Content-Type: application/json" \
  -d '{"question": "how many engineers do we have?"}'
```

## Example Questions

| Question | Source |
|----------|--------|
| How many engineers do we have? | CSV data |
| Who works in New York? | CSV data |
| What is the salary budget for Engineering? | CSV data |
| Who is the highest paid employee? | CSV data |
| What is the capital of France? | LLM knowledge |
| What is today's weather in NYC? | REST API |
| What are today's top news stories? | REST API |


## Deployment

Deployed on Railway.app

Set the following environment variable in Railway:

```
GROQ_API_KEY=your_groq_api_key
```

## Author

Surya — Senior Software Developer 
GitHub: github.com/tejaadus19


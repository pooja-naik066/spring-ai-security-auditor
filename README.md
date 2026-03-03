# spring-ai-security-auditor
 RAG (Retrieval-Augmented Generation) pipeline built with Spring AI, PGVector, and Ollama to identify security vulnerabilities and PII leaks in confidential documents via automated semantic auditing.

## 🏗️ Technical Architecture
1. **Ingestion Pipeline:** Uses `PagePdfDocumentReader` to parse PDFs and `TokenTextSplitter` to create manageable text chunks.
2. **Vector Storage:** Chunks are converted into 768-dimension vectors using `nomic-embed-text` and stored in **Postgres (PGVector)**.
3. **Audit Engine:** When a query is received, the `QuestionAnswerAdvisor` retrieves relevant context and prompts **Llama 3.2** to generate a structured security report.

## 🛠️ Tech Stack
* **Language:** Java 21 (Spring Boot 3.4)
* **AI Framework:** Spring AI (Advisors, Vector Store)
* **LLM Engine:** Ollama (Llama 3.2:3b)
* **Database:** PostgreSQL + PGVector
* **Containerization:** Docker & Docker Compose



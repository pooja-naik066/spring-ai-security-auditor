package com.ai_project.doc_auditor_service.controller;

import com.ai_project.doc_auditor_service.dto.AuditResponse;
import com.ai_project.doc_auditor_service.service.IngestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auditor")
@Slf4j
public class AuditorController {
    @Autowired
    private IngestionService ingestionService;

    private final ChatClient chatClient;

    private final VectorStore vectorStore;

    @Value("classpath:/prompts/security-audit.st")
    private Resource auditPromptResource;

    public AuditorController(IngestionService ingestionService, ChatClient.Builder  builder, VectorStore vectorStore){
        this.ingestionService=ingestionService;
        this.vectorStore = vectorStore;
        var qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .build();

        this.chatClient = builder
                .defaultAdvisors(qaAdvisor)
                .build();

    }

    @PostMapping("/ingest")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile multipartFile) {
        try {
            ingestionService.ingestPdf(multipartFile.getResource());
            return ResponseEntity.ok("PDF ingested and vectorized successfully");
        } catch (Exception e) {
            log.error("Ingestion failed", e);
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

        @GetMapping("/audit")
        public AuditResponse runAudit(@RequestParam(value = "query", defaultValue = "Audit the document") String query) {
            return chatClient.prompt()
                    .system(auditPromptResource)
                    .user(query)
                    .call()
                    .entity(AuditResponse.class);
        }
}

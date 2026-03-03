package com.ai_project.doc_auditor_service.dto;

public record SecurityFinding(String type,String description,int pageNumber,String severity) {
}

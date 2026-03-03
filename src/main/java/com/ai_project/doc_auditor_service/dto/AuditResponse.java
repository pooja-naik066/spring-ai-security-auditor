package com.ai_project.doc_auditor_service.dto;

import java.util.List;

public record AuditResponse(String fileName,String overallSecurityScore,List<SecurityFinding> securityFindings,String executiveSummary) {
}



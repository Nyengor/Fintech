package org.example.fintech.event;

import org.example.fintech.service.AuditService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener {

    private final AuditService auditService;

    public AuditEventListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @Async
    @EventListener
    public void handleAuditEvent(AuditEvent event) {
        auditService.saveAuditLog(event);
    }
}

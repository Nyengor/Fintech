package org.example.fintech.event;

import java.time.LocalDateTime;

public class AuditEvent {
    private final String status;
    private final String ipAddress;
    private final String action;
    private final String entity;
    private final String entityId;
    private final String userEmail;
    private final LocalDateTime timestamp;

    public AuditEvent(String status, String ipAddress, String action, String entity, String entityId, String userEmail, LocalDateTime timestamp) {
        this.status = status;
        this.ipAddress = ipAddress;
        this.action = action;
        this.entity = entity;
        this.entityId = entityId;
        this.userEmail = userEmail;
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getAction() {
        return action;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getEntity() {
        return entity;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

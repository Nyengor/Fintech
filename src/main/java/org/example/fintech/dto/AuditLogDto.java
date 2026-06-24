package org.example.fintech.dto;

import java.time.LocalDateTime;

public class AuditLogDto {
    private String status;
    private String entity;
    private String action;
    private String ipAddress;
    private LocalDateTime timestamp;

    public AuditLogDto(String status, String entity, String action, String ipAddress, LocalDateTime timestamp) {
        this.status = status;
        this.entity = entity;
        this.action = action;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

package org.example.fintech.service;

import org.example.fintech.dto.AuditLogDto;
import org.example.fintech.entity.AuditEntity;
import org.example.fintech.event.AuditEvent;
import org.example.fintech.repository.AuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditService {
    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional
    public void saveAuditLog(AuditEvent event) {
        AuditEntity log = new AuditEntity();
        log.setUserEmail(event.getUserEmail());
        log.setStatus(event.getStatus());
        log.setAction(event.getAction());
        log.setEntity(event.getEntity());
        log.setEntityId(event.getEntityId());
        log.setIpAddress(event.getIpAddress());
        log.setTimestamp(event.getTimestamp());

        auditRepository.save(log);

    }

//    Getting the saved logs
    public List<AuditLogDto> getLogs(String userEmail) {
        List<AuditEntity> logs = auditRepository.findByUserEmailOrderByTimestampDesc(userEmail);

        return logs.stream().map(log -> new AuditLogDto(
                log.getStatus(),
                log.getAction(),
                log.getEntity(),
                log.getIpAddress(),
                log.getTimestamp()
        ))
                .collect(Collectors.toList());
    }
}

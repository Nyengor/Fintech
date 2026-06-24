package org.example.fintech.repository;

import org.example.fintech.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity,Long> {

    List<AuditEntity> findByUserEmailOrderByTimestampDesc(String userEmail);
}

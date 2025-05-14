package com.doubleo.patientservice.domain.guardian.repository;

import com.doubleo.patientservice.domain.guardian.domain.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    List<Guardian> findAllByPatientId(long id);
    List<Guardian> findByPatientIdAndTenantId(Long patientId, String tenantId);
}


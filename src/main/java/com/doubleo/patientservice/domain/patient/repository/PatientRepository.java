package com.doubleo.patientservice.domain.patient.repository;

import com.doubleo.patientservice.domain.patient.domain.Patient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Object> findPatientByPatientCodeAndTenantId(String patientCode, String tenantId);

    List<Patient> findAllByTenantId(String tenantId);
}

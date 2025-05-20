package com.doubleo.patientservice.domain.patient.repository;

import com.doubleo.patientservice.domain.patient.domain.Patient;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findPatientByPatientCodeAndTenantId(String patientCode, String tenantId);

    Page<Patient> findAllByTenantId(String tenantId, Pageable pageable);

    Optional<Patient> findByTenantIdAndPatientNameAndPatientRegNo(String tenantId, String patientName, String patientRegNo);
}

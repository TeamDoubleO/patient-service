package com.doubleo.patientservice.domain.patient.repository;

import com.doubleo.patientservice.domain.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {}

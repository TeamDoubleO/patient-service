package com.doubleo.patientservice.domain.patient.repository;

import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.domain.PatientArea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientAreaRepository extends JpaRepository<PatientArea, Long> {
    List<PatientArea> findAllByPatient(Patient patient);
}

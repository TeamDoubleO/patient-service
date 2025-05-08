package com.doubleo.patientservice.domain.patient.service;

import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.dto.PatientInfoResponse;
import com.doubleo.patientservice.domain.patient.dto.PatientListInfoResponse;

public interface PatientService {

    PatientInfoResponse getPatientInfo(Long patientId);

    PatientListInfoResponse getPatientListInfo(Patient patient);
}

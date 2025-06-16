package com.doubleo.patientservice.domain.patient.service;

import com.doubleo.patientservice.domain.patient.dto.response.PatientInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    PatientInfoResponse getPatientInfo(Long patientId);

    Page<PatientInfoResponse> getPatientListInfo(String keyword, Pageable pageable);
}

package com.doubleo.patientservice.domain.patient.service;

import com.doubleo.patientservice.domain.patient.dto.request.PatientCodeCheckRequest;
import com.doubleo.patientservice.domain.patient.dto.response.PatientInfoResponse;

public interface PatientService {

    PatientInfoResponse getPatientInfo(Long patientId);

    //    List<PatientListInfoResponse> getPatientListInfo();

    void checkPatientCode(PatientCodeCheckRequest request);
}

package com.doubleo.patientservice.domain.patient.service;

import com.doubleo.patientservice.domain.patient.dto.request.PatientCodeCheckRequest;
import com.doubleo.patientservice.domain.patient.dto.response.PatientInfoResponse;
import com.doubleo.patientservice.domain.patient.dto.response.PatientNameResponse;

public interface PatientService {

    PatientInfoResponse getPatientInfo(Long patientId);

    //    List<PatientListInfoResponse> getPatientListInfo();
    PatientNameResponse getPatientName(Long patientId);

    void checkPatientCode(PatientCodeCheckRequest request);
}

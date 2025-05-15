package com.doubleo.patientservice.domain.patient.service;

import com.doubleo.patientservice.domain.patient.dto.request.PatientCodeCheckRequest;
import com.doubleo.patientservice.domain.patient.dto.response.PatientInfoResponse;
import java.util.List;

public interface PatientService {

    PatientInfoResponse getPatientInfo(Long patientId);

    List<PatientInfoResponse> getPatientListInfo();

    void checkPatientCode(PatientCodeCheckRequest request);
}

package com.doubleo.patientservice.domain.patient.service;

import com.doubleo.hospitalservice.domain.area.grpc.AreaResponse;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.dto.PatientInfoResponse;
import com.doubleo.patientservice.domain.patient.dto.PatientListInfoResponse;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import com.doubleo.patientservice.grpc.AreaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final AreaClient areaClient;

    @Override
    @Transactional(readOnly = true)
    public PatientInfoResponse getPatientInfo(Long patientId) {
        Patient patient = validatePatient(patientId);
        AreaResponse area = areaClient.getAreaById(patient.getAdmissionArea());
        return PatientInfoResponse.of(patient, area);
    }

    @Override
    public PatientListInfoResponse getPatientListInfo(Patient patient) {
        return null;
    }

    private Patient validatePatient(Long patientId) {
        return patientRepository
                .findById(patientId)
                .orElseThrow(() -> new CommonException(PatientErrorCode.PATIENT_NOT_FOUND));
    }
}

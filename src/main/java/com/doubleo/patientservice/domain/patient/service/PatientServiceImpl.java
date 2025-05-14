package com.doubleo.patientservice.domain.patient.service;

import com.doubleo.hospitalservice.domain.area.grpc.server.AreaResponse;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.dto.request.PatientCodeCheckRequest;
import com.doubleo.patientservice.domain.patient.dto.response.PatientInfoResponse;
import com.doubleo.patientservice.domain.patient.dto.response.PatientNameResponse;
import com.doubleo.patientservice.domain.patient.grpc.client.AreaClient;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import com.doubleo.patientservice.global.util.TenantValidator;
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
    private final TenantValidator tenantValidator;

    // admin
    @Override
    @Transactional(readOnly = true)
    public PatientInfoResponse getPatientInfo(Long patientId) {
        Patient patient = validatePatient(patientId);
        AreaResponse area = getPatientArea(patient);
        return PatientInfoResponse.from(patient, area);
    }

    @Override
    public PatientNameResponse getPatientName(Long patientId) {
        Patient patient = validatePatient(patientId);
        return PatientNameResponse.from(patient);
    }

    //    @Override
    //    public List<PatientListInfoResponse> getPatientListInfo() {
    //        String tenantId = TenantContextHolder.getTenantId();
    //        return patientRepository.findAllByTenantId(tenantId)
    //                .stream()
    //                .map(PatientListInfoResponse::of)
    //                .toList();
    //    }

    // mobile
    @Override
    @Transactional(readOnly = true)
    public void checkPatientCode(PatientCodeCheckRequest request) {
        isPatientWithCodeExists(request.patientCode());
    }

    // util
    private void isPatientWithCodeExists(String patientCode) {
        patientRepository
                .findPatientByPatientCodeAndTenantId(patientCode, tenantValidator.getTenantId())
                .orElseThrow(() -> new CommonException(PatientErrorCode.PATIENT_NOT_FOUND));
    }

    private Patient validatePatient(Long patientId) {
        Patient patient =
                patientRepository
                        .findById(patientId)
                        .orElseThrow(() -> new CommonException(PatientErrorCode.PATIENT_NOT_FOUND));
        tenantValidator.validateTenant(patient.getTenantId());
        return patient;
    }

    private AreaResponse getPatientArea(Patient patient) {
        AreaResponse area = areaClient.getAreaById(patient.getId());
        tenantValidator.validateTenant(area.getTenantId());
        return area;
    }
}

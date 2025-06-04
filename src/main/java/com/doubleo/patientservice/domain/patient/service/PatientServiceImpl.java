package com.doubleo.patientservice.domain.patient.service;

import com.doubleo.hospitalservice.domain.area.grpc.server.AreaResponse;
import com.doubleo.patientservice.domain.guardian.repository.GuardianRepository;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.domain.PatientArea;
import com.doubleo.patientservice.domain.patient.dto.AreaInfo;
import com.doubleo.patientservice.domain.patient.dto.response.PatientInfoResponse;
import com.doubleo.patientservice.domain.patient.grpc.client.AreaClient;
import com.doubleo.patientservice.domain.patient.repository.PatientAreaRepository;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import com.doubleo.patientservice.global.util.TenantValidator;
import com.doubleo.tenantcontext.TenantContextHolder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final GuardianRepository guardianRepository;
    private final PatientAreaRepository patientAreaRepository;
    private final AreaClient areaClient;
    private final TenantValidator tenantValidator;

    // admin
    @Override
    @Transactional(readOnly = true)
    public PatientInfoResponse getPatientInfo(Long patientId) {
        Patient patient = validatePatient(patientId);
        return getPatientInfoResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientInfoResponse> getPatientListInfo(Pageable pageable) {
        String tenantId = TenantContextHolder.getTenantId();

        return patientRepository
                .findAllByTenantId(tenantId, pageable)
                .map(this::getPatientInfoResponse);
    }

    private PatientInfoResponse getPatientInfoResponse(Patient patient) {
        List<AreaResponse> areas = getPatientAreas(patient);
        Long guardianCount = guardianRepository.countByPatientId(patient.getId());
        List<AreaInfo> areaInfos = areas.stream()
                .map(areaResponse -> {
                    try {
                        String areaFullName = areaClient
                                .getAreaFullNameByCode(areaResponse.getTenantId(), areaResponse.getAreaCode())
                                .getAreaFullName();
                        return new AreaInfo(areaResponse.getAreaCode(), areaFullName);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(areaInfo -> areaInfo != null)
                .toList();
        return PatientInfoResponse.from(patient, areaInfos, guardianCount);
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

    private List<AreaResponse> getPatientAreas(Patient patient) {
        List<PatientArea> areas = patientAreaRepository.findAllByPatient(patient);
        return areas.stream().map(area -> areaClient.getAreaById(area.getAreaId())).toList();
    }
}

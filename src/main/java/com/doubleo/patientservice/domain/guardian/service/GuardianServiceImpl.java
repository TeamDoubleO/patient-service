package com.doubleo.patientservice.domain.guardian.service;

import com.doubleo.patientservice.domain.guardian.domain.Guardian;
import com.doubleo.patientservice.domain.guardian.dto.response.GuardianInfoResponse;
import com.doubleo.patientservice.domain.guardian.repository.GuardianRepository;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import com.doubleo.patientservice.global.util.TenantValidator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {
    private final GuardianRepository guardianRepository;
    private final PatientRepository patientRepository;
    private final TenantValidator tenantValidator;

    @Override
    public List<GuardianInfoResponse> getGuardiansByPatientCode(String patientCode) {

        String tenantId = tenantValidator.getTenantId();

        Patient patient =
                (Patient)
                        patientRepository
                                .findPatientByPatientCodeAndTenantId(patientCode, tenantId)
                                .orElseThrow(
                                        () ->
                                                new CommonException(
                                                        PatientErrorCode.PATIENT_NOT_FOUND));

        List<Guardian> guardians =
                guardianRepository.findByPatientIdAndTenantId(patient.getId(), tenantId);

        return guardianRepository.findByPatientIdAndTenantId(patient.getId(), tenantId).stream()
                .map(GuardianInfoResponse::from)
                .collect(Collectors.toList());
    }
}

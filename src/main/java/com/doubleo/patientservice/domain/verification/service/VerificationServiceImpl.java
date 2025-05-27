package com.doubleo.patientservice.domain.verification.service;

import com.doubleo.memberservice.domain.member.grpc.server.MemberResponse;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.domain.verification.grpc.client.MemberClient;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final PatientRepository patientRepository;
    private final MemberClient memberClient;

    @Override
    public void verifyPatient(String tenantId, Long memberId) {
        MemberResponse member = memberClient.getMemberById(memberId);
        if (patientRepository
                .findByTenantIdAndNameAndRegNo(
                        tenantId, member.getMemberName(), member.getMemberRegNo())
                .isEmpty()) {
            throw new CommonException(PatientErrorCode.PATIENT_NOT_FOUND);
        }
    }

    @Override
    public void verifyPatientCode(String tenantId, Long memberId, String patientCode) {
        if (patientRepository
                .findPatientByPatientCodeAndTenantId(patientCode, tenantId)
                .isEmpty()) {
            throw new CommonException(PatientErrorCode.PATIENT_NOT_FOUND);
        }
    }
}

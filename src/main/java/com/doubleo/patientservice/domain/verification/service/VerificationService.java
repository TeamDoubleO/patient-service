package com.doubleo.patientservice.domain.verification.service;

public interface VerificationService {
    void verifyPatient(String tenantId, Long memberId);

    void verifyPatientCode(String tenantId, Long memberId, String patientCode);
}

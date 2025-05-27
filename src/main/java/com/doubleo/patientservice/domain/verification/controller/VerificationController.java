package com.doubleo.patientservice.domain.verification.controller;

import com.doubleo.patientservice.domain.verification.dto.request.GuardianVerificationRequest;
import com.doubleo.patientservice.domain.verification.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class VerificationController {

    private final VerificationService verificationService;

    @Operation(summary = "환자 검증", description = "현재 회원 정보와 일치하는 환자 유무 확인.")
    @PostMapping("/verify/patient")
    public ResponseEntity<Void> PatientVerify(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestHeader("X-Tenant-Id") String tenantId) {
        verificationService.verifyPatient(tenantId, memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "보호자가 환자 코드 검증", description = "환자 코드가 일치하는 환자 유무 확인.")
    @PostMapping("/verify/guardian")
    public ResponseEntity<Void> GuardianVerify(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestBody GuardianVerificationRequest request) {
        verificationService.verifyPatientCode(tenantId, memberId, request.patientCode());
        return ResponseEntity.ok().build();
    }
}

package com.doubleo.patientservice.domain.patient.controller;

import com.doubleo.patientservice.domain.patient.dto.PatientInfoResponse;
import com.doubleo.patientservice.domain.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "6-1. Admin API", description = "환자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    //    @GetMapping("/{patientId}")
    //    public PatientInfoResponse patientInfoGet(
    //            @RequestHeader("hospitalId") Long hospitalId,
    //            @RequestHeader("X-Admin-Id") Long adminId,
    //            @PathVariable("patientId") Long patientId) {
    //        return patientService.getPatientInfo(patientId);
    //    }

    @Operation(summary = "환자 정보 조회", description = "환자 ID로 환자 정보를 조회하고, 입원 구역 이름을 포함하여 반환합니다.")
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientInfoResponse> getPatientInfo(
            @RequestHeader("hospitalId") Long hospitalId, @PathVariable Long patientId) {
        PatientInfoResponse dto = patientService.getPatientInfo(patientId);
        return ResponseEntity.ok(dto);
    }

    //    @GetMapping
    //    public PatientListInfoResponse patientListInfoGet(
    //            @RequestHeader("hospitalId") Long hospitalId,
    //            @RequestHeader("X-Admin-Id") Long adminId,
    //            @PathVariable("patientId") Long patientId) {
    //        return patientService.getPatientListInfo(hospitalId);
    //    }
}

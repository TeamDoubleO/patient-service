package com.doubleo.patientservice.domain.patient.controller;

import com.doubleo.patientservice.domain.patient.dto.response.PatientInfoResponse;
import com.doubleo.patientservice.domain.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "A-6. Admin API", description = "환자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientAdminController {

    private final PatientService patientService;

    @Operation(summary = "환자 정보 조회", description = "환자 정보를 반환합니다.")
    @GetMapping("/paged")
    public Page<PatientInfoResponse> patientListInfoGet(@RequestParam(required = false) String keyword, Pageable pageable) {
        return patientService.getPatientListInfo(keyword, pageable);
    }

    @Operation(summary = "환자 정보 조회", description = "환자 정보를 반환합니다.")
    @GetMapping("/{patientId}")
    public PatientInfoResponse patientInfoGet(@PathVariable Long patientId) {
        return patientService.getPatientInfo(patientId);
    }

}

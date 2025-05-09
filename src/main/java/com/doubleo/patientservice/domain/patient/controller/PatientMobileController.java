package com.doubleo.patientservice.domain.patient.controller;

import com.doubleo.patientservice.domain.patient.dto.request.PatientCodeCheckRequest;
import com.doubleo.patientservice.domain.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "M-6. Admin API", description = "환자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientMobileController {

    private final PatientService patientService;

    @Operation(summary = "환자 코드 검증", description = "입력받은 환자 코드와 일치하는 유효한 환자 데이터 유무를 확인합니다.")
    @PostMapping("/code")
    public ResponseEntity<Void> patientCodeCheck(
            @Valid @RequestBody PatientCodeCheckRequest request) {
        patientService.checkPatientCode(request);
        return ResponseEntity.ok().build();
    }
}

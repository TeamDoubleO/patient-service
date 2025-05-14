package com.doubleo.patientservice.domain.guardian.controller;

import com.doubleo.patientservice.domain.guardian.dto.response.GuardianInfoResponse;
import com.doubleo.patientservice.domain.guardian.service.GuardianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "A-6. Patient Guardian API", description = "환자 보호자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class GuardianAdminController {
    private final GuardianService guardianService;

    @Operation(summary = "환자 별 보호자 조회", description = "관리자가 자신의 병원의 환자의 보호자를 조회")
    @PostMapping("/{patientCode}/guardians")
    public List<GuardianInfoResponse> getGuardiansByPatientId(@PathVariable String patientCode) {
        return guardianService.getGuardiansByPatientCode(patientCode);
    }
}

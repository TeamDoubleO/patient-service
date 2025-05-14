package com.doubleo.patientservice.domain.guardian.controller;

import com.doubleo.patientservice.domain.guardian.dto.response.GuardianInfoResponse;
import com.doubleo.patientservice.domain.guardian.service.GuardianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "M-6. Patient Guardian API", description = "환자 보호자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class GuardianMobileController {
    private final GuardianService guardianService;

    @Operation(summary = "환자 별 보호자 조회", description = "모바일에서 환자가 자신의 보호자를 조회")
    @PostMapping("/guardians")
    public List<GuardianInfoResponse> getGuardiansByPatientId(
            @RequestHeader String patientCode, @RequestHeader("X-Hospital-Id") Long hospitalId) {
        return guardianService.getGuardiansByPatientCode(patientCode);
    }
}

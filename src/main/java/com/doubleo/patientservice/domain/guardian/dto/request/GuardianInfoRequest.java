package com.doubleo.patientservice.domain.guardian.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record GuardianInfoRequest(
        @Schema(description = "환자 식별코드", example = "62372710") @NotBlank String patientCode) {}

package com.doubleo.patientservice.domain.verification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record GuardianVerificationRequest(
        @Schema(description = "환자 코드", example = "A0101") @NotNull String patientCode) {}

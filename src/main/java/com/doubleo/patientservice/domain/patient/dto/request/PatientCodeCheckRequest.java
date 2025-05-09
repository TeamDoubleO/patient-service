package com.doubleo.patientservice.domain.patient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PatientCodeCheckRequest(
        @Schema(description = "환자 식별코드", example = "62372710") String patientCode) {}

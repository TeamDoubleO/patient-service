package com.doubleo.patientservice.domain.patient.dto.response;

import com.doubleo.patientservice.domain.patient.domain.Patient;
import io.swagger.v3.oas.annotations.media.Schema;

public record PatientNameResponse(
        @Schema(description = "환자 ID", example = "1") Long patientId,
        @Schema(description = "환자 이름", example = "홍길동") String name) {

    public static PatientNameResponse from(Patient patient) {
        return new PatientNameResponse(patient.getId(), patient.getName());
    }
}

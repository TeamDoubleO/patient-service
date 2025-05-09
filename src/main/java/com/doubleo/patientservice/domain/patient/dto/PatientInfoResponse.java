package com.doubleo.patientservice.domain.patient.dto;

import com.doubleo.hospitalservice.domain.area.grpc.AreaResponse;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.domain.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record PatientInfoResponse(
        @Schema(description = "환자 ID", example = "1") Long patientId,
        @Schema(description = "환자 등록 번호", example = "A1234567") String patientCode,
        @Schema(description = "환자 이름", example = "홍길동") String name,
        @Schema(description = "환자 성별", example = "MALE") Sex sex,
        @Schema(description = "등록 일시", example = "2025-05-05T14:30:00") LocalDateTime registeredOn,
        @Schema(description = "입원 구역 ID", example = "100") Long admissionAreaId,
        @Schema(description = "입원 구역 이름", example = "1층 중환자실") String admissionAreaName) {

    public static PatientInfoResponse of(Patient patient, AreaResponse area) {
        return new PatientInfoResponse(
                patient.getId(),
                patient.getPatientCode(),
                patient.getName(),
                patient.getSex(),
                patient.getRegisteredOn(),
                patient.getAdmissionArea(),
                area.getAreaName());
    }
}

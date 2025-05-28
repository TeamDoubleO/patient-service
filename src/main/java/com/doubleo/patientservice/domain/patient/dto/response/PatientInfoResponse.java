package com.doubleo.patientservice.domain.patient.dto.response;

import com.doubleo.hospitalservice.domain.area.grpc.server.AreaResponse;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.domain.Sex;
import com.doubleo.patientservice.domain.patient.dto.AreaInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record PatientInfoResponse(
        @Schema(description = "환자 ID", example = "1") Long patientId,
        @Schema(description = "환자 등록 번호", example = "A1234567") String patientCode,
        @Schema(description = "환자 이름", example = "홍길동") String name,
        @Schema(description = "환자 성별", example = "MALE") Sex sex,
        @Schema(description = "입원 구역 목록") List<AreaInfo> areas,
        @Schema(description = "보호자 수", example = "2") Long guardianCount) {

    public static PatientInfoResponse from(
            Patient patient, List<AreaResponse> areas, Long guardianCount) {
        return new PatientInfoResponse(
                patient.getId(),
                patient.getPatientCode(),
                patient.getName(),
                patient.getSex(),
                areas.stream()
                        .map(
                                areaResponse ->
                                        new AreaInfo(
                                                areaResponse.getAreaCode(),
                                                areaResponse.getAreaName()))
                        .toList(),
                guardianCount);
    }
}

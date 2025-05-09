package com.doubleo.patientservice.domain.patient.dto.response;

import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.domain.Sex;
import java.time.LocalDateTime;

public record PatientGrpcResponse(
        Long patientId,
        String TenantId,
        String patientCode,
        String name,
        Sex sex,
        LocalDateTime registeredOn,
        Long admissionAreaId) {

    public static PatientGrpcResponse fromEntity(Patient patient) {
        return new PatientGrpcResponse(
                patient.getId(),
                patient.getTenantId(),
                patient.getPatientCode(),
                patient.getName(),
                patient.getSex(),
                patient.getRegisteredOn(),
                patient.getAdmissionArea());
    }
}

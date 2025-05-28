package com.doubleo.patientservice.domain.patient.domain;

import com.doubleo.patientservice.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patient_area")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientArea extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_area_id")
    private Long id;

    @JoinColumn(name = "patient_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    @Column(name = "area_id", nullable = false)
    private Long areaId;

    @Builder(access = AccessLevel.PRIVATE)
    private PatientArea(String tenantId, Patient patient, Long areaId) {
        this.tenantId = tenantId;
        this.patient = patient;
        this.areaId = areaId;
    }

    public static PatientArea createPatientARea(String tenantId, Patient patient, Long areaId) {
        return PatientArea.builder().tenantId(tenantId).patient(patient).areaId(areaId).build();
    }
}

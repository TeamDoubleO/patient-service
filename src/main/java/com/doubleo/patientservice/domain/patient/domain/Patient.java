package com.doubleo.patientservice.domain.patient.domain;

import com.doubleo.patientservice.domain.model.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "patient",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "UniqueTenantId",
                    columnNames = {"tenant_id", "patient_id"})
        })
public class Patient extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false)
    private Long id;

    @Column(name = "patient_code", nullable = false)
    private String patientCode;

    @Column(name = "patient_name", nullable = false)
    private String name;

    @Column(name = "patient_reg_no", nullable = false)
    private String regNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "patient_gender", nullable = false)
    private Gender gender;

    @Column(name = "patient_registered_on", nullable = false)
    private LocalDateTime registeredOn;

    @Column(name = "patient_admission_area", nullable = false)
    private Long admissionArea;
}

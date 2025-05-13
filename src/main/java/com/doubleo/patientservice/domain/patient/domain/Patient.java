package com.doubleo.patientservice.domain.patient.domain;

import com.doubleo.patientservice.domain.guardian.domain.Guardian;
import com.doubleo.patientservice.domain.model.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "patient",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "UniqueTenantId",
                    columnNames = {"tenant_id", "patient_id"})
        })
public class Patient extends BaseEntity {

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
    @Column(name = "patient_sex", nullable = false)
    private Sex sex;

    @Column(name = "patient_registered_on", nullable = false)
    private LocalDateTime registeredOn;

    @Column(name = "patient_admission_area", nullable = false)
    private Long admissionArea;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Guardian> guardians = new ArrayList<>();
}

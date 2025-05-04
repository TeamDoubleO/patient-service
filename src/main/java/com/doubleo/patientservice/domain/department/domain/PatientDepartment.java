package com.doubleo.patientservice.domain.department.domain;

import com.doubleo.patientservice.domain.model.BaseTimeEntity;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(
        name = "patient_department",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "UniqueTenantId",
                    columnNames = {"tenant_id", "patient_department_id"})
        })
public class PatientDepartment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_department_id", nullable = false)
    private Long id;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @JoinColumn(name = "patient_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;
}

package com.doubleo.patientservice.domain.patient.domain;

import com.doubleo.patientservice.domain.model.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
public class Patient extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false)
    private Long id;
}

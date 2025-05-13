package com.doubleo.patientservice.domain.guardian.domain;

import com.doubleo.patientservice.domain.model.BaseEntity;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guardian")
public class Guardian extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guardian_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    @Column(name = "guardian_name", nullable = false)
    private String guardianName;

    @Column(name = "guardian_contact", nullable = false)
    private String guardianContact;
}

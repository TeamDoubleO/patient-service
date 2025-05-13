package com.doubleo.patientservice.domain.guardian.repository;

import com.doubleo.patientservice.domain.guardian.domain.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {}

package com.doubleo.patientservice.domain.guardian.service;

import com.doubleo.patientservice.domain.guardian.dto.response.GuardianInfoResponse;
import java.util.List;

public interface GuardianService {
    List<GuardianInfoResponse> getGuardiansByPatientId(Long patientId);
}

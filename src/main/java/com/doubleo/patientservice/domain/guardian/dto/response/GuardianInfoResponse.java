package com.doubleo.patientservice.domain.guardian.dto.response;

import com.doubleo.patientservice.domain.guardian.domain.Guardian;

public record GuardianInfoResponse(Long guardianId, String guardianName, String guardianContact) {
    public static GuardianInfoResponse from(Guardian guardian) {
        return new GuardianInfoResponse(
                guardian.getId(), guardian.getGuardianName(), guardian.getGuardianContact());
    }
}

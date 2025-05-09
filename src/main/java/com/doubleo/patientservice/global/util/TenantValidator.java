package com.doubleo.patientservice.global.util;

import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.TenantErrorCode;
import com.doubleo.tenantcontext.TenantContextHolder;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TenantValidator {

    public String getTenantId() {
        return Optional.ofNullable(TenantContextHolder.getTenantId())
                .orElseThrow(() -> new CommonException(TenantErrorCode.TENANT_ID_NOT_FOUND));
    }

    public void validateTenant(String tenantId) {
        String currentTenantId = getTenantId();
        if (!tenantId.equals(currentTenantId)) {
            throw new CommonException(TenantErrorCode.INVALID_TENANT_ID);
        }
    }
}

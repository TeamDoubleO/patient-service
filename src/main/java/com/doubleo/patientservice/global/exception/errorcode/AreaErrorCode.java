package com.doubleo.patientservice.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AreaErrorCode implements BaseErrorCode {
    AREA_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 구역입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}

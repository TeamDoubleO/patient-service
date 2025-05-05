package com.doubleo.patientservice.domain.patient.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sex {
    MALE("MALE"),
    FEMALE("FEMALE");
    private final String sex;
}

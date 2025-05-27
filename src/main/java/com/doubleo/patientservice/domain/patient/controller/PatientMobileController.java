package com.doubleo.patientservice.domain.patient.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "M-6. Admin API", description = "환자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientMobileController {}

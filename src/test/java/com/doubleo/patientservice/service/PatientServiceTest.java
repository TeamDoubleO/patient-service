package com.doubleo.patientservice.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.doubleo.hospitalservice.domain.area.grpc.server.AreaResponse;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.domain.Sex;
import com.doubleo.patientservice.domain.patient.dto.request.PatientCodeCheckRequest;
import com.doubleo.patientservice.domain.patient.dto.response.PatientInfoResponse;
import com.doubleo.patientservice.domain.patient.grpc.client.AreaClient;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.domain.patient.service.PatientServiceImpl;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import com.doubleo.patientservice.global.util.TenantValidator;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock PatientRepository patientRepository;
    @Mock TenantValidator tenantValidator;
    @Mock AreaClient areaClient;

    @InjectMocks private PatientServiceImpl patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient =
                new Patient(
                        1L,
                        "A1234567",
                        "정선우",
                        "001111-1234567",
                        Sex.MALE,
                        LocalDateTime.of(2025, 5, 9, 16, 30),
                        1L);
        ReflectionTestUtils.setField(patient, "id", 1L);
        ReflectionTestUtils.setField(patient, "tenantId", "SEO25NE01");
    }

    @Nested
    class getPatientInfo {

        @Test
        void 환자정보_조회하면_정상적으로_반환된다() {
            // given
            AreaResponse area =
                    AreaResponse.newBuilder()
                            .setAreaId(1L)
                            .setAreaCode("101")
                            .setAreaName("병동1")
                            .build();
            given(patientRepository.findById(1L)).willReturn(Optional.of(patient));
            given(areaClient.getAreaById(1L)).willReturn(area);

            // when
            PatientInfoResponse response = patientService.getPatientInfo(1L);

            // then
            assertThat(response.patientId()).isEqualTo(patient.getId());
            assertThat(response.patientCode()).isEqualTo(patient.getPatientCode());
            assertThat(response.name()).isEqualTo(patient.getName());
            assertThat(response.sex()).isEqualTo(patient.getSex());
            assertThat(response.registeredOn()).isEqualTo(patient.getRegisteredOn());
        }
    }

    @Nested
    class checkPatientCode {

        @Test
        void 환자_코드가_일치하지_않으면_오류가_발생한다() {
            // given
            String patientCode = "Wrong";

            PatientCodeCheckRequest request = new PatientCodeCheckRequest(patientCode);

            // when & then
            assertThatThrownBy(() -> patientService.checkPatientCode(request))
                    .isInstanceOf(CommonException.class)
                    .hasMessageContaining(PatientErrorCode.PATIENT_NOT_FOUND.getMessage());
        }
    }
}

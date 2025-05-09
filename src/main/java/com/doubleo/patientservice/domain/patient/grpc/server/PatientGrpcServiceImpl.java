package com.doubleo.patientservice.domain.patient.grpc.server;

import com.doubleo.patientservice.domain.patient.dto.response.PatientGrpcResponse;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import com.doubleo.patientservice.global.util.TimeUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PatientGrpcServiceImpl extends PatientServiceGrpc.PatientServiceImplBase {

    private final PatientRepository patientRepository;

    public PatientGrpcServiceImpl(PatientRepository patientRepository, TimeUtil timeUtil) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void getPatient(
            PatientRequest request, StreamObserver<PatientResponse> responseObserver) {
        patientRepository
                .findById(request.getPatientId())
                .map(PatientGrpcResponse::fromEntity)
                .ifPresentOrElse(
                        res -> {
                            PatientResponse resp =
                                    PatientResponse.newBuilder()
                                            .setPatientId(res.patientId())
                                            .setTenantId(res.TenantId())
                                            .setPatientCode(res.patientCode())
                                            .setName(res.name())
                                            .setSex(Sex.valueOf(res.sex().getSex()))
                                            .setRegisteredOn(
                                                    TimeUtil.fromLocalDateTime(res.registeredOn()))
                                            .setAdmissionArea(res.admissionAreaId())
                                            .build();
                            responseObserver.onNext(resp);
                            responseObserver.onCompleted();
                        },
                        () -> {
                            responseObserver.onError(
                                    new CommonException(PatientErrorCode.PATIENT_NOT_FOUND));
                        });
    }
}

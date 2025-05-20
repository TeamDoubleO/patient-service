package com.doubleo.patientservice.domain.patient.grpc.server;

import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.dto.response.PatientGrpcResponse;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import com.doubleo.patientservice.global.util.TimeUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

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

    @Override
    public void getPatientByNameAndRegNo(
            PatientByNameAndRegNoRequest request, StreamObserver<PatientResponse> responseObserver
    ) {
        Optional<Patient> patient = patientRepository.findByTenantIdAndPatientNameAndPatientRegNo(request.getTenantId(), request.getPatientName(), request.getPatientRegNo());
        if (patient.isPresent()) {
            Patient p = patient.get();
            responseObserver.onNext(PatientResponse.newBuilder()
                            .setPatientId(p.getId()).setTenantId(p.getTenantId()).setPatientCode(p.getPatientCode()).setName(p.getName()).setSex(Sex.valueOf(p.getSex().getSex())).setRegisteredOn(TimeUtil.fromLocalDateTime(p.getRegisteredOn())).setAdmissionArea(p.getAdmissionArea()).build());
            responseObserver.onCompleted();
        }
        else {
            responseObserver.onError(
                    new CommonException(PatientErrorCode.PATIENT_NOT_FOUND)
            );
        }
    }
}

package com.doubleo.patientservice.domain.patient.grpc.server;

import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.domain.PatientArea;
import com.doubleo.patientservice.domain.patient.repository.PatientAreaRepository;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import com.doubleo.patientservice.global.util.TimeUtil;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PatientGrpcServiceImpl extends PatientServiceGrpc.PatientServiceImplBase {

    private final PatientRepository patientRepository;
    private final PatientAreaRepository patientAreaRepository;

    public PatientGrpcServiceImpl(
            PatientRepository patientRepository, PatientAreaRepository patientAreaRepository) {
        this.patientRepository = patientRepository;
        this.patientAreaRepository = patientAreaRepository;
    }

    @Override
    public void getPatient(
            PatientRequest request, StreamObserver<PatientResponse> responseObserver) {
        Optional<Patient> patient = patientRepository.findById(request.getPatientId());
        createPatientResponse(responseObserver, patient);
    }

    @Override
    public void getPatientByNameAndRegNo(
            PatientByNameAndRegNoRequest request,
            StreamObserver<PatientResponse> responseObserver) {
        Optional<Patient> patient =
                patientRepository.findByTenantIdAndNameAndRegNo(
                        request.getTenantId(), request.getPatientName(), request.getPatientRegNo());
        createPatientResponse(responseObserver, patient);
    }

    @Override
    public void getPatientByCode(
            PatientByCode request, StreamObserver<PatientResponse> responseObserver) {
        Optional<Patient> patient =
                patientRepository.findByTenantIdAndPatientCode(
                        request.getTenantId(), request.getPatientCode());
        createPatientResponse(responseObserver, patient);
    }

    private void createPatientResponse(
            StreamObserver<PatientResponse> responseObserver, Optional<Patient> patient) {
        if (patient.isPresent()) {
            Patient p = patient.get();
            responseObserver.onNext(
                    PatientResponse.newBuilder()
                            .setPatientId(p.getId())
                            .setTenantId(p.getTenantId())
                            .setPatientCode(p.getPatientCode())
                            .setName(p.getName())
                            .setSex(Sex.valueOf(p.getSex().getSex()))
                            .setRegisteredOn(TimeUtil.fromLocalDateTime(p.getRegisteredOn()))
                            .addAllAreas(
                                    patientAreaRepository.findAllByPatient(p).stream()
                                            .map(PatientArea::getAreaId)
                                            .toList())
                            .build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new CommonException(PatientErrorCode.PATIENT_NOT_FOUND));
        }
    }
}

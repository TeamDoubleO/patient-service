package com.doubleo.patientservice.domain.guardian.grpc.server;

import com.doubleo.patientservice.domain.guardian.domain.Guardian;
import com.doubleo.patientservice.domain.guardian.repository.GuardianRepository;
import com.doubleo.patientservice.domain.patient.domain.Patient;
import com.doubleo.patientservice.domain.patient.repository.PatientRepository;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.GrpcExceptionUtil;
import com.doubleo.patientservice.global.exception.errorcode.GuardianErrorCode;
import com.doubleo.patientservice.global.exception.errorcode.PatientErrorCode;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GuardianGrpcServiceImpl extends GuardianServiceGrpc.GuardianServiceImplBase {

    private final PatientRepository patientRepository;
    private final GuardianRepository guardianRepository;

    @PersistenceContext private EntityManager entityManager;

    public GuardianGrpcServiceImpl(
            GuardianRepository guardianRepository, PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
        this.guardianRepository = guardianRepository;
    }

    @Override
    public void getGuardian(
            GuardianRequest request, StreamObserver<GuardianResponse> responseObserver) {
        try {
            GuardianResponse response = getGuardianResponseById(request.getGuardianId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (CommonException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getPatientGuardianList(
            PatientGuardianListRequest request,
            StreamObserver<PatientGuardianListResponse> responseObserver) {
        try {
            List<Guardian> guardians =
                    guardianRepository.findAllByPatientId(request.getPatientId());
            List<GuardianResponse> guardianResponses =
                    guardians.stream().map(this::toGuardianResponse).toList();
            PatientGuardianListResponse response =
                    PatientGuardianListResponse.newBuilder()
                            .addAllGuardians(guardianResponses)
                            .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (CommonException e) {
            responseObserver.onError(GrpcExceptionUtil.toStatusRuntimeException(e.getErrorCode()));
        }
    }

    @Override
    public void createGuardian(
            CreateGuardianRequest request,
            StreamObserver<CreateGuardianResponse> responseObserver) {
        try {
            long patientId = request.getPatientId();

            if (!patientRepository.existsById(patientId)) {
                throw new CommonException(PatientErrorCode.PATIENT_NOT_FOUND);
            }

            Patient patient = entityManager.getReference(Patient.class, patientId);

            Guardian guardian =
                    Guardian.createGuardian(
                            request.getTenantId(),
                            patient,
                            request.getGuardianName(),
                            request.getGuardianContact());
            Guardian saved = guardianRepository.save(guardian);

            CreateGuardianResponse response =
                    CreateGuardianResponse.newBuilder().setGuardianId(saved.getId()).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (CommonException e) {
            responseObserver.onError(GrpcExceptionUtil.toStatusRuntimeException(e.getErrorCode()));
        }
    }

    private GuardianResponse getGuardianResponseById(long id) {
        return guardianRepository
                .findById(id)
                .map(this::toGuardianResponse)
                .orElseThrow(() -> new CommonException(GuardianErrorCode.GUARDIAN_NOT_FOUND));
    }

    private GuardianResponse toGuardianResponse(Guardian guardian) {
        return GuardianResponse.newBuilder()
                .setGuardianId(guardian.getId())
                .setTenantId(guardian.getTenantId())
                .setPatientId(guardian.getPatient().getId())
                .setGuardianName(guardian.getGuardianName())
                .setGuardianContact(guardian.getGuardianContact())
                .build();
    }
}

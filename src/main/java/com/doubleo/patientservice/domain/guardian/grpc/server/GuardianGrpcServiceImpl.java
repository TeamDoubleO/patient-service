package com.doubleo.patientservice.domain.guardian.grpc.server;

import com.doubleo.patientservice.domain.guardian.domain.Guardian;
import com.doubleo.patientservice.domain.guardian.repository.GuardianRepository;
import com.doubleo.patientservice.global.exception.CommonException;
import com.doubleo.patientservice.global.exception.errorcode.GuardianErrorCode;
import io.grpc.stub.StreamObserver;
import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GuardianGrpcServiceImpl extends GuardianServiceGrpc.GuardianServiceImplBase {

    private final GuardianRepository guardianRepository;

    public GuardianGrpcServiceImpl(GuardianRepository guardianRepository) {
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
                    guardians.stream()
                            .map(guardian -> getGuardianResponseById(guardian.getId()))
                            .toList();
            PatientGuardianListResponse response =
                    PatientGuardianListResponse.newBuilder()
                            .addAllGuardians(guardianResponses)
                            .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (CommonException e) {
            responseObserver.onError(e);
        }
    }

    private GuardianResponse getGuardianResponseById(long id) {
        return guardianRepository
                .findById(id)
                .map(
                        res ->
                                GuardianResponse.newBuilder()
                                        .setGuardianId(res.getId())
                                        .setTenantId(res.getTenantId())
                                        .setPatientId(res.getPatient().getId())
                                        .setGuardianName(res.getGuardianName())
                                        .setGuardianContact(res.getGuardianContact())
                                        .build())
                .orElseThrow(() -> new CommonException(GuardianErrorCode.GUARDIAN_NOT_FOUND));
    }
}

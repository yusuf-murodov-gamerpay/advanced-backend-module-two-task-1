package com.advancedbackend.module_two.service;

import com.advancedbackend.module_two.stubs.pingpong.PingPongRequest;
import com.advancedbackend.module_two.stubs.pingpong.PingPongResponse;
import com.advancedbackend.module_two.stubs.pingpong.PingPongServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {
    private static final Logger log = LogManager.getLogger(PingPongServiceImpl.class);

    @Override
    public void getPong(PingPongRequest request, StreamObserver<PingPongResponse> responseObserver) {
        log.info("Received request: {}", request);
        var responseBuilder = PingPongResponse.newBuilder();
        if ("ping".equalsIgnoreCase(request.getMessage())) {
            responseBuilder.setMessage("pong");
        } else {
           responseBuilder.setMessage("please write ping to get pong!");
        }
        var response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

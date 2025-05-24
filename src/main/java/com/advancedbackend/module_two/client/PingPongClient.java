package com.advancedbackend.module_two.client;

import com.advancedbackend.module_two.stubs.pingpong.PingPongRequest;
import com.advancedbackend.module_two.stubs.pingpong.PingPongResponse;
import com.advancedbackend.module_two.stubs.pingpong.PingPongServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PingPongClient {
    private static final Logger log = LogManager.getLogger(PingPongClient.class);
    private final PingPongServiceGrpc.PingPongServiceBlockingStub pingPongServiceBlockingStub;

    public PingPongClient(Channel channel) {
        pingPongServiceBlockingStub = PingPongServiceGrpc.newBlockingStub(channel);
    }

    public PingPongResponse getPingPong(String message) {
        var request = PingPongRequest.newBuilder()
                .setMessage(message)
                .build();

        return pingPongServiceBlockingStub.getPong(request);
    }

    public static void main(String[] args) {
        log.info("Creating a channel and calling the ping pong client");
        var channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext().build();
        var client = new PingPongClient(channel);

        System.out.print("Enter your ping message: ");
        var scanner = new Scanner(System.in);
        var input = scanner.nextLine();
        var response = client.getPingPong(input);
        log.info("Client received message: {}", response.getMessage());

        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.fatal("Order service client shutdown failed", e);
        }
    }
}

package com.advancedbackend.module_two.service;

import com.advancedbackend.module_two.stubs.chat.ChatPayload;
import com.advancedbackend.module_two.stubs.chat.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    private static final Logger log = LogManager.getLogger(ChatServiceImpl.class);

    @Override
    public StreamObserver<ChatPayload> chat(StreamObserver<ChatPayload> responseObserver) {
        var messageQueue = new LinkedBlockingQueue<ChatPayload>();
        new Thread(() -> {
            var scanner = new Scanner(System.in);
            try {
                while (true) {
                    ChatPayload received = messageQueue.take();
                    log.info("You have new msg: {}", received.getMessage());

                    System.out.print("Send your message: ");
                    var input = scanner.nextLine();

                    var response = ChatPayload.newBuilder().setMessage(input).build();
                    responseObserver.onNext(response);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Input thread interrupted", e);
            } catch (Exception e) {
                log.error("Error in input thread", e);
                responseObserver.onError(e);
            }
        }, "UserInputThread").start();

        return new StreamObserver<>() {
            @Override
            public void onNext(ChatPayload chatPayload) {
                messageQueue.offer(chatPayload);
            }

            @Override
            public void onError(Throwable throwable) {
                log.warn("Error while receiving msg from client", throwable);
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Client completed the chat");
                responseObserver.onCompleted();
            }
        };
    }
}

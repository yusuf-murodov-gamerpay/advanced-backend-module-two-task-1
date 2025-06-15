package com.advancedbackend.module_two.client;

import com.advancedbackend.module_two.stubs.chat.ChatPayload;
import com.advancedbackend.module_two.stubs.chat.ChatServiceGrpc;
import com.google.common.base.Strings;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class ChatClient {
    private static final Logger log = LogManager.getLogger(ChatClient.class);
    private final ChatServiceGrpc.ChatServiceStub chatServiceStub;

    public ChatClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public ChatClient(ManagedChannelBuilder<?> channelBuilder) {
        Channel channel = channelBuilder.build();
        this.chatServiceStub = ChatServiceGrpc.newStub(channel);
    }

    public void startChatting() {
        var requestObserver = chatServiceStub.chat(new StreamObserver<>() {
            @Override
            public void onNext(ChatPayload chatPayload) {
                log.info("You have new msg: {}", chatPayload.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                log.warn("Error while receiving msg from the server", throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Stream completed");
            }
        });

        log.info("Hey there!");
        try {
            System.out.print("Send your message: ");
            Scanner scanner = new Scanner(System.in);
            String messageContent;
            while (true) {
                messageContent = scanner.nextLine();
                if (messageContent.equals("exit")) {
                    break;
                }
                ChatPayload message = ChatPayload.newBuilder()
                        .setMessage(messageContent)
                        .build();
                requestObserver.onNext(message);
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        } finally {
            requestObserver.onCompleted();
        }
    }

    public static void main(String[] args) {
        var chatClient = new ChatClient("localhost", 8081);
        chatClient.startChatting();
    }
}

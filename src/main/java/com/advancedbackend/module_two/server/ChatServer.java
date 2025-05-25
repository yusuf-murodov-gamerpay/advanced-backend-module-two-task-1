package com.advancedbackend.module_two.server;

import com.advancedbackend.module_two.service.ChatServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ChatServer {
    private static final Logger log = LogManager.getLogger(ChatServer.class);
    private final Server server;
    private final int port;

    public ChatServer(int port) {
        this(ServerBuilder.forPort(port), port);
    }

    public ChatServer(ServerBuilder<?> serverBuilder, int port) {
        this.server = serverBuilder
                .addService(new ChatServiceImpl())
                .build();
        this.port = port;
    }

    public void start() throws IOException, InterruptedException {
        server.start();
        log.info("Chat server started, listening on {}", port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Clean server shutdown in case JVM was shutdown");
            try {
                ChatServer.this.stopServer();
            } catch (InterruptedException e) {
                log.fatal("Server shutdown failed", e);
            }
        }));

        blockUntilShutdown();
    }

    public void stopServer() throws InterruptedException {
        if(server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if(server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        var chatServer = new ChatServer(8081);
        chatServer.start();
    }
}

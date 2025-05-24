package com.advancedbackend.module_two.server;

import com.advancedbackend.module_two.service.PingPongServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class PingPongServer {
    private static final Logger log = LogManager.getLogger(PingPongServer.class);
    private Server server;

    public void startServer() {
        final int port = 8080;
        try {
            server = ServerBuilder.forPort(port)
                    .addService(new PingPongServiceImpl())
                    .build()
                    .start();
            log.info("Server started at port 8080");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("Clean server shutdown in case JVM was shutdown");
                try {
                    PingPongServer.this.stopServer();
                } catch (InterruptedException e) {
                    log.fatal("Server shutdown failed", e);
                }
            }));
        } catch (IOException e) {
            log.fatal("Server did not start", e);
        }
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

    public static void main(String[] args) throws InterruptedException {
        PingPongServer orderServer = new PingPongServer();
        orderServer.startServer();
        orderServer.blockUntilShutdown();
    }
}

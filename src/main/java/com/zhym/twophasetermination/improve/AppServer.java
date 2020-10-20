package com.zhym.twophasetermination.improve;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 23:57
 */
public class AppServer extends Thread{
    private int port;

    private static final int DEFAULT_PORT = 12722;

    private volatile boolean start = true;

    private List<ClientHandler> clientHandlers = new ArrayList<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private ServerSocket socket;
    public AppServer() {
        this(DEFAULT_PORT);
    }

    public AppServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            this.socket = new ServerSocket(port);
            while (start) {
                Socket client = this.socket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                executor.submit(clientHandler);
                this.clientHandlers.add(clientHandler);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            this.dispose();
        }
    }

    private void dispose() {
        clientHandlers.stream().forEach(ClientHandler::stop);
        this.executor.shutdown();
    }


    public void shutdown() throws IOException {
        this.start = false;
        this.socket.close();
        this.interrupt();
    }
}

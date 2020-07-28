package com.zhoujian.socket.server;

import com.zhoujian.socket.ExecutorServicePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Socket服务端示例
 * @author zhoujian
 */
public class SocketServer {

    private static int PORT = 8088;
    private ServerSocket serverSocket;
    private static Logger logger = LoggerFactory.getLogger(SocketServer.class);

    public static void main(String[] args) {
        try {
            new SocketServer().startUp(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startUp(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        logger.info("Socket Server is online, listening at port {}", PORT);
        while (true){
            Socket socket = serverSocket.accept();
            logger.info("socket port is {} connect successful", socket.getPort());
            ExecutorServicePool.executorService.execute(new AnswerThread(socket));
        }
    }

    static class AnswerThread implements Runnable {

        private Socket socket;

        public AnswerThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            String content = null;
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                while ((content = bufferedReader.readLine()) != null){
                    logger.info("form client: {}", content);
                    socket.getOutputStream().write(content.getBytes());
                    socket.getOutputStream().write("\n".getBytes());
                    socket.getOutputStream().flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

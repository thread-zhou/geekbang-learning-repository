package com.zhoujian.socket.client;

import com.zhoujian.socket.ExecutorServicePool;
import com.zhoujian.socket.server.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Socket客户端示例
 * @author zhoujian
 */
public class SocketClient {

    private static String HOST = "127.0.0.1";
    private static int PORT = 8088;
    private static Logger logger = LoggerFactory.getLogger(SocketClient.class);

    public static void main(String[] args) throws IOException {
        Socket client = new Socket(HOST, PORT);
        ExecutorServicePool.executorService.execute(new ReceiveThread(client));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        while (true){
            String msg = reader.readLine();
            writer.write(msg);
            writer.write("\n");
            writer.flush();
        }
    }

    static class ReceiveThread implements Runnable{

        private Socket socket;

        public ReceiveThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            String receive = null;
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                while ((receive = bufferedReader.readLine()) != null){
                    logger.info("from server: {}", receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

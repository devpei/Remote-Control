package com.dev2future.socket;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketClient implements Runnable {

    public static Map<String, Socket> sockets = new HashMap<>();

    private String serverAddress;

    private Integer port;

    private String mark;

    public SocketClient() {
    }

    public SocketClient(String serverAddress, Integer port, String mark) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.mark = mark;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(getServerAddress(), getPort());
            if (socket.isConnected()) {
                Log.d("SocketSuccess", "-------------------------------------->我连接上了");
                addSocket(getMark(), socket);
            }
            Log.d("SocketSize", "-------------------------------------->" + sockets.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addSocket(String mark, Socket socket) {
        sockets.put(mark, socket);
    }

    public static void removeSocket(String mark) {
        sockets.remove(mark);
    }

    public static Socket getSocket(String mark) {
        return sockets.get(mark);
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
//    private volatile static SocketClient socketClient;
//
//    private SocketClient() {
//    }
//
//    public static SocketClient getInstance() {
//        if (socketClient == null) {
//            synchronized (SocketClient.class) {
//                if (socketClient == null) {
//                    socketClient = new SocketClient();
//                }
//            }
//        }
//        return socketClient;
//    }
}

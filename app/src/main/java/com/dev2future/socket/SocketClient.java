package com.dev2future.socket;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.dev2future.model.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
                Log.d("SocketSuccess", "==>连接上了");
                addSocket(getMark(), socket);
                //开始监听消息
                messageListener(socket);
                //初次连接告诉服务端客户端类型
                //RemoteControl代表是移动控制客户端
                Map<String, Object> content = new HashMap<>();
                content.put("clientType", "RemoteControl");
                //指明消息类型是直接告诉服务端
                Message message = new Message("0.0.0.0", content);
                MessageHandleImpl sendMessage = new MessageHandleImpl("Operate");
                sendMessage.singleSend(message);
                MessageHandleImpl.addImpl("Operate", sendMessage);
            }
            Log.d("SocketSize", "==>SocketSize" + sockets.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void messageListener(final Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = socket.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while (socket.isConnected() && !(socket.isClosed())) {
                        int read = inputStream.read();
                        // 数据格式JSON，流分析以#为起点，$为终点。
                        if (read != -1) {
                            if (read == 35) {
                                // 说明是某一段数据的起点#
                                baos.reset();
                                continue;
                            } else if (read == 36) {
                                Log.d("Message", "<==收到消息：" + new String(baos.toByteArray(), "UTF-8"));
                                // 说明是某一段数据的终点$
                                MessageHandleImpl.getImpl("Operate").messageAnalysis((Message) JSON.parseObject(baos.toByteArray(), Message.class, Feature.IgnoreNotMatch));
                            }
                            baos.write(read);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "Operate:Listener").start();
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
}

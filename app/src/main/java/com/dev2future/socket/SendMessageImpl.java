package com.dev2future.socket;

import com.alibaba.fastjson.JSON;
import com.dev2future.model.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SendMessageImpl implements SendMessage {

    public static Map<String, SendMessageImpl> impls = new HashMap<>();

    private String mark;

    private Message message;

    private boolean send = true;

    public SendMessageImpl() {
    }

    public SendMessageImpl(String mark, Message message) {
        this.mark = mark;
        this.message = message;
    }

    @Override
    public void singleSend(String mark, Message message) throws IOException {
        Socket socket = SocketClient.getSocket(mark);
        socket.getOutputStream().write(("#" + JSON.toJSONString(message) + "$").getBytes("UTF-8"));
    }

    @Override
    public void run() {
        while (send) {
            try {
                singleSend(getMark(), getMessage());
                Thread.sleep(100);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addImpl(String mark, SendMessageImpl impl) {
        impls.put(mark, impl);
    }

    public static void removeImpl(String mark) {
        impls.remove(mark);
    }

    public static SendMessageImpl getImpl(String mark) {
        return impls.get(mark);
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}

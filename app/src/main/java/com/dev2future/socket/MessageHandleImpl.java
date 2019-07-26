package com.dev2future.socket;

import com.alibaba.fastjson.JSON;
import com.dev2future.model.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHandleImpl implements MessageHandle {

    public static Map<String, MessageHandle> impls = new HashMap<>();

    private String mark;

    private Message message;

    private boolean send = true;

    public MessageHandleImpl() {
    }

    public MessageHandleImpl(String mark) {
        this.mark = mark;
    }

    @Override
    public void singleSend(Message message) throws IOException {
        Socket socket = SocketClient.getSocket(mark);
        socket.getOutputStream().write(("#" + JSON.toJSONString(message) + "$").getBytes("UTF-8"));
    }

    @Override
    public void continueSend(Message message) {
        while (send) {
            try {
                singleSend(message);
                Thread.sleep(100);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void messageAnalysis(Message message) {
        Object devicesList = message.getContent().get("DevicesList");
        if (devicesList != null) {
            List<String> devices = (List) devicesList;

        }
    }

    @Override
    public void stopSend() {
        send = false;
    }

    public static void addImpl(String mark, MessageHandle impl) {
        impls.put(mark, impl);
    }

    public static void removeImpl(String mark) {
        impls.remove(mark);
    }

    public static MessageHandle getImpl(String mark) {
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

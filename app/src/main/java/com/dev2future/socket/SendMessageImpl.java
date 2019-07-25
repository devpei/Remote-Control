package com.dev2future.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SendMessageImpl implements SendMessage {

    public static Map<String, SendMessageImpl> impls = new HashMap<>();

    private String mark;

    private String content;

    private boolean send = true;

    public SendMessageImpl() {
    }

    public SendMessageImpl(String mark, String content) {
        this.mark = mark;
        this.content = content;
    }

    @Override
    public void singleSend(String mark, String content) throws IOException {
        Socket socket = SocketClient.getSocket(mark);
        socket.getOutputStream().write(content.getBytes("UTF-8"));
    }

    @Override
    public void run() {
        while (send) {
            try {
                singleSend(getMark(), getContent());
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}

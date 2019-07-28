package com.dev2future.socket;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dev2future.model.Message;

import java.io.IOException;
import java.net.Socket;

public class SingleSend implements Runnable {

    private Socket socket;

    private Message message;

    public SingleSend(){}

    public SingleSend(Socket socket,Message message){
        this.socket = socket;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            socket.getOutputStream().write(("#" + JSON.toJSONString(message) + "$").getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MessageError","==>消息发送异常："+e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}

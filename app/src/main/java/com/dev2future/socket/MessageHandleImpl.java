package com.dev2future.socket;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dev2future.model.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHandleImpl implements MessageHandle {

    public static Map<String, MessageHandleImpl> impls = new HashMap<>();

    //socket表示
    private String mark;

    private Message message;

    //决定是否发送
    private boolean send = true;

    //决定发送的类型
    //单次发送single
    //持续发送continue
    private String sendType;

    public MessageHandleImpl() {
    }

    public MessageHandleImpl(String mark) {
        this.mark = mark;
    }

    @Override
    public void singleSend(Message message){
        Socket socket = SocketClient.getSocket(getMark());
        try {
            socket.getOutputStream().write(("#" + JSON.toJSONString(message) + "$").getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MessageError","==>消息发送异常："+e.getMessage());
        }
    }

    @Override
    public void continueSend(Message message) {
        while (send) {
            try {
                singleSend(message);
                Thread.sleep(100);
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

    @Override
    public void run() {
        if("single".equals(getSendType())){
            //表示单一发送
            singleSend(getMessage());
        }else if("continue".equals(getSendType())){
            //表示持续发送
            continueSend(getMessage());
        }else {
            Log.d("SendTip","<==发送类型无法判断");
        }
    }

    public static void addImpl(String mark, MessageHandleImpl impl) {
        impls.put(mark, impl);
    }

    public static void removeImpl(String mark) {
        impls.remove(mark);
    }

    public static MessageHandleImpl getImpl(String mark) {
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

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }
}

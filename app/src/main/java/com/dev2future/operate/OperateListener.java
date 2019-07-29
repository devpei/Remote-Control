package com.dev2future.operate;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dev2future.R;
import com.dev2future.model.Message;
import com.dev2future.socket.ContinueSend;
import com.dev2future.socket.MessageHandleImpl;
import com.dev2future.socket.SocketClient;

import java.util.HashMap;
import java.util.Map;

public class OperateListener implements View.OnTouchListener, OperateBehavior {

    private ContinueSend send;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.btnUp:
                operateUp(event.getAction());
                break;
            case R.id.btnBottom:
                operateBottom(event.getAction());
                break;
        }
        return false;
    }

    public void operateUp(int action) {
        sendContent(action, "1");
    }

    public void operateBottom(int action) {
        sendContent(action, "2");
    }

    private void sendContent(int action, String content) {
        if (action == MotionEvent.ACTION_DOWN) {
            //按下发送指令
            Map<String, Object> msgContent = new HashMap();
            msgContent.put("command", content);
            //MessageHandleImpl sendMessage = new MessageHandleImpl("Operate");
            //MessageHandleImpl operate = (MessageHandleImpl) MessageHandleImpl.getImpl("Operate");
            Message message = new Message(SocketClient.getSocket("Operate").getInetAddress().getHostAddress(), "192.168.1.196", msgContent);
            //sendMessage.setMessage(message);
            //sendMessage.setSendType("continue");
            //启动线程发送消息
            //new Thread(sendMessage).start();
            send = new ContinueSend(SocketClient.getSocket("Operate"),message);
            new Thread(send).start();
        } else if (action == MotionEvent.ACTION_UP) {
            //离开停止指令
            //MessageHandleImpl.getImpl("Operate").stopSend();
            //MessageHandleImpl.removeImpl("Operate");
            send.setSend(false);
        }
    }
}

package com.dev2future.operate;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dev2future.R;
import com.dev2future.socket.SendMessageImpl;
import com.dev2future.socket.SocketClient;

import java.net.Socket;

public class OperateListener implements View.OnTouchListener, OperateBehavior {

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
            SendMessageImpl operate = new SendMessageImpl("Operate", content);
            operate.addImpl("Operate:Send", operate);
            new Thread(operate, "Operate:Send").start();
            Log.d("MessageImpl", "------------->发送实例数量" + SendMessageImpl.impls.size());
        } else if (action == MotionEvent.ACTION_UP) {
            //离开停止指令
            SendMessageImpl.getImpl("Operate:Send").setSend(false);
            SendMessageImpl.removeImpl("Operate:Send");
            Log.d("MessageImpl", "------------->离开实例数量" + SendMessageImpl.impls.size());
        }
    }
}

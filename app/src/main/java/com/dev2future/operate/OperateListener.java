package com.dev2future.operate;

import android.view.MotionEvent;
import android.view.View;

import com.dev2future.R;

public class OperateListener implements View.OnTouchListener, OperateBehavior {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.btnUp:
                operateUp(event.getAction());
                break;
            case R.id.btnBottom:
                break;
        }
        return false;
    }

    public void operateUp(int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            //按下发送前进指令
        } else if (action == MotionEvent.ACTION_UP) {
            //离开停止发送指令
        }
    }

    public void operateBottom(int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            //按下发送后退指令
        } else if (action == MotionEvent.ACTION_UP) {
            //离开停止发送指令
        }
    }
}

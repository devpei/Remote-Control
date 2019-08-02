package com.dev2future;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dev2future.operate.OperateListener;
import com.dev2future.socket.SocketClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //服务器地址输入框
    private EditText serverAddressControl;

    //服务器端口输入框
    private EditText serverPortControl;

    //连接按钮
    private Button connectServer;

    //设备选择
    private Spinner selectDevices;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //更新在线设备
                case 9:
                    if (com.dev2future.model.Message.selectIps.size() > 0) {
                        selectDevices.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, com.dev2future.model.Message.selectIps));
                    }
                    break;
                //连接成功，控件禁用
                case 8:
                    serverAddressControl.setEnabled(false);
                    serverPortControl.setEnabled(false);
                    connectServer.setEnabled(false);
                    break;
                //断开连接，解除控件禁用状态
                case 7:
                    serverAddressControl.setEnabled(true);
                    serverPortControl.setEnabled(true);
                    connectServer.setEnabled(true);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        com.dev2future.model.Message.handler = handler;

        connectServer = findViewById(R.id.btnConnect);
        connectServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverAddressControl = findViewById(R.id.serverAddress);
                serverAddressControl.setText("192.168.1.202");
                String serverAddress = serverAddressControl.getText().toString();
                if (serverAddress == null || "".equals(serverAddress)) {
                    toast("请输入服务器IP地址");
                    return;
                }
                serverPortControl = findViewById(R.id.serverPort);
                serverPortControl.setText("7800");
                Integer serverPort = Integer.valueOf(serverPortControl.getText().toString());
                if (serverPort < 1 || serverPort == null) {
                    toast("请输入服务器端口");
                    return;
                }
                new Thread(new SocketClient(serverAddress, serverPort, "Operate")).start();
            }
        });

        findViewById(R.id.btnUp).setOnTouchListener(new OperateListener());
        findViewById(R.id.btnBottom).setOnTouchListener(new OperateListener());

        //获取选择控件对象
        selectDevices = findViewById(R.id.selectDevices);
        //选中事件
        selectDevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                com.dev2future.model.Message.selectIp = com.dev2future.model.Message.selectIps.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 通知提示
     *
     * @param msg
     */
    public void toast(String msg) {
        Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}

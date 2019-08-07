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
<<<<<<< HEAD
                EditText serverAddressControl = findViewById(R.id.serverAddress);
                serverAddressControl.setText("192.168.1.4");
=======
                serverAddressControl = findViewById(R.id.serverAddress);
                serverAddressControl.setText("192.168.1.202");
>>>>>>> b3b23a7bea436455cb82d743e6977876738a00c7
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
<<<<<<< HEAD
        Spinner selectDevices = findViewById(R.id.selectDevices);
        List<String> ips = new ArrayList<>();
        ips.add("请选择设备");
        ips.add("192.168.1.3");
        selectDevices.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, ips));

//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(5000);
//                        Log.d("#####", JSON.toJSONString(Message.selectIps));
//                        if (Message.selectIps.size() > 0) {
//                            selectDevices.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, Message.selectIps));
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        //点击监听
//        selectDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (Message.selectIps.size() > 0) {
//                    ((Spinner) view).setAdapter(new ArrayAdapter<>(view.getContext(), 0, Message.selectIps));
//                } else {
//                    toast("暂无设备接入");
//                }
//            }
//        });
        //选中事件
//        selectDevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("Select", "==>选中" + Message.selectIp);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Log.d("Select", "==>什么都没选");
//            }
//        });
=======
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
>>>>>>> b3b23a7bea436455cb82d743e6977876738a00c7
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

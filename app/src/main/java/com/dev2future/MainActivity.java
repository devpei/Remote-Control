package com.dev2future;

import android.os.Bundle;
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

import com.dev2future.model.Message;
import com.dev2future.operate.OperateListener;
import com.dev2future.socket.SocketClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectServer = findViewById(R.id.btnConnect);
        connectServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText serverAddressControl = findViewById(R.id.serverAddress);
                serverAddressControl.setText("192.168.1.202");
                String serverAddress = serverAddressControl.getText().toString();
                if (serverAddress == null || "".equals(serverAddress)) {
                    toast("请输入服务器IP地址");
                    return;
                }
                EditText serverPortControl = findViewById(R.id.serverPort);
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
//        Spinner selectDevices = findViewById(R.id.selectDevices);
//        selectDevices.setPrompt("选择设备");
//        List<String> ips = new ArrayList<>();
//        ips.add("192.168.1.202");
//        ips.add("192.168.1.203");
//        selectDevices.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, ips));

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
//                Log.d("Select", "-------------->开始选择");
//                String s = Message.selectIps.get(position);
//                Message.selectIp = s;
//                Log.d("Select", "-------------->选中" + Message.selectIp);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
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

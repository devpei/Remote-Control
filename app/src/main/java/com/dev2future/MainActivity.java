package com.dev2future;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev2future.operate.OperateListener;
import com.dev2future.socket.SocketClient;

import java.io.IOException;
import java.net.Socket;

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

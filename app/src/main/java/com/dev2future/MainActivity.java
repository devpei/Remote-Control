package com.dev2future;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private static String address;

    private static Integer port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectServer = findViewById(R.id.btnConnect);
        connectServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText serverAddressControl = findViewById(R.id.serverAddress);
                String serverAddress = serverAddressControl.getText().toString();
                if (serverAddress == null || "".equals(serverAddress)) {
                    toast("请输入服务器IP地址");
                    return;
                }
                MainActivity.address = serverAddress;
                EditText serverPortControl = findViewById(R.id.serverPort);
                Integer serverPort = Integer.valueOf(serverPortControl.getText().toString());
                if (serverPort < 1 || serverPort == null) {
                    toast("请输入服务器端口");
                    return;
                }
                MainActivity.port = serverPort;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("------------->进来了");
                        try {
                            Socket socket = new Socket(MainActivity.address, MainActivity.port);
                            Log.d("Socket", "--------->建立连接了");
                            socket.getOutputStream().write(new String("hello").getBytes());
                            Log.d("Socket", "--------->发送消息了");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("SocketError", "<---------" + e.getMessage());
                        }
                    }
                }).start();
            }
        });

        Button btnUp = findViewById(R.id.btnUp);

        //btnUp.setOnTouchListener();
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

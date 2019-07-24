package com.dev2future.socket;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient implements Runnable {

    private Socket socket;

    private InputStream inputStream;

    private OutputStream outputStream;

    private byte[] buff = new byte[2048];

    public SocketClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();

            while (true) {

                if (inputStream.read(buff) != -1) {
                    Log.d("Message", new String(buff, "UTF-8"));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

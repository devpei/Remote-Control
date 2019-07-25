package com.dev2future.socket;

import com.dev2future.model.Message;

import java.io.IOException;

public interface SendMessage extends Runnable {

    void singleSend(String mark, Message message) throws IOException;
}

package com.dev2future.socket;

import java.io.IOException;

public interface SendMessage extends Runnable {

    void singleSend(String mark, String content) throws IOException;
}

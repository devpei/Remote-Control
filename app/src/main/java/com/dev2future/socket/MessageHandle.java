package com.dev2future.socket;

import com.dev2future.model.Message;

import java.io.IOException;

public interface MessageHandle extends Runnable {

    /**
     * 单一发送
     *
     * @throws IOException
     */
    void singleSend(Message message);

    /**
     * 持续发送
     */
    void continueSend(Message message);

    /**
     * 停止发送消息
     */
    void stopSend();

    /**
     * 消息分析
     *
     * @param message
     */
    void messageAnalysis(Message message);
}

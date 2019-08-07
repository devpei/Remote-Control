package com.dev2future.model;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Message {

    public static List<String> selectIps = new ArrayList<>();

    public static Handler handler = null;

    /**
     * 选中的地址
     */
    public static String selectIp;

    /**
     * 源地址
     */
    private String sourcesAddress;

    /**
     * 目的地址
     */
    private String destinationAddress;

    /**
     * 内容
     */
    private Map<String, Object> content;

    public Message() {
    }

    public Message(String destinationAddress, Map<String, Object> content) {
        this.destinationAddress = destinationAddress;
        this.content = content;
    }

    public Message(String sourcesAddress, String destinationAddress, Map<String, Object> content) {
        this.sourcesAddress = sourcesAddress;
        this.destinationAddress = destinationAddress;
        this.content = content;
    }

    public String getSourcesAddress() {
        return sourcesAddress;
    }

    public void setSourcesAddress(String sourcesAddress) {
        this.sourcesAddress = sourcesAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }
}

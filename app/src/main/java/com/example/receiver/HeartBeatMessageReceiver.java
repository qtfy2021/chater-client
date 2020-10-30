package com.example.receiver;

import android.app.Service;

import com.alibaba.fastjson.JSONObject;

public class HeartBeatMessageReceiver implements MessageReceiver {
    @Override
    public void parseMessage(JSONObject json, Service service) {

    }
}

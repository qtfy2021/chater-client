package com.example.receiver;

import android.app.Service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


//接受信息后进行处理
public interface MessageReceiver {
    void parseMessage(JSONObject json, Service service) throws JSONException;
}

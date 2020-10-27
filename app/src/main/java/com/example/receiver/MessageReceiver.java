package com.example.receiver;

import android.app.Service;

import org.json.JSONException;
import org.json.JSONObject;

//接受信息后进行处理
public interface MessageReceiver {
    void parseMessage(JSONObject json, Service service) throws JSONException;
}

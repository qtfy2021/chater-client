package com.example.receiver;

import android.app.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.model.Entity.DownloadMessage;

import org.greenrobot.eventbus.EventBus;

public class DownloadMessageReceiver implements MessageReceiver {
    @Override
    public void parseMessage(JSONObject json, Service service) throws JSONException {

        DownloadMessage downloadMessage = JSON.toJavaObject(json, DownloadMessage.class);

        EventBus.getDefault().post(downloadMessage);
    };
}

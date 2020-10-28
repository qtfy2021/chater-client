package com.example.receiver;

import android.app.Service;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.model.Entity.DownloadMessage;
import com.example.until.UserInfoUtil;

public class DownloadMessageReceiver implements MessageReceiver {
    @Override
    public void parseMessage(JSONObject json, Service service) throws JSONException {

        DownloadMessage downloadMessage = JSON.toJavaObject(json, DownloadMessage.class);




        Intent intent = new Intent();
        //intent.setAction("com.chatter.chatterHub.messageContent");
        //intent.putExtra("picture", picture);
        //service.sendBroadcast(intent);
    }
}

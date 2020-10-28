package com.example.receiver;

import android.app.Service;
import android.content.Intent;

import com.example.until.UserInfoUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class DownloadMessageReceiver implements MessageReceiver {
    @Override
    public void parseMessage(JSONObject json, Service service) throws JSONException {
        String curUserId = UserInfoUtil.getUserId();
        Intent intent = new Intent();
        intent.setAction("com.chater.chaterHub.messagecontent");


    }
}

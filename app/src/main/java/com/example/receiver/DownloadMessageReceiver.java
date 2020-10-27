package com.example.receiver;

import android.app.Service;

import org.json.JSONException;
import org.json.JSONObject;

public class DownloadMessageReceiver implements MessageReceiver {
    @Override
    public void parseMessage(JSONObject json, Service service) throws JSONException {

    }
}

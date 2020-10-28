package com.example.net.listener;

import android.util.Log;

import com.example.service.WebSocketClientService;
import com.example.receiver.ReceiverMessageParser;

public class OnReceiverMessageListener implements WsListener{
    WebSocketClientService webSocketClientService;
    public OnReceiverMessageListener(WebSocketClientService webSocketClientService) {
        Log.d("OnReceiverMessage监听器", "初始化");
        this.webSocketClientService = webSocketClientService;
    }

    @Override
    public void receiverMessage(String message) {
        Log.d("OnReceiverMessage监听器", "接收到信息");
        Log.d("OnReceiverMessage监听器", message);
        ReceiverMessageParser parser = new ReceiverMessageParser();
        parser.parser(message, webSocketClientService);
    }
}

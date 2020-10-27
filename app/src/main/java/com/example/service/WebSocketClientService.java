package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.net.MyWebSocketClient;
import com.example.net.listener.OnReceiverMessageListener;
import com.example.until.LocalThreadPools;

public class WebSocketClientService extends Service {
    private WebSocketClientBinder mBinder= new WebSocketClientBinder();

    public class WebSocketClientBinder extends Binder {
        // 在Binder中定义一个自定义的接口用于数据交互
         // 这里直接把当前的服务传回给宿主
        public WebSocketClientService getService() {
            return WebSocketClientService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate(){
            initWebsocketClient();
    }

    public void initWebsocketClient(){
        //listening onMessage()
        Log.d("初始化ws", "初始化开始");
        Log.d("初始化ws", "初始化中...");

        LocalThreadPools.getInstance(this).execute(
                new Runnable() {
                    @Override
                    public void run() {
                        MyWebSocketClient.getInstance().setWsListener(new OnReceiverMessageListener(WebSocketClientService.this));
                    }
                }
        );

        Log.d("初始化ws", "初始化完成");
    }



    public void sendMessage(String json){
        MyWebSocketClient.getInstance().send(json);

}

}

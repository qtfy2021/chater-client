package com.example.net;

import android.content.res.Resources;
import android.os.Looper;
import android.util.Log;

import com.example.chater.BuildConfig;
import com.example.net.Enum.WsStatus;
import com.example.net.listener.WsListener;
import com.example.until.AppContext;
import com.example.until.LocalThreadPools;
import com.example.until.UrlUtil;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MyWebSocketClient extends WebSocketClient implements Client{
            private static MyWebSocketClient mInstance;
            private WsListener wsListener;


            /*Websocket config*/
            private static final int FRAME_QUEUE_SIZE = 5;
            private static final int CONNECT_TIMEOUT = 10000;
            private static final  String DEF_TEST_URL = UrlUtil.WEB_SOCKET_URL.CHAT_URL;
            private static final String DEF_RELEASE_URL = UrlUtil.WEB_SOCKET_URL.CHAT_URL;
            private static final  String DEF_URL = BuildConfig.DEBUG ? DEF_TEST_URL : DEF_RELEASE_URL;
            private WsStatus status;

            public static MyWebSocketClient getInstance(){
                if (mInstance == null){
                    synchronized (MyWebSocketClient.class) {
                        if(mInstance == null) {
                            mInstance = new MyWebSocketClient( URI.create(DEF_URL), CONNECT_TIMEOUT);
                            mInstance.startConnect();
                        }
                    }
                }
                Log.i("获取ws客户端", "获取");
                return mInstance;
    }

    public MyWebSocketClient(URI uri){
        super(uri, new Draft_6455());
    }

    public MyWebSocketClient(URI uri, int timeout){
        super(uri, new Draft_6455(), null, timeout);
    }

    public void startConnect() {
                try{
                    Log.d("获取ws客户端", "（startConnect）正在连接..");
                    mInstance.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
        }
    }

    private void closeConnect() {
        try {
            if (null != mInstance) {
                mInstance.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mInstance = null;
        }
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {

                setStatus(WsStatus.CONNECT_SUCCESS);

                        //开始心跳连接
                        Log.i("websocket", "准备开始心跳连接+心跳thread");

                ConnetionAliveTest.getInstance().startHeartBeatTest();




                Log.d("MyWebSocketClient信息内容", String.valueOf(Thread.currentThread().getId()));
                Log.i("MyWebSocketClient正在", "onOpen()");
    }

    @Override
    public void onMessage(String message) {
                if(wsListener != null){
                    wsListener.receiverMessage(message);

                }
        Log.d("MyWebSocketClient信息内容","内容"+ message);

        Log.d("MyWebSocketClient", "onMessage()");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("MyWebSocketClient", "onClose()");
    }

    @Override
    public void onError(Exception ex) {

        Log.e("MyWebSocketClient", "onError()");
        Log.e("error:", ex.toString());
    }

    public void setStatus(WsStatus status) {
        this.status = status;
    }

    public void setWsListener(WsListener wsListener){
                this.wsListener = wsListener;
    }
}

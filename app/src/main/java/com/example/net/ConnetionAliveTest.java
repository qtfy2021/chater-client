package com.example.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class ConnetionAliveTest {
    private static ConnetionAliveTest mInstance;
    private static final long HRART_BEAT_RATE = 20 * 1000;
    private int times;

    private  Handler mHander = new Handler();
    private Runnable heartBeatRunable = new Runnable() {
        @Override
        public void run() {

                if (MyWebSocketClient.getInstance().isClosed()) {
                    reconnectWs();
                }

                times++;
                Log.i("心跳连接次数", String.valueOf(times));
                mHander.postDelayed(this, HRART_BEAT_RATE);
        }

    };

    public void reconnectWs() {

        //停止执行heartbeat线程
        mHander.removeCallbacks(heartBeatRunable);
        new Thread() {
            @Override
            public void run() {
                    try {
                        Log.i("正在重连中","重连中");
                        MyWebSocketClient.getInstance().reconnectBlocking();
                    } catch (InterruptedException e) {
                        Log.e("重连失败：","+");
                        e.printStackTrace();
                    }

            }
        }.start();
    }

    public void startHeartBeatTest() {
            Log.d("开始postDelayed", "开始...");
            mHander.postDelayed(heartBeatRunable, HRART_BEAT_RATE);
    }

    public static ConnetionAliveTest getInstance() {
        if(mInstance == null){
            synchronized (ConnetionAliveTest.class) {
                if(mInstance == null) {
                    mInstance = new ConnetionAliveTest();
                }
            }
        }
        return mInstance;
    }
}

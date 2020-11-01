package com.example.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.until.AppContext;
import com.example.until.LocalThreadPools;

public class ConnetionAliveTest {
    private static ConnetionAliveTest mInstance;
    private static final long HRART_BEAT_RATE = 20 * 1000;
    private int times;

    private  Handler mHander = new Handler(Looper.getMainLooper());
    private Runnable heartBeatRunable = new Runnable() {
        @Override
        public void run() {

                if (MyWebSocketClient.getInstance().isClosed()) {
                    reconnectWs();
                }

                times++;
                Log.i("心跳连接次数", String.valueOf(times));
                Log.i("线程状态", String.valueOf(Thread.activeCount()));
                mHander.postDelayed(this, HRART_BEAT_RATE);
        }

    };

    public void reconnectWs() {

        //停止执行heartbeat线程
        mHander.removeCallbacks(heartBeatRunable);
        LocalThreadPools.getInstance(AppContext.getContext()).execute(

                () -> {
                    try {
                        Log.i("正在重连中","重连中");
                        MyWebSocketClient.getInstance().reconnectBlocking();
                    } catch (InterruptedException e) {
                        Log.e("重连失败：","+");
                        e.printStackTrace();
                    }

                }
        );

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

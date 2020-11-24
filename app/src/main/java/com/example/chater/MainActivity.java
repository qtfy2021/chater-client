package com.example.chater;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.example.presenter.*;
import com.example.service.DownLoadService;
import com.example.service.UpLoadService;
import com.example.service.WebSocketClientService;
import com.example.model.Dao.DataBaseManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.IBinder;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private TextView mTextMessage;

    private FragmentTransaction ft;
    //interface
    private  MessagePresenter messPresenter;

    private FrameLayout contentLayout;

    private blogFragment blogfragment;
    private homeFragment homefragment;
    private FriendFragment friendfragment;

    private Fragment mContent;

    /*service*/
    private WebSocketClientService webSocketClientService;
    private WebSocketClientService.WebSocketClientBinder binder;

    private UpLoadService upLoadService;
    private UpLoadService.UpLoadServiceBinder upLoadServiceBinder;

    private DownLoadService downLoadService;
    private DownLoadService.MyBinder downLoadServiceBinder;

    //服务绑定活动
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (WebSocketClientService.WebSocketClientBinder) service;
            webSocketClientService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnection upLoadServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            upLoadServiceBinder = (UpLoadService.UpLoadServiceBinder) iBinder;
            upLoadService = upLoadServiceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private ServiceConnection downLoadServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downLoadServiceBinder  = (DownLoadService.MyBinder) iBinder;
            downLoadService = downLoadServiceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);

        //初始化
        init();
        //初始化底部切换栏
        initFramgmenTransaction();
        switchContent(1);

        //绑定service
        bindService();
        //注册广播
        registerReceiver();
        //

        Log.d("MainActivity", "ThreadID:" + String.valueOf(Thread.currentThread().getId()));
         startJWebSClientService();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //服务绑定activity
    private void bindService() {
        Log.i("服务:", "在main_activity绑定");
        Intent bindIntent = new Intent(MainActivity.this, WebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);

        Intent upLoadbindService = new Intent(MainActivity.this, UpLoadService.class);
        bindService(upLoadbindService, upLoadServiceConnection, BIND_AUTO_CREATE);

        Intent downLoadbindService = new Intent(MainActivity.this, DownLoadService.class);
        bindService(downLoadbindService, downLoadServiceConnection, BIND_AUTO_CREATE);

    }

    //开启服务
    private void startJWebSClientService() {
        Log.i("启动服务", "启动");
        Intent intent = new Intent(this, WebSocketClientService.class);
        startService(intent);
    }

    //1、定义广播
    //2、动态注册广播
    private class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent){
                //此处更新ui
            //动态注册中BroadcastReceiver和Activity也是运行在同一个进程中的，因此调用sendBraodcast()时，
            // 传入onReceive()方法里的Context对象context其实就是调用sendBroadcast()的Activty对象
            messPresenter.updateUi(MainActivity.this, intent);
        }
    }


    private void registerReceiver() {
        Log.i("broadcast:", "main_activity注册接受者messageReceiver");
        MessageReceiver messageReceiver = new MessageReceiver();
        //IntentFilter filter = new IntentFilter("com.chater.servicecallback.messagecontent");
        IntentFilter filter = new IntentFilter("com.chatter.chatterHub.messageContent");
        registerReceiver(messageReceiver, filter);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        switchContent(1);
                        return true;
                    case R.id.navigation_dashboard:
                        switchContent(2);
                        return true;
                    case R.id.navigation_notifications:
                        switchContent(3);
                        return true;
                }

                return false;
            };

    private void init() {
        ft = getSupportFragmentManager().beginTransaction();
        messPresenter = new MessagePresenterImpl();
        contentLayout = findViewById(R.id.fragment_Layout);
        mContent = null;

        DataBaseManager.getInstance().getDatabase(getApplicationContext());

    }

    private void initFramgmenTransaction() {
        if (homefragment != null && homefragment.isAdded() ) {
            ft.remove(homefragment);
        }
        if (friendfragment != null && friendfragment.isAdded() ) {
            ft.remove(friendfragment);
        }
        if (blogfragment != null && blogfragment.isAdded() ) {
            ft.remove(blogfragment);
        }
        ft.commitAllowingStateLoss();
        blogfragment = null;
        friendfragment = null;
        homefragment = null;

    }

    private void switchContent(int itemNum) {

        Fragment mfragment = null;
        ft = getSupportFragmentManager().beginTransaction();

        if(itemNum == 1) {
            if(homefragment == null) {
                homefragment = new homeFragment();
            }
            mfragment = homefragment;
        }

        else if(itemNum == 2) {
            if(friendfragment == null) {
                friendfragment = new FriendFragment();
            }
            mfragment = friendfragment;
        }

        else if(itemNum == 3) {
            if(blogfragment == null) {
                blogfragment = new blogFragment();
            }
            mfragment = blogfragment;
        }

        if(mContent == null){
            ft.add(contentLayout.getId(), mfragment).commit();
            mContent = mfragment;
        }else {
            if (!mfragment.isAdded()) {
                ft.hide(mContent).add(contentLayout.getId(), mfragment).commitAllowingStateLoss();
            } else {
                ft.hide(mContent).show(mfragment).commitAllowingStateLoss();
            }
            mContent = mfragment;
        }
    }

    public Fragment getHomeFragment(){
        return homefragment;
    }

    public DownLoadService getDownLoadService() {
        return downLoadService;
    }

}

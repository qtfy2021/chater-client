package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.File;

public class DownLoadService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends Binder {
        public DownLoadService getService(){
            return DownLoadService.this;
        }
    }

    public void startDownLoad(File file, String url){

    }

}

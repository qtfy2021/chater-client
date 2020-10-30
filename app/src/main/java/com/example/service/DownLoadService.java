package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.model.Entity.DownloadMessage;
import com.example.model.Entity.Pictures;
import com.example.net.DownLoadPicHttp;
import com.example.until.LocalThreadPools;
import com.example.until.UserInfoUtil;

public class DownLoadService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public DownLoadService getService(){
            return DownLoadService.this;
        }
    }

    public void startDownLoad(DownloadMessage downloadMessage, String filePath, String url){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", UserInfoUtil.getUserToken());
        jsonObject.put("downloadMsg", JSON.toJSONString(downloadMessage));

        LocalThreadPools.getInstance(this).execute(
            new DownLoadRunnable(jsonObject, filePath, url)
        );
    }


    public class DownLoadRunnable implements Runnable{
        JSONObject downloadJson;
        String filePath;
        String url;
        DownLoadRunnable(JSONObject downloadJson, String filePath, String url){
            this.downloadJson = downloadJson;
            this.filePath = filePath;
            this.url = url;
        }

        @Override
        public void run() {
            Pictures pictures = new DownLoadPicHttp().downLoadPic(downloadJson, filePath, url);

            Message message = new Message();
            message.obj = pictures;
            message.what = 02;
            new Handler().sendMessage(message);
        }
    }
}

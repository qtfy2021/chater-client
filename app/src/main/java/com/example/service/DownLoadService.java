package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.enumClass.FileTypeEnum;
import com.example.model.Entity.DownloadMessage;
import com.example.model.Entity.Pictures;
import com.example.net.DownLoadPicHttp;
import com.example.until.LoadLocalPic;
import com.example.until.LocalThreadPools;
import com.example.until.UrlUtil;
import com.example.until.UserInfoUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DownLoadService extends Service {

    public DownLoadService(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(DownloadMessage downloadMessage){
        startDownLoad(downloadMessage);
    }


    public void startDownLoad(DownloadMessage downloadMessage){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", UserInfoUtil.getUserToken());
        jsonObject.put("downloadMsg", JSON.toJSONString(downloadMessage));

        Runnable runnable = null;
        int TypeNum = Integer.valueOf(downloadMessage.getFileType());
        FileTypeEnum fileTypeEnum = FileTypeEnum.getFileTypeEnumByTypeNum(TypeNum);
        switch (fileTypeEnum){
            case HEAD_PIC:
                //runnable = new DownLoadPicRunnable(jsonObject, LoadLocalPic.CHAT_PIC_DIR, UrlUtil.PIC_URL.DOWNLOAD_URL);
                break;
            case CHAT_PICTURE:
                runnable = new DownLoadPicRunnable(jsonObject, LoadLocalPic.CHAT_PIC_DIR, UrlUtil.PIC_URL.DOWNLOAD_URL);
                break;
            case STATIC_RESOURCE:
                //runnable = new DownLoadPicRunnable(jsonObject, LoadLocalPic.CHAT_PIC_DIR, UrlUtil.PIC_URL.DOWNLOAD_URL);
                break;
            default:
                break;
        }

        if (runnable != null){
            LocalThreadPools.getInstance(this).execute(runnable);
        }
    }


//在线程池运行，空闲线程自动回收
    public class DownLoadPicRunnable implements Runnable{
        JSONObject downloadJson;
        String filePath;
        String url;
        DownLoadPicRunnable(JSONObject downloadJson, String filePath, String url){
            this.downloadJson = downloadJson;
            this.filePath = filePath;
            this.url = url;
        }

        @Override
        public void run() {
            Pictures pictures = null;

            pictures = new DownLoadPicHttp().downLoadPic(downloadJson, filePath, url);

            Intent intent = new Intent();
            intent.setAction("com.chatter.chatterHub.messageContent");
            intent.putExtra("picture", pictures);
            DownLoadService.this.sendBroadcast(intent);
            Log.d("DownLoadPicRunnable", String. format("thread %d finished", this.hashCode()));


            Thread.currentThread().interrupt();
            /*Message message = new Message();
            message.obj = pictures;
            message.what = 02;
            new Handler().sendMessage(message);*/
        }
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        EventBus.getDefault().unregister(this);
    }
}

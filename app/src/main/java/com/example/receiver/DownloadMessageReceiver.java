package com.example.receiver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.chater.MainActivity;
import com.example.model.Entity.DownloadMessage;
import com.example.model.Entity.Pictures;
import com.example.service.DownLoadService;
import com.example.until.AppContext;
import com.example.until.LoadLocalPic;
import com.example.until.UrlUtil;
import com.example.until.UserInfoUtil;

import java.lang.ref.WeakReference;

public class DownloadMessageReceiver implements MessageReceiver {
    @Override
    public void parseMessage(JSONObject json, Service service) throws JSONException {

        DownloadMessage downloadMessage = JSON.toJavaObject(json, DownloadMessage.class);


       MainActivity activity = (MainActivity) AppContext.getContext();
       DownLoadService downLoadService = activity.getDownLoadService();
       if (downLoadService != null){
         downLoadService.startDownLoad(
                  downloadMessage,
                  LoadLocalPic.CHAT_PIC_DIR,
                  UrlUtil.PIC_URL.DOWNLOAD_URL);
       }


        Looper.prepare();
        DownLoadMsgHandler downLoadMsgHandler = new DownLoadMsgHandler(service);
       Looper.loop();

    }

    static class DownLoadMsgHandler extends Handler{
        WeakReference<Service> weakReference ;

        public DownLoadMsgHandler(Service service) {
            weakReference = new WeakReference<Service>(service);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 02:
                    Pictures picture = (Pictures) msg.obj;
                    Intent intent = new Intent();
                    intent.setAction("com.chatter.chatterHub.messageContent");
                    intent.putExtra("picture", picture);
                    weakReference.get().sendBroadcast(intent);
                    Looper.myLooper().quit();
                    break;
                default:
                    break;
            }

        }
    };
}

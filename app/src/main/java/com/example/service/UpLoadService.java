package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.example.model.Entity.middleware.LoadFileVo;
import com.example.net.OkHttpUploadDownLoad;
import com.example.until.AppContext;
import com.example.until.LocalThreadPools;
import com.example.until.ToastUntil;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**CREATE BY SHAY 2020/08 **/

public class UpLoadService extends Service {
    UpLoadServiceBinder mBind = new UpLoadServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }

    public class UpLoadServiceBinder extends Binder {
        public UpLoadService getService(){
            return UpLoadService.this;
        }
    }

    /**@param fileVoList 为LoadFileVo文件表，文件本体
     * @param requestHeadInfoList 存储对应LoadFileVo类中的文件信息类列表,上传文件的请求
     * @param url 上传接口链接**/
    public <T> void startUpLoad(final List<LoadFileVo> fileVoList, final List<T> requestHeadInfoList ,final String url, Handler handler){

        LocalThreadPools.getInstance(this).execute(
                new Runnable() {
                    @Override
                    public void run() {

                        List<LoadFileVo> list = new ArrayList<>();
                        for(int index = 0; index < fileVoList.size(); index++){
                            LoadFileVo loadFileVo = fileVoList.get(index);
                            T requestHeadObject = requestHeadInfoList.get(index);

                            //fastjson
                            JSONObject requestFileInfoBodyJson = (JSONObject) JSONObject.toJSON(requestHeadObject);

                           boolean isSucceed =  OkHttpUploadDownLoad.netUpload(loadFileVo.getFile(), requestFileInfoBodyJson, url);
                           if (!isSucceed){
                               list.add(loadFileVo);
                               ToastUntil.showToast("发送失败", AppContext.getContext());
                           }
                        }

                        //记得清空LoadFVoFileList//
                        /**使用handler通知上传结果**/
                        android.os.Message osMessage = new Message();
                        osMessage.what = 001;
                        osMessage.obj = list;
                        Log.i("failedFile Handler", list.toString());


                        //Log.d("uploadService:Looper:", String.valueOf(Looper.myLooper().hashCode()));
                        handler.sendMessage(osMessage);

                        Log.d("uploadPicRunnable", String.format("thread %d finished", this.hashCode()));
                        //Thread.currentThread().interrupt();

                    }
                }



        );

    }

}

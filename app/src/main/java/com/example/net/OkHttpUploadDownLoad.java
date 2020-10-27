package com.example.net;



import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.until.AppContext;
import com.example.until.Base64Uitl;
import com.example.until.ToastUntil;


import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class OkHttpUploadDownLoad {

    /**@JSONObject is fastjson Class
     * **/
    public static boolean netUpload(File file, JSONObject requestFileInfoJson, String url){

        try {
            Log.d("senPicTest", file.getName());
            OkHttpClient okHttpClient = new OkHttpClient.Builder().
                    connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();;

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("info", requestFileInfoJson.toString())
                    .addFormDataPart("file", Base64Uitl.GetImageStr(file))
                   /* .addFormDataPart("file", requestFileInfoJson.getString("picId"),
                    RequestBody.create(MediaType.parse("application/octet-stream"), file))*/
                    .build();

            Log.d("生产base64的字符：", Base64Uitl.GetImageStr(file));

           long startTime = System.currentTimeMillis();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            OkHttpOnCallback callback = new OkHttpOnCallback();

           /* execute的方法是同步方法，
            enqueue的方法是异步方法，*/
            okHttpClient.newCall(request).enqueue(callback);

            long endTime = System.currentTimeMillis();

           //String runtime = String.valueOf(endTime - startTime).substring(0, -3)
             //                   + "."
              //                  + String.valueOf(endTime - startTime).substring(-3);



            return callback.isSucceed();


        }catch (Exception e){
            e.printStackTrace();
            ToastUntil.showToast(file.getName()+"上传失败", AppContext.getContext());
            return false;
        }
    }


    public static boolean netDownLoad(String url, String saveDir){
        return false;
    }
}

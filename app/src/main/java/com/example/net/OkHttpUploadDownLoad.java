package com.example.net;



import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.until.AppContext;
import com.example.until.Base64Uitl;
import com.example.until.ToastUntil;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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


           long startTime = System.currentTimeMillis();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            OkHttpOnCallback callback = new OkHttpOnCallback();

           /* execute的方法是同步方法，
            enqueue的方法是异步方法，*/
            //okHttpClient.newCall(request).enqueue(callback);
            Response response = okHttpClient.newCall(request).execute();

            long endTime = System.currentTimeMillis();

           //String runtime = String.valueOf(endTime - startTime).substring(0, -3)
             //                   + "."
              //                  + String.valueOf(endTime - startTime).substring(-3);

            return response.isSuccessful();


        }catch (Exception e){
            e.printStackTrace();
            ToastUntil.showToast(file.getName()+"上传失败", AppContext.getContext());
            return false;
        }
    }


    //通过设置ReadTimeout参数，例：超过5秒没有读取到内容时，就认为此次读取不到内容并
    public static Response netDownLoad(JSONObject requestFileInfoJson, String url) throws IOException {


        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();


        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"), JSON.toJSONString(requestFileInfoJson));

        Request request = new Request.Builder().url(url).post(requestBody).build();



        OkHttpOnCallback callback = new OkHttpOnCallback();

           /* execute的方法是同步方法，
            enqueue的方法是异步方法，*/
        Response response = okHttpClient.newCall(request).execute();

        return response;
    }


}

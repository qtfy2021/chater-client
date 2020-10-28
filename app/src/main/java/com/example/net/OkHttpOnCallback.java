package com.example.net;

import android.util.Log;

import com.example.until.AppContext;
import com.example.until.ToastUntil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OkHttpOnCallback implements Callback {

        private boolean isSucceed = false;
        private Response response = null;

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws
        IOException {

            //?toString
        String result = response.body().string();
        Log.i("Http Response Result", response.body().toString());

        if (!response.isSuccessful()) {
            Log.e("Http Response Error","response code: " + response.code());
            return;
        }

        if(result.equals("{}")) {
            Log.e("Http Response null","response: " + result);
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(JSONTokener(result));
            //服务器端出错
            if(!jsonObject.get("errorMsg").equals("0")){

                ToastUntil.showToast(jsonObject.getString("errorMsg")  , AppContext.getContext() );

            }else{
                //成功
                isSucceed = true;
               this.response = response;
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

    }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    /*runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {*/
        ToastUntil.showToast("服务器连接出错" + e.getMessage(), AppContext.getContext() );
                         /*             }
                                  });*/
    }

    public boolean isSucceed(){
            return isSucceed;
    }

    public Response getResponse(){
        return response;
    }


    public String JSONTokener(String in) {
        // consume an optional byte order mark (BOM) if it exists
        if (in != null && in.startsWith("\ufeff")) {
            in = in.substring(1);
        }
        return in;
    }

}

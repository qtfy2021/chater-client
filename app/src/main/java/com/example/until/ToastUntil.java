package com.example.until;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;


public class ToastUntil {
    public static void showToast(String text, Context context){

        if(Looper.myLooper() == Looper.getMainLooper()){
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }else {
            Looper.prepare();
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            Looper.loop();

        }
    }
}

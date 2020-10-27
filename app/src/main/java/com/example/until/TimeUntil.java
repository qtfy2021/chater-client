package com.example.until;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;

public class TimeUntil {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDateTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = formatter.format(date);

        return time;
    }
}

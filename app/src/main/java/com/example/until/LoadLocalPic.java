package com.example.until;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

public class LoadLocalPic {
    public static final String CHAT_PIC_DIR = "/sdcard/chatter/chat_pic/";
    public static final String USER_HEAD_ICON_DIR = "/sdcard/chatter/head_pic/";


    public static Bitmap getChatPicBitmap(String fileName, Activity activity){
        return getBitmap(CHAT_PIC_DIR, fileName, activity);
    }

    public static Bitmap getBitmap(String path, String fileName, Activity activity){
        File file = new File(path);
        Bitmap bitmap = null;
        try {
            if(!file.exists()){
                file.mkdir();
            }else {
               // FileInputStream fs = new FileInputStream(path + fileName);


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;//只解析图片信息

                 bitmap  = BitmapFactory.decodeFile(path + fileName,  options);

                //计算缩放比
                int rate = (int)(options.outHeight / (float)100);
                if (rate <= 0)
                    rate = 1;
                options.inSampleSize = rate;

                //重新载入
                options.inJustDecodeBounds = false;
                bitmap  = BitmapFactory.decodeFile(path + fileName,  options);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LoadLocalPic：" , "图片读取出错");
        }finally {
            return bitmap;
        }

    }

}

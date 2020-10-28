package com.example.until;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

public class LoadLocalPic {
    public static final String BOOK_PIC_DIR = "/sdcard/chatter/chat_pic/";
    public static final String USER_HEAD_ICON_DIR = "/sdcard/chatter/head_pic/";


    public static Bitmap getBookCoverBitmap(String fileName, Activity activity){
        return getBitmap(BOOK_PIC_DIR, fileName, activity);
    }

    public static Bitmap getBitmap(String path, String fileName, Activity activity){
        File file = new File(path);
        Bitmap bitmap = null;
        try {
            if(!file.exists()){
                file.mkdir();
            }else {
                FileInputStream fs = new FileInputStream(path + fileName);

                bitmap  = BitmapFactory.decodeStream(fs);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LoadLocalPic：" , "图片读取出错");
        }finally {
            return bitmap;
        }

    }

}

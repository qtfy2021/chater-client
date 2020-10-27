package com.example.model.Entity.middleware;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;

/**
 * 读取图片封装实体类, 一个图片上传信息封装一个实体类
 * **/

public class LoadFileVo implements Serializable {

    File file;

    int progress;//进度
    boolean isUpload = false;

    byte[] bitmap;

    public Bitmap getBitmap() {
         return BitmapFactory.decodeByteArray(bitmap, 0,bitmap.length);//从字节数组解码位图

    }
    public  byte[] getBitmapArray() {
        return bitmap;

    }


    public LoadFileVo(){}

    public LoadFileVo(File file, int pg){
        this.file = file;
        this.progress = pg;
    }

    public LoadFileVo(File file, boolean isUpload, int pg, Bitmap bitmap){
        this.file = file;
        this.progress = pg;
        this.isUpload = isUpload;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
        this.bitmap = baos.toByteArray();
    }

    public void setBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
        this.bitmap =  baos.toByteArray();
    }

    public boolean isUpload(){
        return isUpload;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


}

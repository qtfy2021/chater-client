package com.example.presenter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.chater.chatHubActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectPicPresenterImpl implements SelectPicPresenter {

    Context context = null;
    Intent intent = null;
    String mPhotoPath;
    Activity activity;
    Uri uriImg;
    File mPhotoFile;

    public SelectPicPresenterImpl(Activity activity, Intent intent){
        this.context = activity;
        this.intent = intent;
        this.activity = activity;
    }

    @Override
    public void selectpic() {
        dynRequestPermission();
        initSelections();
    }


    private void dynRequestPermission() {
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED)
                || (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ){
                ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                },1);

        }

    }


    private void initSelections() {
        final CharSequence[] items = {"相册", "照相"};
        AlertDialog.Builder dialog = new AlertDialog.Builder((Activity)context);
        dialog.setTitle("添加图片");
        dialog.setItems(items, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        startPickPic();
                        break;
                    case 1:
                        startOpenCamera();
                        break;
                    default:
                        break;
                }
            }
        }).create();
        dialog.show();
    }


    private void startPickPic(){
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setType("image/*");
        activity.startActivityForResult(intent1, 0);
    }

    private void startOpenCamera(){
        try{
            Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
            mPhotoPath  = getSDPath() + "/" + getPhotoFileName();
            mPhotoFile = new File(mPhotoPath);
            if (!mPhotoFile.exists()) {
                mPhotoFile.createNewFile();
                }

            uriImg = FileProvider.getUriForFile(context, "com.ahbcd.app.tms.provider", mPhotoFile);
            Log.i("TAG", "onClick: "+ mPhotoPath +"---------" + context.getPackageName() + ".provider");
            //授予接收 Activity 权限访问 intent 中的特定数据 URI
            intent1.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //设置输出文件
            intent1.putExtra( MediaStore.EXTRA_OUTPUT, uriImg);


            //chathubactivity开始调用相机，等待相机反馈
            activity.startActivityForResult(intent1, 1);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExists = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(sdCardExists) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public  String getPhotoPath(){
        return mPhotoPath;
    }

    public Uri getUriImg(){
        return uriImg;
    }

    public File getmPhotoFile() {
        return mPhotoFile;
    }
}

package com.example.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public interface SelectPicPresenter<priavte> {
    void selectpic();

    public  String getPhotoPath();

    public Uri getUriImg();

    public File getmPhotoFile();

}

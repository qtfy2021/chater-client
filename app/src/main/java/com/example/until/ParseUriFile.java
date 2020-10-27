package com.example.until;

import android.net.Uri;

import java.io.File;

public class ParseUriFile {

    public static File parseUriFile(Uri uri ){
        try {
            File file = new File(uri.toString());
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

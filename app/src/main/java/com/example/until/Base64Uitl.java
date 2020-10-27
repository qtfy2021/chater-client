package com.example.until;

import android.util.Base64;

import java.io.*;

public class Base64Uitl {


    public static boolean generateImg(String base64data, String filePathAndName){
        if (base64data == null || base64data.equals("")){
            return false;
        }



        try{

            // Base64解码,对字节数组字符串进行Base64解码并生成文件
            byte[] b = Base64.decode(base64data, Base64.DEFAULT);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成文件
            OutputStream out = new FileOutputStream(filePathAndName);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }


// 图片转化成base64字符串
    public static String GetImageStr(File filePathAndName) {// 将文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream("/sdcard/81035511_p2.jpg");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码

        return Base64.encodeToString(data, Base64.DEFAULT);// 返回Base64编码过的字节数组字符串
    }



}





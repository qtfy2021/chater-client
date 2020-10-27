package com.example.net;

import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class OkHttpUploadDownLoadTest {



    @Before
    public void setUp() {


    }


    @Test
    public void netUpload() throws JSONException {
        File file = new File("/sdcard/81035511_p2.jpg");
        String json = "{\"1\":\"2\", \"2\":\"3\"}";
        JSONObject jsonObject = new JSONObject(Boolean.parseBoolean(json));
        OkHttpUploadDownLoad.netUpload(file, jsonObject, "https://www.baidu.com");
    }
}
package com.example.net;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.model.Entity.Pictures;
import com.example.model.Entity.middleware.LoadFileVo;
import com.example.service.UpLoadService;
import com.example.until.UrlUtil;

import java.util.ArrayList;
import java.util.List;

public class SendPicListHttp {

   public List<String> sendPic(List<Pictures> picsListInfo, List<LoadFileVo> fileVoList, UpLoadService service){
        //考虑fileList替换为深拷贝
        final List<Pictures> picInfoList = picsListInfo;

        //用于存储正确的图片id
        final List<String> picIdList = new ArrayList<String>();

        /**上传图片**/
        if(picInfoList.size() > 0){

            Log.d("SendPicListHttp", String.valueOf(fileVoList.size()));

            //等待通知
            Looper.prepare();

            Log.d("sendPicList:Looper:", String.valueOf(Looper.myLooper().hashCode()));

            @SuppressLint("HandlerLeak")
            Handler handler = new Handler(){
                @Override
                public void handleMessage(android.os.Message msg){
                    if(msg.what == 001){

                        List<LoadFileVo> failedFileList = (List<LoadFileVo>) msg.obj;

                        //清除上传失败的图片
                        if(failedFileList.size() > 0){
                            for(LoadFileVo mFile:failedFileList){
                                String mFileid = mFile.getFile().getName().split(".")[0];

                                for( int i = 0 ; i < picInfoList.size(); i++){
                                    Pictures mPicture = picInfoList.get(i);
                                    if(mPicture.getPicId().equals(mFileid)){
                                        picInfoList.remove(i);
                                    }
                                }
                            }
                        }

                        for (Pictures pic:
                                picInfoList) {

                            picIdList.add(pic.getPicId());

                        }

                        //停止等待消息
                        Looper.myLooper().quit();
                    }

                }
            };

            service.startUpLoad(fileVoList, picInfoList, UrlUtil.PIC_URL.UPLOAD_URL, handler);
            Looper.loop();
        }

        return picIdList;

    }


}

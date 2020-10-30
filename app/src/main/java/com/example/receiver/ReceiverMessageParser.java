package com.example.receiver;

import android.app.Service;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.enumClass.MessageTypeEnum;



public class ReceiverMessageParser {
    public void parser(String jsonString, Service service){
        try{
            JSONObject json = JSONObject.parseObject(jsonString);
            int messageType = json.getInteger("MessageType");
            MessageReceiver receiver = null;
            if(messageType > -1){

                //根据消息码获取枚举类
                MessageTypeEnum messageTypeEnum =
                        MessageTypeEnum.getMessageTypeEnumByTypeNum(messageType);

                switch (messageTypeEnum){
                    case CHAT_MESSAGE:
                        receiver = new ChatMessageReceiver();
                        break;
                    case NOTIFICATION:
                        receiver = new NotificationMessageReceiver();
                        break;

                    case  HEARTBEAT_PACKAGE:
                        receiver = new HeartBeatMessageReceiver();
                        break;
                    case ERROR_MESSAGE:
                        receiver = new ErrorMessageReceiver();
                        break;
                    case DOWNLOAD_MESSAGE:
                        receiver = new DownloadMessageReceiver();
                        break;
                    default:
                        Log.i( "receviceMessageformNet:", "dose not exist messageType");
                        break;
                }
                if(receiver != null){
                    Log.d("解析", "解析json");
                    receiver.parseMessage(json, service);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

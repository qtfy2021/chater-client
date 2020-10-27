package com.example.until;

import android.app.Service;
import android.util.Log;

import com.example.enumClass.MessageTypeEnum;

import com.example.receiver.ChatMessageReceiver;
import com.example.receiver.DownloadMessageReceiver;
import com.example.receiver.ErrorMessageReceiver;
import com.example.receiver.HeartBeatMessageReceiver;
import com.example.receiver.MessageReceiver;
import com.example.receiver.NotificationMessageReceiver;

import org.json.JSONException;
import org.json.JSONObject;

public class ReceiverMessageParser {
    public void parser(String jsonString, Service service){
        try{
            JSONObject json = new JSONObject(jsonString);
            int messageType = json.getInt("MessageType");
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

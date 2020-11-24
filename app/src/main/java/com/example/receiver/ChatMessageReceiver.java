package com.example.receiver;

import android.app.Service;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.model.Dao.MessageDao;
import com.example.model.Entity.ChatMessage;
import com.example.until.MD5CodeCeator;
import com.example.until.UserInfoUtil;

public class ChatMessageReceiver implements MessageReceiver{
    @Override
    public void parseMessage(JSONObject json, Service service) throws JSONException {

        String curUserId = UserInfoUtil.getUserId();
        Intent intent = new Intent();
        intent.setAction("com.chatter.chatterHub.messageContent");

        Log.d("准备发送接受到消息广播1", "进行判断" + json.getString("toID"));
        if( json.getString("toID").equals(curUserId)){
            ChatMessage chatMessage = new ChatMessage();

            json.put("messageId", MD5CodeCeator.randomUUID());

            chatMessage.setMessageId(json.getString("messageId"));

            chatMessage.setIsHasPic(json.getInteger("isHasPic"));
            chatMessage.setToID(json.getString("toID"));
            chatMessage.setFromID(json.getString("fromID"));
            chatMessage.setSendTime(json.getString("sendTime"));
            chatMessage.setTextContent(json.getString("textContent"));

            Log.d("准备发送接受到消息广播2", chatMessage.getTextContent());
            MessageDao.getInstance().addMessage(chatMessage, service);
            intent.putExtra("chatMessage", chatMessage);
            service.sendBroadcast(intent);
        }

    }

}

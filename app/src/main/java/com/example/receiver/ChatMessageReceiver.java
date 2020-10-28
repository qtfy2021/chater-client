package com.example.receiver;

import android.app.Service;
import android.content.Intent;
import android.util.Log;

import com.example.model.Dao.MessageDao;
import com.example.model.Entity.Message;
import com.example.until.MD5CodeCeator;
import com.example.until.UserInfoUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class ChatMessageReceiver implements MessageReceiver{
    @Override
    public void parseMessage(JSONObject json, Service service) throws JSONException {

        String curUserId = UserInfoUtil.getUserId();
        Intent intent = new Intent();
        intent.setAction("com.chater.chaterHub.messagecontent");

        Log.d("准备发送接受到消息广播1", "进行判断" + json.getString("toID"));
        if( json.getString("toID").equals(curUserId)){
            Message message = new Message();

            //判断信息是否带有图片
           //int hasPic = json.getInt("isHasPic");
            //message.setHasPic(hasPic);
          /*  if(hasPic){
                JSONArray picList = json.getJSONArray("pictures");
                Queue<String> picQueue = new LinkedList<String>();
                for (int i = 0; i < picList.length(); i++) {
                    picQueue.add((String) picList.get(i));
                }
                message.setPicQueue(picQueue);
            }
*/

            Random random = new Random();
            json.put("messageId", MD5CodeCeator.randomUUID());

            message.setMessageId(json.getString("messageId"));

            message.setHasPic(json.getInt("isHasPic"));
            message.setToID(json.getString("toID"));
            message.setFromID(json.getString("fromID"));
            message.setSendTime(json.getString("sendTime"));
            message.setTextContent(json.getString("textContent"));

            Log.d("准备发送接受到消息广播2", message.getTextContent());
            MessageDao.getInstance().addMessage(message, service);
            intent.putExtra("message", message);
            service.sendBroadcast(intent);
        }

    }

}

package com.example.model.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.model.Entity.Message;

import java.util.ArrayList;

public class MessageDao {

    //表名
    private final static String MESSAGE_TABLE_NAME = "message";

    //列名
    private final static String MESSAGE_ID = "messageId";
    private final static String FROM_ID = "fromID";
    private final static String TO_ID = "toID";
    private final static String TEXT_CONTENT = "textContent";
    private final static String SEND_TIME = "sendTime";
    private final static String IS_HASPIC = "isHasPic";

    private volatile static MessageDao instance;

    public static MessageDao getInstance() {
        if (instance == null) {
            synchronized (MessageDao.class) {
                if (instance == null) {
                    instance = new MessageDao();
                }
            }
        }
        return instance;
    }


    //查询信息
    public synchronized ArrayList<Message> selectMessageByUserIDandFromID
                                (String toID, String fromID, Context context){

        ArrayList<Message> messageList = null;

        try {
            //创建游标对象
            Cursor cursor = DataBaseManager.getInstance().getDatabase(context)
                    .query(MESSAGE_TABLE_NAME,
                            new String[]{"*"},
                            FROM_ID + "=? and "+ TO_ID + " = ?",
                            new String[]{fromID, toID},
                            null,
                            null,
                            SEND_TIME + " ASC");

            //获取查询数据

            messageList = new ArrayList<Message>();
            while (cursor.moveToNext()) {
                Message message = new Message();
                message.setMessageId(cursor.getString(cursor.getColumnIndex(MESSAGE_ID)));
                message.setFromID(cursor.getString(cursor.getColumnIndex(FROM_ID)));
                message.setToID(cursor.getString(cursor.getColumnIndex(TO_ID)));
                message.setTextContent(cursor.getString(cursor.getColumnIndex(TEXT_CONTENT)));
                message.setSendTime(cursor.getString(cursor.getColumnIndex(SEND_TIME)));
                message.setHasPic(cursor.getInt(cursor.getColumnIndex(IS_HASPIC)) );

                messageList.add(message);
            }

            cursor.close();
        }catch (Exception e){

        }finally {
            //关闭数据库
            DataBaseManager.getInstance().closeDatabase();
            return messageList;
        }
    }

    //添加信息
    public synchronized boolean addMessage(Message message, Context context){
        boolean isSucccess = false;
        try{
            ContentValues cv = new ContentValues();
            cv.put(MESSAGE_ID, message.getMessageId());
            cv.put(FROM_ID, message.getFromID());
            cv.put(TO_ID, message.getToID());
            cv.put(TEXT_CONTENT, message.getTextContent());
            cv.put(SEND_TIME, message.getSendTime());
            cv.put(IS_HASPIC, message.getIsHasPic());

            DataBaseManager.getInstance().getDatabase(context)
                    .insert(MESSAGE_TABLE_NAME, null, cv);

            isSucccess = true;
        }catch (Exception e){
            isSucccess = false;
        }finally {
            return isSucccess;
        }
    }





}

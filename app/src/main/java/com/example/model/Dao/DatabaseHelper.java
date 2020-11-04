package com.example.model.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public  class  DatabaseHelper extends SQLiteOpenHelper {
    private volatile static DatabaseHelper instance = null;

    private final static String DATABASE_NAME = "chaters.db";
    private final static String CREATE_MESSAGE_TABLE_SQL = " CREATE TABLE " +"message ("  +
                                                              "`messageId` VARCHAR(64) NOT NULL," +
                                                             " `textContent` VARCHAR(255) ,"  +
                                                              "`sendTime` DATETIME ,"         +
                                                              "`fromID` VARCHAR(64) ,"        +
                                                             " `toID` VARCHAR(64) ,"          +
                                                              "`isHasPic` TINYINT ,"          +
                                                                "PRIMARY KEY (`messageId`))";

      private final static String CREATE_PICTURES_TABLE_SQL = "CREATE TABLE"  + "pictures" +"(" +
              "picId VARCHAR(64) PRIMARY KEY NOT NULL, " +
              "[messageId] VARCHAR(64) NOT NULL," +
              " [sendId] VARCHAR(64)," +
              "[recId] VARCHAR(64)," +
              " [dateTime] DATETIME)";





    //带全部参数的构造函数，此构造函数必不可少

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d("新建数据库", "成功");
    }

    //保证多线程只有设置一个数据库的连接
    public static  DatabaseHelper getDataHelper(Context context){
        if(instance == null){
            synchronized (DatabaseHelper.class){
                if(instance == null){
                    instance = new DatabaseHelper(context, DATABASE_NAME, null, 1);
                }
            }
        } return instance;
    }


    //创建数据库时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建message表
        Log.d("新建数据库message表：", "进入");
        db.execSQL(CREATE_MESSAGE_TABLE_SQL);
        db.execSQL(CREATE_PICTURES_TABLE_SQL);
        Log.d("新建数据库message表：", "成功");
    }

    //修改表结构使用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("修改数据库表结构：", "进入");
    }


}

package com.example.model.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

//该抽象类中的方法用于避免多线程调用数据库操作开/关时，中断其他其他正在操作的线程
public  class DataBaseManager {
    private AtomicInteger mOpenCounter = new AtomicInteger();//自增长类
    private SQLiteDatabase mDatabase;
    private volatile static DataBaseManager mDataBaseManager = null;
    //打开数据库方法

    public static synchronized DataBaseManager getInstance(){
        if ( mDataBaseManager == null){
            mDataBaseManager = new DataBaseManager();
        }
        return mDataBaseManager;
    }

    public synchronized SQLiteDatabase getDatabase(Context context) {
        //自动couter+1
        if (mOpenCounter.incrementAndGet() == 1) {//incrementAndGet会让mOpenCounter自动增长1
            // Opening new database
            //通过该方法创建的数据库存放的目录是固定的，其路径为/data/data/**packageName**/databases/
            //当第一次调用getReadableDatabase()方法或getWritableDatabase()方法即可返回一个SQLiteDatabase对象。如果是第一次调用，则会创建数据库。
            try {
                mDatabase = DatabaseHelper.getDataHelper(context).getWritableDatabase();
        } catch (Exception e) {
            mDatabase = DatabaseHelper.getDataHelper(context).getReadableDatabase();
        }
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        //自动couter-1
        if (mOpenCounter.decrementAndGet() == 0){
            mDatabase.close();
        }
    }


}

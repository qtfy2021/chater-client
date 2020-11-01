package com.example.until;


import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description TODO 全局线程池
 * @author shay
 * @Date 2020/8/17
 *
 * **/
public class LocalThreadPools {

    /********************线程池设置********************/
    private static String TAG = LocalThreadPools.class.getSimpleName();

    private static ExecutorService THREAD_POOL_EXECUTOR;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2;

    private static final int KEEP_ALIVE_SECONDS = 60;

    private static final BlockingDeque<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(8);

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {

        //初始值1
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "new Task" + mCount.getAndIncrement());
        }
    };


    private class RejectedHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            ToastUntil.showToast("操作过于频繁，稍后再试" , AppContext.getContext() );
        }
    }


    private void initThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory, new RejectedHandler()) {

            @Override
            public void execute(Runnable command){
                super.execute(command);

                Log.d(TAG,"子任务线程池状态");
                Log.d(TAG,"ActiveCount="+getActiveCount());
                Log.d(TAG,"PoolSize="+getPoolSize());
                Log.d(TAG,"Queue="+getQueue().size());

            }

        };

        //核心线程空闲超时回收
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    /****************************************/


    private WeakReference<Context> mContext;
    private LocalThreadPools(Context context){
        mContext = new WeakReference<>(context);
        initThreadPool();
    }


    private static LocalThreadPools instance;
    public static LocalThreadPools getInstance(Context context){

        if(instance == null){
            instance = new LocalThreadPools(context);
        }

        return instance;
    }


    public void execute(Runnable command){
        THREAD_POOL_EXECUTOR.execute(command);

    }


}

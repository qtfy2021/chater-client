package com.example.chater;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import com.example.ViewAdapter.LoadPicAdapter;
import com.example.listener.OnClcikSendMessageListener;
import com.example.model.Entity.middleware.LoadFileVo;
import com.example.presenter.LoadChatMessage;
import com.example.presenter.MessagePresenter;
import com.example.presenter.ReceiveMessageAtChatHub;
import com.example.presenter.SelectPicPresenter;
import com.example.presenter.SelectPicPresenterImpl;
import com.example.service.UpLoadService;
import com.example.service.WebSocketClientService;
import com.example.until.ParseUriFile;
import com.example.until.PermissionsUtil;
import com.example.until.ToastUntil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**CREATE BY SHAY 2020**/

/*聊天界面，聊天对象id存于当前activity intent*/
public class chatHubActivity extends AppCompatActivity {
    //view
    Button sendButton;

    private boolean isPickPicLayoutOpen = false;

    //service
    private WebSocketClientService webSocketClientService;
    private WebSocketClientService.WebSocketClientBinder binder;

    private UpLoadService upLoadService;
    private UpLoadService.UpLoadServiceBinder upLoadServiceBinder;

    //presenter
    private MessagePresenter messagePresenter;
    private MessagePresenter recMessagePresenter;
    private SelectPicPresenter selectPicPresenter;

    //上传文件序列表
    private List<LoadFileVo> fileList = new ArrayList<>();
    LoadPicAdapter adapter = null;

    @BindView(R.id.chat_content_hub_rvPic)
    RecyclerView rvPic;

    @BindView(R.id.chat_content_hub_tvNum)
    TextView tvNum;

    @BindView(R.id.chat_content_hub_addpicbutton)
    Button addPicButton;

    @BindView(R.id.chat_hub_scrollView)
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chat_hub);

        ButterKnife.bind(this);


        //创建presenter
        messagePresenter = new LoadChatMessage();
        recMessagePresenter = new ReceiveMessageAtChatHub();

        selectPicPresenter = new SelectPicPresenterImpl(this, getIntent());

        //加载聊天信息
        messagePresenter.updateUi(this, getIntent());
        scrollView.fullScroll(View.FOCUS_DOWN);
        //QIDONGFUWU
        startJWebSClientService();
        //绑定服务
        bindService();
        //注册服务
        registerDReceiver();
        //注册按键监听器
        registerListener();
        //initAdapter
        initAdapter();


        PermissionsUtil.verifyStoragePermissions(this);




//然后通过一个函数来申请

    }

    //使用Bitmap显示图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("", "");

        if (requestCode == 1){//相机反馈
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一
            Bitmap bitmap = BitmapFactory.decodeFile(selectPicPresenter.getPhotoPath(), options);
            if( bitmap != null){
                if(selectPicPresenter.getUriImg() != null){
                    saveUritoFile(selectPicPresenter.getUriImg(), 1);
                }

                if (!bitmap.isRecycled()){
                    bitmap.recycle();//回收图片内存
                    System.gc();//提醒系统及时回收
                }

            }
        }

        if(requestCode == 0){//相册反馈
            Log.i("图册选择测试", "收到相册反馈");
            if(data != null){
                Uri uri = data.getData();
                Log.i("图册选择测试", uri.toString());
                saveUritoFile(uri, 0);
            }else{
                Log.e("图册选择测试error", "反馈为null");
            }
        }
    }

    //选择图片转为file，作为上传文件，并添加进fileList，通知recycleview更新，显示预览图片
    private void saveUritoFile(Uri uriImage, int type){
        Bitmap photoBmp = null;
        if (uriImage != null){
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;

               /** 外界的程序通过ContentResolver接口可以访问ContentProvider提供的数据*/
                photoBmp = BitmapFactory.decodeStream(this.getContentResolver().
                                openInputStream(uriImage),null, options);
                File file = new File("");

                if( type == 0){
                    file = ParseUriFile.parseUriFile(uriImage);
                    Log.i("图册选择测试", file.getName());
                }else {
                    if(selectPicPresenter.getmPhotoFile() != null){
                        file = selectPicPresenter.getmPhotoFile();
                    }
                }

                fileList.add(new LoadFileVo(file, false, 0, photoBmp));
                tvNum.setText( (fileList.size() - 1) + "/8");
                if (fileList.size() > 8){
                    //index 0是 + 号图标

                }

                    //提醒更新recycleView
                adapter.notifyDataSetChanged();

            }catch(Exception e) {
                e.printStackTrace();

            }
        }


    }

   /*****************设置服务连接********************/
    //ws的连接
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof WebSocketClientService.WebSocketClientBinder){
                binder = (WebSocketClientService.WebSocketClientBinder) service;
                webSocketClientService = binder.getService();
            }else if (service instanceof UpLoadService.UpLoadServiceBinder){
                upLoadServiceBinder = (UpLoadService.UpLoadServiceBinder) service;
                upLoadService = upLoadServiceBinder.getService();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    //
   /* private ServiceConnection upLoadServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("service test", "正在注册upload service");
            upLoadServiceBinder = (UpLoadService.UpLoadServiceBinder) service;
            upLoadService = upLoadServiceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };*/

    /******************************/

    //开启连接服务
    private void startJWebSClientService() {
        Intent intent = new Intent(this, WebSocketClientService.class);
        startService(intent);
        Intent intent2 = new Intent(this, UpLoadService.class);
       startService(intent2);
    }


    //服务绑定当前activity


    private  void bindService() {
        Log.i("服务:", "在chathub_activity绑定");
        Intent bindIntent = new Intent(chatHubActivity.this, WebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);

        Intent bindUpLoadIntent = new Intent(chatHubActivity.this, UpLoadService.class);
        bindService(bindUpLoadIntent, serviceConnection, BIND_AUTO_CREATE);//bind并且创建service

    }

    //1、定义广播
    //2、动态注册广播
    private class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
                Log.i("onRecevice:", "chathub_activity接收到信息");
                recMessagePresenter.updateUi(chatHubActivity.this, intent);
        }
    }

    private void registerDReceiver() {
        Log.i("服务:", "chathub_activity注册接受者");
        MessageReceiver messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter("com.chatter.chatterHub.messageContent");
        registerReceiver(messageReceiver, filter);


    }

    private void registerListener(){
        sendButton = findViewById(R.id.chat_content_hub_sendbutton);
        sendButton.setOnClickListener(new OnClcikSendMessageListener(chatHubActivity.this, fileList));

        addPicButton.setOnClickListener(v -> {
            LinearLayout aLinearLayout = findViewById(R.id.chat_content_hub_addPicLayout);
            if(isPickPicLayoutOpen){
                aLinearLayout.setVisibility(View.INVISIBLE);
                isPickPicLayoutOpen = false;
            }else {
                aLinearLayout.setVisibility(View.VISIBLE);
                isPickPicLayoutOpen = true;
            }
        });
    }



    private void initAdapter() {

        fileList.add(new LoadFileVo());


        //传入list引用，stringbuffer，stringbuilder同理
        adapter = new LoadPicAdapter(this, fileList, 8);


        rvPic.setAdapter(adapter);
        rvPic.setLayoutManager(new GridLayoutManager(this, 5));

        //item间距
        rvPic.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int paddingPix = 5;

                outRect.bottom = paddingPix;
                outRect.right = paddingPix;
                outRect.left = paddingPix;
            }
        });

        //设置触发item选择
        adapter.setListener(new LoadPicAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position) {
                if (fileList.size() > 8){
                    ToastUntil.showToast("一次只能上传8张图片", chatHubActivity.this);

                }else {
                    selectPicPresenter.selectpic();

                }
            }

            @Override
            public void del(View view) {
                tvNum.setText((fileList.size()) - 1 + "/8");
            }
        });

    }

    public WebSocketClientService getWebSocketClientService(){
        return webSocketClientService;
    }

    public UpLoadService getUpLoadService() {

        return upLoadService;
    }
}

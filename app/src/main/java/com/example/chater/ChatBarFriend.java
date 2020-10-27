package com.example.chater;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ChatBarFriend extends LinearLayout {
    private ImageView headImgView;
    private TextView contentTextView;
    private LinearLayout contentLayout;
    private Context mContext;
    private View mView;

    private OnClickListener onClickListener;

    private int imageId;
    private String contentText;
    private String nameText;
    private String messageId;
    private String UId;



    public int getHeadImgId() {
        return imageId;
    }

    public String getContentText() {
        return contentText;
    }
    public String getNameText() {
        return nameText;
    }

    //设置主要显示文字
    public void setContentText (String text) {
        if (text != null ) {
            contentText = text;
            contentTextView.setText(text);
        }

    }

    //显示头像
    public void setHeadImg(Bitmap image) {
        if (image != null) {
            headImgView.setImageBitmap(image);
        }
    }

    //添加发送图片
    public void addContentImg(Bitmap image) {
        if (image != null) {
            ImageView imageView = new ImageView(mContext);
            LayoutParams params = (LayoutParams) imageView.getLayoutParams();
            params.width = LayoutParams.MATCH_PARENT;
            params.height = LayoutParams.WRAP_CONTENT;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(image);
            imageView.setLayoutParams(params);

            contentLayout.addView(imageView);
        }
    }


    //设置头像图片id
    public void setImageById(int imageId) {
        if (imageId != R.drawable.icon_) {
            this.imageId = imageId;
            headImgView.setImageResource(imageId);
        }
    }

     public TextView getContentTextView () {
        return  contentTextView;
     }

    public ImageView getHeadImgView () {
        return  headImgView;
    }


    /***********
     * 1) Context是一个抽象类，其通用实现在ContextImpl类中。
     *
     * 2) Context：是一个访问application环境全局信息的接口，通过它可以访问application的资源和相关的类，其主要功能如下：
     *
     * 启动Activity
     * 启动和停止Service
     * 发送广播消息(Intent)
     * 注册广播消息(Intent)接收者
     * 可以访问APK中各种资源(如Resources和AssetManager等)
     * 可以访问Package的相关信息
     * APK的各种权限管理
     *
     * 3)其中context是当前Activity的Context,
     * attrs是一组属性值,例如layout_width,layout_height等等,
     * defStyleAttr和控件的style有关
     * *************/
    public ChatBarFriend(Context context) {
        this(context,null);
    }

    public ChatBarFriend(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatBarFriend(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        /***
         * 获得 LayoutInflater 实例的三种方式

        1.

        LayoutInflater inflater = getLayoutInflater();  //调用Activity的getLayoutInflater()
        2.

        LayoutInflater localinflater =(LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        3.

         LayoutInflater inflater = LayoutInflater.from(context);
         ****/
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //调用Activity的getLayoutInflater()<-实质上也就是调用getSystemService;xml布局文件，并且实例化
        mView = inflater.inflate(R.layout.chat_bar_friend,this, true);

        contentTextView = (TextView) mView.findViewById(R.id.char_bar_friend_Textview);
        headImgView = (ImageView) mView.findViewById(R.id.char_bar_friend_Imageview);
        contentLayout = (LinearLayout) mView.findViewById(R.id.char_bar_friend_contentLayout);

        TypedArray typedarray = mContext.obtainStyledAttributes(attrs, R.styleable.chatBarLayout);

        setContentText(typedarray.getString(R.styleable.chatBarLayout_content_text));
        setImageById(typedarray.getResourceId(R.styleable.chatBarLayout_imag_src, R.drawable.icon_));
    }



    //设置view的onclick监听器转移到当前控件，设置监听器
    public void setViewOnclickClickListener(OnClickListener onclickClickListener) {
        this.onClickListener = onclickClickListener;
        mView.setOnClickListener(onclickClickListener);

    }



}

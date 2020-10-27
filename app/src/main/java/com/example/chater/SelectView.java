package com.example.chater;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

public class SelectView extends LinearLayout {
    private ImageView imageView;
    private TextView titleTextView;

    private OnClickListener onClickListener;

    private int imageId;
    private String titleText;
    private Context mContext;
    private View mView;
    private String fromID;

    public int getImageId() {
        return imageId;
    }

    public String getTitleText() {
        return titleText;
    }


    //设置主要显示文字
    public void setTitleText (String text) {
        if (text != null ) {
            titleText = text;
            titleTextView.setText(text);
        }

    }

    //显示头像
    public void setImage(Bitmap image) {
        if (image != null) {
            imageView.setImageBitmap(image);
        }
    }


    //设置头像图片id
    public void setImageId(int imageId) {
        if (imageId != 10000) {
            this.imageId = imageId;
            imageView.setImageResource(imageId);
        }
    }

    public void setFromID(String fromID){
        this.fromID = fromID;
    }

     public TextView getTitleTextView () {
        return  titleTextView;
     }

    public String getFromID(){return fromID;}
    public ImageView getImageView () {
        return  imageView;
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
    public SelectView(Context context) {
        this(context,null);
    }

    public SelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//调用Activity的getLayoutInflater()<-实质上也就是调用getSystemService;xml布局文件，并且实例化
        mView = inflater.inflate(R.layout.menuitem,this, true);

        titleTextView = (TextView) mView.findViewById(R.id.menu_item_titleText);
        imageView = (ImageView) mView.findViewById(R.id.menu_item_image);


        TypedArray typedarray = mContext.obtainStyledAttributes(attrs, R.styleable.menuitemLayout);

        setTitleText(typedarray.getString(R.styleable.menuitemLayout_title_text));
        setImageId(typedarray.getResourceId(R.styleable.menuitemLayout_icon_refrence, 1000));
    }


    //设置view的onclick监听器转移到当前控件，设置监听器
    public void setViewOnclickClickListener(OnClickListener onclickClickListener) {
        this.onClickListener = onclickClickListener;
        mView.setOnClickListener(onclickClickListener);

    }



}

package com.example.listener;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.chater.SelectView;
import com.example.chater.chatHubActivity;

//聊天列表-聊天对象监听器
public class SelectViewOnclickListener implements View.OnClickListener {
    private Activity activity;

    public SelectViewOnclickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        SelectView selectView = (SelectView) v;
        Intent i = new Intent(activity, chatHubActivity.class);

        //把聊天对象的id发送过去
        Log.d("selectView", "已点击，点击ID为" +selectView.getFromID());
        i.putExtra("userID", selectView.getFromID());
        activity.startActivity(i);
    }
}

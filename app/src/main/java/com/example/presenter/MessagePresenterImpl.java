package com.example.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.chater.MainActivity;
import com.example.chater.R;
import com.example.chater.SelectView;
import com.example.model.Entity.ChatMessage;

public class  MessagePresenterImpl implements MessagePresenter{

    private Activity activity;
    private Intent intent;

    //传入context和消息
    @Override
    public void updateUi(Activity activity, Intent intent) {
        this.activity = activity;
        this.intent = intent;

        updateHomeFragment();

    }


    //更新聊天列表
    private void updateHomeFragment(){

        SelectView selectView;

        Fragment homeFragment = ((MainActivity)activity).getHomeFragment();
        if( homeFragment.isResumed() || homeFragment.isRemoving() || homeFragment == null){
            Log.d(this.getClass().getName(), "fragment已被销毁");
            return;
        }
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.homeframge_layout);
        ChatMessage chatMessage = (ChatMessage) intent.getSerializableExtra("chatMessage");
        int chatNum = linearLayout.getChildCount();
        for(int i = 0; i < chatNum; i++){
            selectView = (SelectView) linearLayout.getChildAt(i);

            //查看是否信息发送人是否已经在聊天列表中
            //有则修改提示内容
            //无则添加新的聊天
            if(selectView.getFromID().equals(chatMessage.getFromID())){
                selectView.setTitleText(chatMessage.getTextContent());
            }else{
                selectView = new SelectView(activity);

                selectView.setTitleText(chatMessage.getTextContent());
                selectView.setFromID(chatMessage.getFromID());

                linearLayout.addView(selectView);
            }
        }
    }
}

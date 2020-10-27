package com.example.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import com.example.chater.R;
import com.example.chater.SelectView;
import com.example.model.Entity.Message;

public class  MessagePresenterImpl implements MessagePresenter{

    private Context context;
    private Intent intent;

    //传入context和消息
    @Override
    public void updateUi(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;

        updateHomeFragment();

    }


    //更新聊天列表
    private void updateHomeFragment(){

        Activity activity = (Activity) context;
        SelectView selectView;

        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.homeframge_layout);
        Message message = (Message) intent.getSerializableExtra("Message");
        int chatNum = linearLayout.getChildCount();
        /*for(int i = 0; i < chatNum; i++){
            selectView = (SelectView) linearLayout.getChildAt(i);

            //查看是否信息发送人是否已经在聊天列表中
            //有则修改提示内容
            //无则添加新的聊天
            if(selectView.getFromID().equals(message.getFromID())){
                selectView.setTitleText(message.getTextContent());
            }else{
                selectView = new SelectView(activity);

                selectView.setTitleText(message.getTextContent());
                selectView.setFromID(message.getFromID());

                linearLayout.addView(selectView);

            }
        }*/
    }
}

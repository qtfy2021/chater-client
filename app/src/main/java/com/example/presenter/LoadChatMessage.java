package com.example.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chater.ChatBarFriend;
import com.example.chater.R;
import com.example.model.Dao.MessageDao;
import com.example.model.Entity.ChatMessage;
import com.example.until.UserInfoUtil;

import java.util.ArrayList;


//打开对应用户的聊天时读取聊天信息
public class LoadChatMessage implements MessagePresenter {
    @Override
    public void updateUi(Activity activity, Intent intent) {

        LinearLayout linearLayout = activity.findViewById(R.id.chat_content_hub_contentLayout);
        //读取历史聊天记录
        ArrayList<ChatMessage> messagesList= MessageDao.getInstance().selectMessageByUserIDandFromID(UserInfoUtil.getUserId(), intent.getStringExtra("userId"), activity);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lpChatbar = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        if(messagesList != null){
            for (ChatMessage chatMessage : messagesList) {
                //设置显示日期view
                TextView textView = new TextView(activity);
                textView.setText(chatMessage.getSendTime());
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(lp);

                //设置聊天内容view
                ChatBarFriend chatBarFriendView = new ChatBarFriend(activity);
                chatBarFriendView.setImageById(R.drawable.icon_);
                chatBarFriendView.setContentText(chatMessage.getTextContent());
                chatBarFriendView.setLayoutParams(lpChatbar);

                linearLayout.addView(textView);
                linearLayout.addView(chatBarFriendView);
            }

            ScrollView scrollView = activity.findViewById(R.id.chat_hub_scrollView);
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }

    }
}

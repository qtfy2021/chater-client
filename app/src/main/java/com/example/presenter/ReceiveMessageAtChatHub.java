package com.example.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chater.R;
import com.example.chater.ChatBarFriend;
import com.example.model.Entity.ChatMessage;
import com.example.model.Entity.Pictures;
import com.example.until.LoadLocalPic;

//在聊天界面实时接收信息
public class ReceiveMessageAtChatHub implements MessagePresenter {

    private Activity activity;
    private Intent intent;

    @Override
    public void updateUi(Activity activity, Intent intent) {
        this.activity = activity;
        this.intent = intent;
        receive();
    }

    private void addChatToLayout(ChatMessage chatMessage) {
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.chat_content_hub_contentLayout);
        ChatBarFriend chatBarFriend = new ChatBarFriend (activity);
        TextView timeTextView = new TextView(activity);

        chatBarFriend.setContentText(chatMessage.getTextContent());
        chatBarFriend.setHeadImg(null);

        Log.d("准备添加到好友回复", chatMessage.getTextContent() );
        timeTextView.setText(chatMessage.getSendTime());
        timeTextView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        timeTextView.setLayoutParams(lp);
        linearLayout.addView(timeTextView);
        linearLayout.addView(chatBarFriend);
    }

    private void addImgToLayout(Pictures picture) {
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.chat_content_hub_contentLayout);
        ChatBarFriend chatBarFriend = new ChatBarFriend (activity);
        chatBarFriend.setHeadImg(null);

        Bitmap bitmap = LoadLocalPic.getBitmap(LoadLocalPic.CHAT_PIC_DIR, picture.getPicId() + ".jpg", activity);
        chatBarFriend.addContentImg(bitmap);

        TextView timeTextView = new TextView(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        timeTextView.setLayoutParams(lp);
        linearLayout.addView(timeTextView);
        linearLayout.addView(chatBarFriend);

    }


    private void receive(){
        Bundle extras = intent.getExtras();
        if(extras != null){

            if (extras.containsKey("message")){
                ChatMessage chatMessage = (ChatMessage) intent.getSerializableExtra("chatMessage");
                addChatToLayout(chatMessage);

            }else if (extras.containsKey("picture")){
                Pictures picture = (Pictures) intent.getSerializableExtra("picture");
                Log.d(this.getClass().getName(), "接收到pic，准备添加pic" + picture.getPicId());
                addImgToLayout(picture);
            } else {
                Log.e("ReceiveMessageAtChatHub", "消息解析错误");
            }

        }
    }
}

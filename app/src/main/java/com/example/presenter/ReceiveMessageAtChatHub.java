package com.example.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chater.R;
import com.example.chater.ChatBarFriend;
import com.example.model.Dao.MessageDao;
import com.example.model.Entity.Message;

//在聊天界面实时接收信息
public class ReceiveMessageAtChatHub implements MessagePresenter {

    private Context context;
    private Intent intent;

    @Override
    public void updateUi(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        addChatToLayout();
    }

    private void addChatToLayout() {
        Activity activity = (Activity) context;
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.chat_content_hub_contentLayout);
        ChatBarFriend chatBarFriend = new ChatBarFriend (activity);
        TextView timeTextView = new TextView(activity);

        Message message = (Message) intent.getSerializableExtra("message");
        chatBarFriend.setContentText(message.getTextContent());
        chatBarFriend.setHeadImg(null);

        Log.d("准备添加到好友回复", message.getTextContent() );
       // SpannableString sp = new SpannableString(message.getSendTime());
        /*what：对SpannableString进行润色的各种Span；
        int：需要润色文字段开始的下标；
        end：需要润色文字段结束的下标；
        flags：决定开始和结束下标是否包含的标志位，有四个参数可选
        SPAN_INCLUSIVE_EXCLUSIVE：包括开始下标，但不包括结束下标
        SPAN_EXCLUSIVE_INCLUSIVE：不包括开始下标，但包括结束下标
        SPAN_INCLUSIVE_INCLUSIVE：既包括开始下标，又包括结束下标
        SPAN_EXCLUSIVE_EXCLUSIVE：不包括开始下标，也不包括结束下标
        这里涉及到一个重要的角色，就是各种各样的span，它决定我们要对文字的进行怎样的润饰，而后三个参数决定润饰哪些文字
        */

        //sp.setSpan();
        timeTextView.setText(message.getSendTime());
        timeTextView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        timeTextView.setLayoutParams(lp);

        //MessageDao.getInstance().addMessage(message, context);

        linearLayout.addView(timeTextView);
        linearLayout.addView(chatBarFriend);

    }
}

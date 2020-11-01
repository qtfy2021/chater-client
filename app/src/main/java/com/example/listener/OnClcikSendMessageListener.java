package com.example.listener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.model.Entity.Pictures;
import com.example.model.Entity.middleware.LoadFileVo;
import com.example.net.SendPicListHttp;
import com.example.until.AppContext;
import com.example.until.ClassFleidValueAndName;
import com.example.until.ListDeepCopyUtil;
import com.example.until.LocalThreadPools;
import com.example.until.MD5CodeCeator;
import com.example.until.TimeUntil;
import com.example.chater.ChatBarUser;
import com.example.chater.R;
import com.example.chater.chatHubActivity;
import com.example.model.Dao.MessageDao;
import com.example.model.Entity.Message;
import com.example.until.ToastUntil;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*发送聊天信息监听器*/
public class OnClcikSendMessageListener implements View.OnClickListener{

    private chatHubActivity activity;
    private List<LoadFileVo> fileVoListF;
    public OnClcikSendMessageListener(Activity activity, List<LoadFileVo> fileVoList) {
        this.activity = (chatHubActivity) activity;
        this.fileVoListF = fileVoList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        List<LoadFileVo> fileVoList = ListDeepCopyUtil.depCopy(fileVoListF);

        fileVoList.remove(0);

        Log.i("发送：", "sendButton点击");

        final String sendId = "123";
        final String recId = "123";

        EditText editText = activity.findViewById(R.id.chat_content_hub_input);
        final LinearLayout linearLayout = activity.findViewById(R.id.chat_content_hub_contentLayout);
        final String text = editText.getText().toString();
        final String FromID = activity.getIntent().getStringExtra("userID");
        final String curTime = TimeUntil.getDateTime();

        if(text.equals("") && fileVoList.size() == 0 ) {
            ToastUntil.showToast("没有要发送的内容", AppContext.getContext());
            return;
        }


        /**ui显示**/

        //时间显示
        final TextView textView = new TextView(activity);
        textView.setText(curTime);
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(lp);

        //内容显示
        LinearLayout.LayoutParams lpChatbar = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ChatBarUser chatBarUser = new ChatBarUser(activity);
        chatBarUser.setImageById(R.drawable.icon_);
        chatBarUser.setContentText(text);
        chatBarUser.setLayoutParams(lpChatbar);

        linearLayout.addView(textView);
        linearLayout.addView(chatBarUser);

        //此处编写等待ui//
        /////
        /////

        //子线程执行消息发送处理
        LocalThreadPools.getInstance(AppContext.getContext()).execute(

                new Runnable() {
                    @Override
                    public void run() {

                        int isHasPic = 0;
                        final List<Pictures> picsListInfo = new ArrayList<Pictures>();

                        Message message = new Message();
                        message.setMessageId(MD5CodeCeator.randomUUID());
                        message.setSendTime(curTime);
                        message.setTextContent(text);
                        message.setToID(recId);
                        message.setFromID(sendId);


                        //判断是否有图片
                        if(fileVoList.size() > 0 && (fileVoList.get(0).getBitmap() != null) ){

                            for (LoadFileVo loadFileVo:fileVoList
                            ) {
                                if(loadFileVo.getFile() == null){
                                    continue;
                                }

                                Pictures picture = new Pictures();
                                //picture.setDateTime();
                                String PicId = MD5CodeCeator.randomUUID();
                                picture.setPicId(PicId);
                                picture.setSendId(sendId);
                                picture.setRecId(recId);
                                picture.setMessageId(message.getMessageId());

                                picsListInfo.add(picture);

                            }

                            SendPicListHttp sendPicListHttp = new SendPicListHttp();
                            List<String> succeedPicList =
                                    sendPicListHttp.sendPic(
                                            picsListInfo,
                                            fileVoList,
                                            activity.getUpLoadService());

                            if(succeedPicList.size() > 0){
                                isHasPic = 1;
                            }
                        }

                        message.setHasPic(isHasPic);

                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("MessageType", "0");
                            Class messageClass = Class.forName("com.example.model.Entity.Message");
                            //获取属性名
                            String[] fleidsName = ClassFleidValueAndName.getFiledName(messageClass);

                            for(String fleidName: fleidsName){
                                jsonObject.put(fleidName,
                                        //获取属性对应值
                                        ClassFleidValueAndName.getFieldValueByName(fleidName, message));
                            }
                            Log.d("send json" , jsonObject.toString());
                            //发送信息

                            if ( !jsonObject.toString().equals("{}")){
                                activity.getWebSocketClientService().sendMessage(jsonObject.toString());

                                //存储到本地数据库
                                MessageDao.getInstance().addMessage(message, AppContext.getContext());

                                /**通知更新发送ui*
                                 *
                                 * */
                            }
                        } catch (ClassNotFoundException e) {
                            Log.i("发送:", "发送失败");
                            Log.i("class错误:", e.toString());
                            e.printStackTrace();
                        }catch (JSONException e){
                            Log.i("发送:", "发送失败");
                            Log.i("json错误:", e.toString());
                        }finally {


                            Log.i("OnclickSendMessage:", "发送成功");
                        }

                    }
                }
        );


    }
}

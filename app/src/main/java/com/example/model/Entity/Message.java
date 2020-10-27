package com.example.model.Entity;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

    private String messageId;
    private String fromID;
    private String toID;
    private String textContent;
    private String sendTime;

    private int isHasPic;

    public int getIsHasPic() {
        return isHasPic;
    }

    public void setIsHasPic(int isHasPic) {
        this.isHasPic = isHasPic;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public void setHasPic(int isHasPic) {
        this.isHasPic = isHasPic;
    }


    public String getTextContent() {
        return textContent;
    }

    public String getSendTime() {
        return sendTime;
    }

    public int isHasPic() {
        return isHasPic;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }
}

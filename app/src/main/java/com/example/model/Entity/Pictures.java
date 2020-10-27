package com.example.model.Entity;

import java.io.Serializable;

public class Pictures implements Serializable {
        public String getPicId() {
                return picId;
        }

        public void setPicId(String picId) {
                this.picId = picId;
        }

        public String getSendId() {
                return sendId;
        }

        public void setSendId(String sendId) {
                this.sendId = sendId;
        }

        public String getRecId() {
                return recId;
        }

        public void setRecId(String recId) {
                this.recId = recId;
        }

        public String getDateTime() {
                return dateTime;
        }

        public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
        }

        public String getMessageId() {
                return messageId;
        }

        public void setMessageId(String messageId) {
                this.messageId = messageId;
        }

        private String messageId;

        private String picId;

        private String sendId;

        private String recId;

        private String dateTime;

}

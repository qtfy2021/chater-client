package com.example.enumClass;

//默认第一个枚举常量的值为0
public enum  MessageTypeEnum {
    CHAT_MESSAGE(0),
    HEARTBEAT_PACKAGE(1),
    NOTIFICATION(3),
    ERROR_MESSAGE(4),
    DOWNLOAD_MESSAGE(5);

    //自定义成员的数值必须加入\\
    public int getTypeNum() {
        return TypeNum;
    }

    public void setTypeNum(int typeNum) {
        TypeNum = typeNum;
    }

    private int TypeNum;

    MessageTypeEnum(int Type){
        this.TypeNum = Type;
    }

    public static MessageTypeEnum getMessageTypeEnumByTypeNum(int i){
        for (MessageTypeEnum typeEnum:MessageTypeEnum.values()
             ) {
            if(typeEnum.getTypeNum() == i){
                return typeEnum;
            }
        }

        return null;
    }
}

package com.example.until;

public class UserInfoUtil {

    public static String getUserId(){
        return SharedPrefenccesUtil.getValue(
                SharedPrefenccesUtil.UserInfo.USER_INFO_FILE_NAME,
                SharedPrefenccesUtil.UserInfo.USER_ID);
    }

    public static String getUserName(){
        return SharedPrefenccesUtil.getValue(
                SharedPrefenccesUtil.UserInfo.USER_INFO_FILE_NAME,
                SharedPrefenccesUtil.UserInfo.USER_NAME);
    }

    public static String getUserToken(){
        return SharedPrefenccesUtil.getValue(
                SharedPrefenccesUtil.UserInfo.USER_INFO_FILE_NAME,
                SharedPrefenccesUtil.UserInfo.USER_TOKEN);
    }

}

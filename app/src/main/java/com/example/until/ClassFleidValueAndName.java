package com.example.until;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*反射获取class信息*/
public class ClassFleidValueAndName {
    /*传入class获取属性名数组*/
    public static String[] getFiledName(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        String[] fieldsName = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldsName[i] = fields[i].getName();
        }

        return fieldsName;
    }


    /*传入属性名和对象获取值*/
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            Log.i("获取fieldValue错误",e.toString());
            return null;
        }
    }

//      返回属性名和属性值map
//    public static HashMap<String, String> getFiledValueAndName(Class clazz){
//        Field[] fields = clazz.getDeclaredFields();
//        HashMap<String, String> map = new HashMap<String, String>() ;
//        for (int i = 0; i < fields.length; i++) {
//           map.put(fields[i].getName(), );
//        }
//    }
}

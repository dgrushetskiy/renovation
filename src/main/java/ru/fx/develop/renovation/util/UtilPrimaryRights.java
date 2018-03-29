package ru.fx.develop.renovation.util;

public class UtilPrimaryRights {

    public static String getStringToString(String value){
        String data = "Долевая собственность";
        if (value != null && value.equals(data)){
            return new String("");
        }
        return value;
    }

    public static String getStringToStringSobstven(String value){
        String data = "Собственность";
        if (value != null && value.equals(data)){
            return new String("");
        }
        return value;
    }

    public static String getStringToStringSovmSobstven(String value){
        String data = "Совместная собственность";
        if (value != null && value.equals(data)){
            return new String("");
        }
        return value;
    }

    public static String getStringToStringSobstvenMoscow(String value){
        String data = "Собственность г.Москвы";
        if (value != null && value.equals(data)){
            return new String("");
        }
        return value;
    }


}

package ru.fx.develop.renovation.util;

public class UtilDisabledPerson {

    public static String toStringYesNo(Boolean bool) {
        return toString(bool, "Да", " ", null);
    }

    public static String toString(Boolean bool, String trueString, String falseString, String nullString) {
        if (bool == null) {
            return nullString;
        }
        return bool.booleanValue() ? trueString : falseString;
    }

    public static String valueInteger(Integer value){
        if (value == null){
            return new String(" ");
        }else {
            return String.valueOf(value);
        }
    }

    public static Integer getInteger(String str){
        if (str != null){
            return Integer.parseInt(str);
        }else {
            return new Integer(0);
        }
    }

    public static Integer getBoolInteger(Boolean value){
        Integer a = (value)? 1:0;
        return a;
    }

    public static String getStringToString(String vaalue){
        String data = "Улучшение жилищных условий";
        if (vaalue != null && vaalue.equals(data)){
            return new String("Да");
        }
        return new String(" ");
    }

    public static Integer getIntegerStr(String str){
        String data = "Да";
        if (str != null && str.equals(data)){
            return new Integer(1);
        }
        return new Integer(0);
    }

    public static String getStringToStringAdaptation(String vaalue){
        String data = "Улучшение жилищных условий";
        if (vaalue == null){
            return new String(" ");
        } if (vaalue.equals(data)){
            return new String(" ");
        }
        return vaalue;
    }

    public static Integer getIntegerStrAdaptation(String str){
        String data = " ";
        if (str != null && str.equals(data)){
            return new Integer(0);
        }
        return new Integer(1);
    }

    public static Integer getIntegerFirstGroup(String str){
        String data = "1 группа инвалидности";
        if (str != null && str.equals(data)){
            return new Integer(1);
        }
        return new Integer(0);
    }

    public static Integer getIntegerSecondGroup(String str){
        String data = "2 группа инвалидности";
        if (str != null && str.equals(data)){
            return new Integer(1);
        }
        return new Integer(0);
    }

    public static Integer getIntegerThreeGroup(String str){
        String data = "3 группа инвалидности";
        if (str != null && str.equals(data)){
            return new Integer(1);
        }
        return new Integer(0);
    }

    public static Integer getIntegerFourGroup(String str){
        String data = "4 - ребенок-инвалид";
        if (str != null && str.equals(data)){
            return new Integer(1);
        }
        return new Integer(0);
    }

    public static String getIntegerInvalidGroup(Integer value){
        if (value == 4){
            return new String("4 - ребенок-инвалид");
        } if (value == 3){
            return new String("3 группа инвалидности");
        } if (value == 2){
            return new String("2 группа инвалидности");
        } if (value == 1) {
            return new String("1 группа инвалидности");
        }

        return String.valueOf(value);
    }


}

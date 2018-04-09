package ru.fx.develop.renovation.util;

import java.math.BigDecimal;

public class StringToBigDecimal {

    static final String EMPTY = "";
    static final String POINT = String.valueOf('.');
    static final String COMMA = String.valueOf(',');
    static final String POINT_AS_STRING = ".";
    static final String COMMA_AS_STRING = ",";

    public static BigDecimal toBigDecimal(final String value) {
        if (value != null) {
            boolean negativeNumber = false;

            if (value.contains("(") && value.contains(")"))
                negativeNumber = true;
            if (value.endsWith("-") || value.startsWith("-"))
                negativeNumber = true;

            String parsedValue = value.replaceAll("[^0-9\\,\\.]", EMPTY);

            if (negativeNumber)
                parsedValue = "-" + parsedValue;

            int lastPointPosition = parsedValue.lastIndexOf(POINT);
            int lastCommaPosition = parsedValue.lastIndexOf(COMMA);

            //handle '1423' case, just a simple number
            if (lastPointPosition == -1 && lastCommaPosition == -1)
                return new BigDecimal(parsedValue);
            //handle '45.3' and '4.550.000' case, only points are in the given String
            if (lastPointPosition > -1 && lastCommaPosition == -1) {
                int firstPointPosition = parsedValue.indexOf(POINT);
                if (firstPointPosition != lastPointPosition)
                    return new BigDecimal(parsedValue.replace(POINT_AS_STRING, EMPTY));
                else
                    return new BigDecimal(parsedValue);
            }
            //handle '45,3' and '4,550,000' case, only commas are in the given String
            if (lastPointPosition == -1 && lastCommaPosition > -1) {
                int firstCommaPosition = parsedValue.indexOf(COMMA);
                if (firstCommaPosition != lastCommaPosition)
                    return new BigDecimal(parsedValue.replace(COMMA_AS_STRING, EMPTY));
                else
                    return new BigDecimal(parsedValue.replace(COMMA, POINT));
            }
            //handle '2.345,04' case, points are in front of commas
            if (lastPointPosition < lastCommaPosition) {
                parsedValue = parsedValue.replace(POINT_AS_STRING, EMPTY);
                return new BigDecimal(parsedValue.replace(COMMA, POINT));
            }
            //handle '2,345.04' case, commas are in front of points
            if (lastCommaPosition < lastPointPosition) {
                parsedValue = parsedValue.replace(COMMA_AS_STRING, EMPTY);
                return new BigDecimal(parsedValue);
            }
            throw new NumberFormatException("Unexpected number format. Cannot convert '" + value + "' to BigDecimal.");
        }
        return null;
    }
}

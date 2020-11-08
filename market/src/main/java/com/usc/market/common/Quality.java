package com.usc.market.common;


public enum Quality {
    Brand_New, New_Open_Box, Like_New, Used_Very_Good, Used_Good, Used_Acceptable;

    public static boolean isValid(int quality) {
        return quality >= 0 && quality < Quality.values().length;
    }
}

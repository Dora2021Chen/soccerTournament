package com.api.soccerTournament.utility;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getGsonStr(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static void printGsonStr(Object object) {
        Gson gson = new Gson();
        if (object != null) {
            printStr(gson.toJson(object));
        } else {
            printStr("object is null");
        }
    }

    public static void printStr(String str) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dt.format(new Date())).append(": ");
        if (str != null) {
            stringBuilder.append(str);
        } else {
            stringBuilder.append("str is null");
        }

        System.out.println(stringBuilder);
    }
}

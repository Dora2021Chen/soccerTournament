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
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dt.format(new Date())).append(": ").append(gson.toJson(object));
        System.out.println(stringBuilder);
    }
}

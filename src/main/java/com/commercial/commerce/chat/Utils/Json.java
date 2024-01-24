package com.commercial.commerce.chat.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {

    public static String toJson(Object object) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(object);
    }

    public static Object fromJson(String objectJson, Class<?> classType) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(objectJson, classType);
    }

}

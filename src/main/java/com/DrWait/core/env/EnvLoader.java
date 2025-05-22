package com.DrWait.core.env;

import io.github.cdimascio.dotenv.Dotenv;

/* .env 값을 편하게 가져오기 위한 util */
public class EnvLoader {
    private static final Dotenv dotenv = Dotenv.configure()
            .filename("dev.env")
            .ignoreIfMissing()
            .load();

    public static String get(String key){
        return dotenv.get(key);
    }

    public static int getInt(String key, int defaultValue){
        try {
            return Integer.parseInt(dotenv.get(key));
        } catch (Exception e){
            return defaultValue;
        }
    }
}
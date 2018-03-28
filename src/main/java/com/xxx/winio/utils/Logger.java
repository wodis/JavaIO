package com.xxx.winio.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss] ");

    private static String date() {
        return sdf.format(new Date());
    }

    public static void i(String i) {
        System.out.println(date() + i);
    }

    public static void e(String e) {
        System.err.println(date() + e);
    }
}

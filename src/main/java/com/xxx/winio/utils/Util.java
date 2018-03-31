package com.xxx.winio.utils;

import com.xxx.winio.config.Config;

public class Util {
    public static void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isStringEmpty(String s) {
        if (s != null) s = s.trim();
        return s == null || s.length() == 0;
    }

    public static void main(String[] args) {
        System.out.println(String.format(Config.JS_TRANS, Config.PC_DESC));
    }
}

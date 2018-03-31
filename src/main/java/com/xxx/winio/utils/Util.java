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

    public static void main(String[] args) {
        System.out.println(String.format(Config.JS_TRANS, Config.PC_DESC));
    }
}

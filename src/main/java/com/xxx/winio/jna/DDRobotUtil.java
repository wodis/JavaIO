package com.xxx.winio.jna;

import com.xxx.winio.utils.Util;

public class DDRobotUtil {
    private static DD robot = DD.INSTANCE;

    public static void main(String[] args) {
        robot.DD_key(401, 1);
        robot.DD_key(401, 2);
    }

    public static void delete(int length) {
        for (int i = 0; i < length; i++) {
            robot.DD_key(214, 1);
            robot.DD_key(214, 2);
            Util.sleep(100);
        }
    }

    public static void input(String str){
        robot.DD_str(str);
    }
}

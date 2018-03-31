package com.xxx.winio.config;

public class Config {
    public static String PC_DESC = "VM";

    /**
     * IE句柄
     */
    public static String IE_EDIT = "203E4";
    public static String DEV_EDIT = "10392";

    public static long WINDOW_WAIT_TIME = 2000;
    public static long INPUT_LOW_WAIT_TIME = 300;
    public static long INPUT_UP_WAIT_TIME = 300;
    public static long INPUT_VIRTUAL_WAIT_TIME = 0;

    public static final String API_LIST = "https://loannode.renrendai.com/credit/list";

    public static final String JS_SETSK = "pgeditor.pwdSetSk(\"%s\");";
    public static final String JS_ID = "var id=%s;";
    public static final String JS_TRANS = "img_test.src='https://loannode.renrendai.com/credit/transfer?id='+id+'&pass_enc='+pass_enc+'&desc=%s';";
}

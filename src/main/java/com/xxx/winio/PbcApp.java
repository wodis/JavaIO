package com.xxx.winio;

import com.sun.jna.platform.win32.WinDef;
import com.xxx.winio.config.Config;
import com.xxx.winio.jna.User32;
import com.xxx.winio.model.Callback;
import com.xxx.winio.model.PbcPass;
import com.xxx.winio.pbc.PbcService;
import com.xxx.winio.utils.KeyBoardUtil;
import com.xxx.winio.utils.Logger;
import com.xxx.winio.utils.Util;

import java.util.List;

import static com.sun.jna.platform.win32.WinUser.*;

/**
 * Created by Administrator on 2018/3/27.
 */
public class PbcApp {
    public static void main(String[] args) throws Exception {
        int sleepTimes = 0;
        final PbcService pbc = new PbcService();
        while (true){
            Util.sleep(1000);
            List<PbcPass> list = pbc.listPassSrc();
            if (list.size() > 0){
                loop(pbc, list);
                sleepTimes = 0;
            } else {
                sleepTimes ++;
                Logger.i("No Data...");
                if (sleepTimes < 6){
                    Util.sleep(1 * 1000);
                } else {
                    Util.sleep(1000);
                    sleepTimes = 0;
                }
            }
        }
    }

    private static void loop(final PbcService pbc , List<PbcPass> list){
        for (final PbcPass pbcPass : list) {
            pbc.showIEBrowser(new Callback() {
                public boolean callback(WinDef.HWND root, WinDef.HWND current) {
                    //ie 打开后输入密码
                    Util.sleep(2000);
                    String passSrc = pbcPass.getPassSrc();
                    pbc.inputPassword(passSrc);
//                    sleep(300 * passSrc.length());
                    User32.INSTANCE.ShowWindow(root, SW_SHOWNOACTIVATE);
//                    User32.INSTANCE.ShowWindow(root, SW_MINIMIZE);
                    //打开控制台
                    pbc.showIEDevelopTool(new Callback() {
                        public boolean callback(WinDef.HWND root, WinDef.HWND current) {
                            //调用JS
                            Util.sleep(2000);
                            sendJSCmd(pbcPass);
                            //Sleep等待JS调用完成
//                            sleep(1000);
                            User32.INSTANCE.ShowWindow(root, SW_SHOWNOACTIVATE);
                            return false;
                        }
                    });
                    return false;
                }
            });
        }
    }

    private static void sendJSCmd(PbcPass pbcPass) {
        String id = "vm1";
        StringBuilder sb = new StringBuilder();
//        sb.append("pgeditor.pwdSetSk(\"").append(pbcPass.getRandomFactor()).append("\");");
        sb.append(String.format(Config.JS_SETSK, pbcPass.getRandomFactor()));
        sb.append("var pwdResult = pgeditor.pwdResultRSA();");
        sb.append("pwdResult;");
        sb.append("var img_test=new Image();");
//        sb.append("var id=").append(pbcPass.getId()).append(";");
        sb.append(String.format(Config.JS_ID, String.valueOf(pbcPass.getId())));
        sb.append("var pass_enc=encodeURIComponent(pwdResult);");
//        sb.append("img_test.src='https://loannode.renrendai.com/credit/transfer?id='+id+'&pass_enc='+pass_enc+'&desc=").append(id).append("';");
        sb.append(String.format(Config.JS_TRANS, Config.PC_DESC));
        KeyBoardUtil.sendVirtualString(sb.toString());
        KeyBoardUtil.sendVK(13);
    }
}

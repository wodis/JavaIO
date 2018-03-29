package com.xxx.winio;

import com.sun.jna.platform.KeyboardUtils;
import com.sun.jna.platform.win32.WinDef;
import com.xxx.winio.api.VKMapping;
import com.xxx.winio.jna.User32;
import com.xxx.winio.model.Callback;
import com.xxx.winio.model.PbcPass;
import com.xxx.winio.pbc.PbcService;
import com.xxx.winio.utils.KeyBoardUtil;
import com.xxx.winio.utils.Logger;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.HWND;

import java.util.ArrayList;
import java.util.List;

import static com.sun.jna.platform.win32.WinUser.*;
import static com.xxx.winio.api.VirtualKeyBoard.KeyDown;
import static com.xxx.winio.api.VirtualKeyBoard.KeyPress;
import static com.xxx.winio.api.VirtualKeyBoard.KeyUp;

/**
 * Created by Administrator on 2018/3/27.
 */
public class PbcApp {
    public static void main(String[] args) throws Exception {
        int sleepTimes = 0;
        final PbcService pbc = new PbcService();
        while (true){
            List<PbcPass> list = pbc.listPassSrc();
            if (list.size() > 0){
                loop(pbc, list);
                sleepTimes = 0;
            } else {
                sleepTimes ++;
                Logger.i("No Data...");
                if (sleepTimes < 6){
                    sleep(1 * 1000);
                } else {
                    sleep(1000);
                    sleepTimes = 0;
                }
            }
        }
    }

    private static void loop(final PbcService pbc , List<PbcPass> list){
        for (final PbcPass pbcPass : list) {
            pbc.showIE(new Callback() {
                public boolean callback(WinDef.HWND root, WinDef.HWND current) {
                    //ie 打开后输入密码
                    sleep(1000);
                    String passSrc = pbcPass.getPassSrc();
                    pbc.inputPassword(passSrc);
                    sleep(300 * passSrc.length());
//                    User32.INSTANCE.ShowWindow(root, SW_MINIMIZE);
                    //打开控制台
                    pbc.showDev(new Callback() {
                        public boolean callback(WinDef.HWND root, WinDef.HWND current) {
                            //调用JS
                            sendDevCmd2(pbcPass);
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

    private static void sendDevCmd(PbcPass pbcPass) {
        StringBuilder sb = new StringBuilder();
        sb.append("pgeditor.pwdSetSk(\"" + pbcPass.getRandomFactor() + "\");");
        sb.append("var pwdResult = pgeditor.pwdResultRSA();");
        sb.append("pwdResult;");
        KeyBoardUtil.sendVirtualString(sb.toString());
        KeyBoardUtil.sendVK(13);

        sb = new StringBuilder();
        sb.append("var img_test=new Image();");
        KeyBoardUtil.sendVirtualString(sb.toString());
        KeyBoardUtil.sendVK(13);

        sb = new StringBuilder();
        sb.append("var id=" + pbcPass.getId() + ";");
        sb.append("var pass_enc=pwdResult;");
        KeyBoardUtil.sendVirtualString(sb.toString());
        KeyBoardUtil.sendVK(13);
        KeyBoardUtil.sendVirtualString("img_test.src='https://localhost/credit/transfer?id='+id+'&pass_enc='+pass_enc;");
        KeyBoardUtil.sendVK(13);
    }

    private static void sendDevCmd2(PbcPass pbcPass) {
        StringBuilder sb = new StringBuilder();
        sb.append("pgeditor.pwdSetSk(\"" + pbcPass.getRandomFactor() + "\");");
        sb.append("var pwdResult = pgeditor.pwdResultRSA();");
        sb.append("pwdResult;");
        sb.append("var img_test=new Image();");
        sb.append("var id=" + pbcPass.getId() + ";");
        sb.append("var pass_enc=pwdResult;");
        sb.append("img_test.src='https://localhost/credit/transfer?id='+id+'&pass_enc='+pass_enc;");
        KeyBoardUtil.sendVirtualString(sb.toString());
        KeyBoardUtil.sendVK(13);
    }

    private static void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

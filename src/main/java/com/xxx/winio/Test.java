package com.xxx.winio;

import com.sun.jna.platform.win32.WinDef;
import com.xxx.winio.jna.User32;
import com.xxx.winio.model.Callback;
import com.xxx.winio.pbc.PbcService;
import com.xxx.winio.utils.Util;

import static com.sun.jna.platform.win32.WinUser.SW_SHOWNOACTIVATE;

public class Test {
    public static void main(String[] args) {
        final PbcService pbc = new PbcService();
        pbc.showIEBrowser(new Callback() {
            public boolean callback(WinDef.HWND root, WinDef.HWND current) {
                //ie 打开后输入密码
                Util.sleep(2000);
                String passSrc = "12345";
                pbc.inputPassword(passSrc);
//                    sleep(300 * passSrc.length());
                User32.INSTANCE.ShowWindow(root, SW_SHOWNOACTIVATE);
//                    User32.INSTANCE.ShowWindow(root, SW_MINIMIZE);
                //打开控制台
                pbc.showIEDevelopTool(new Callback() {
                    public boolean callback(WinDef.HWND root, WinDef.HWND current) {
                        //调用JS
                        Util.sleep(2000);
//                        sendJSCmd(pbcPass);
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

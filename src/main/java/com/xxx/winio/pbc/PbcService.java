package com.xxx.winio.pbc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.xxx.winio.jna.User32;
import com.xxx.winio.model.Callback;
import com.xxx.winio.model.PbcPass;
import com.xxx.winio.network.HttpUtil;
import com.xxx.winio.utils.KeyBoardUtil;
import com.xxx.winio.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sun.jna.platform.win32.WinUser.*;

public class PbcService {
    private final static String IE_EDIT = "19049E";
    private final static String DEV_EDIT = "C03A2";

    public void showIE(final Callback callback) {
        Logger.i("Start IE Window");
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow("IEFrame", null);
        final WinDef.HWND root = hwnd;
        User32.INSTANCE.ShowWindow(hwnd, SW_RESTORE);
        User32.INSTANCE.SetForegroundWindow(hwnd);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "Frame Tab", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "TabWindowClass", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "Shell DocObject View", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "Internet Explorer_Server", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, null, null);
//        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "ATL:Edit", null);

        User32.INSTANCE.EnumChildWindows(hwnd, new WinUser.WNDENUMPROC() {
            public boolean callback(WinDef.HWND hWnd, Pointer data) {
                String addr = hWnd.getPointer().toString().toUpperCase();
                if (addr.contains(IE_EDIT)) {
                    Logger.i("Found Out IE Window :" + hWnd.getPointer());
                    User32.INSTANCE.ShowWindow(hWnd, SW_NORMAL);        // SW_RESTORE
                    User32.INSTANCE.SetForegroundWindow(hWnd);   // bring to front
                    if (callback != null) {
                        callback.callback(root, hWnd);
                    }
                    return false;
                }
                return true;
            }
        }, null);
    }

    public void showDev(final Callback callback) {
        Logger.i("Start IE Development Tools");
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow("IEDEVTOOLS", null);
        final WinDef.HWND root = hwnd;
        User32.INSTANCE.ShowWindow(hwnd, SW_RESTORE);        // SW_RESTORE
        User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "SysTabControl32", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "#32770", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "WTL_SplitterWindow", null);

        User32.INSTANCE.EnumChildWindows(hwnd, new WinUser.WNDENUMPROC() {
            public boolean callback(WinDef.HWND hWnd, Pointer data) {
                String addr = hWnd.getPointer().toString().toUpperCase();
                if (addr.contains(DEV_EDIT)) {
                    Logger.i("Found Out IE Dev Window :" + hWnd.getPointer());
                    User32.INSTANCE.SetFocus(hWnd);
                    User32.INSTANCE.ShowWindow(hWnd, SW_NORMAL);        // SW_RESTORE
                    User32.INSTANCE.SetForegroundWindow(hWnd);   // bring to front
                    if (callback != null) {
                        callback.callback(root, hWnd);
                    }
                    return false;
                }
                return true;
            }
        }, null);
    }

    public void inputPassword(String s) {
        Logger.i("Input Password :" + s);
        try {
            KeyBoardUtil.delete(20);
            KeyBoardUtil.sendString(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PbcPass> listPassSrc() {
        List<PbcPass> list = new ArrayList<PbcPass>();
        try {
            String result = HttpUtil.get("https://loannode.renrendai.com/credit/list");
            if (result != null) {
                JSONObject object = JSON.parseObject(result);
                if (!object.containsKey("code")) {
                    return list;
                }

                int code = object.getIntValue("code");
                String data = object.getJSONObject("data").getString("list");

                if (code == 0) {
                    List<PbcPass> array = JSON.parseArray(data, PbcPass.class);
                    list.addAll(array);
                } else {
                    Logger.e("异常code:" + code);
                }
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return list;
    }

    public static void main(String[] args) {
        final PbcService pbcService = new PbcService();
        pbcService.showDev(null);
    }
}

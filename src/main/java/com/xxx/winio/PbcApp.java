package com.xxx.winio;

import com.sun.jna.platform.win32.WinDef;
import com.xxx.winio.api.VKMapping;
import com.xxx.winio.jna.User32;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.HWND;

import static com.xxx.winio.api.VirtualKeyBoard.KeyDown;
import static com.xxx.winio.api.VirtualKeyBoard.KeyPress;
import static com.xxx.winio.api.VirtualKeyBoard.KeyUp;

/**
 * Created by Administrator on 2018/3/27.
 */
public class PbcApp {
    public static void main(String[] args) throws Exception {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow("IEFrame", null);
        System.out.println(hwnd.getPointer());
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "Frame Tab", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "TabWindowClass", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "Shell DocObject View", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "Internet Explorer_Server", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "ATL:6C3C0D98", null);
        hwnd = User32.INSTANCE.FindWindowEx(hwnd, null, "ATL:Edit", null);
        System.out.println(hwnd.getPointer());

        User32.INSTANCE.ShowWindow(hwnd, 9 );        // SW_RESTORE
        User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front

        String s="123456a";
        for (int i = 0; i < s.length(); i++) {
            KeyPress(VKMapping.toScanCode(""+s.charAt(i)));
        }
    }
}

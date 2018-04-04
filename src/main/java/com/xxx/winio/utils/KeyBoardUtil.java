package com.xxx.winio.utils;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.xxx.winio.api.User32;
import com.xxx.winio.api.VKMapping;
import com.xxx.winio.config.Config;

import java.awt.event.KeyEvent;

import static com.sun.jna.platform.win32.WinUser.KEYBDINPUT.KEYEVENTF_UNICODE;
import static com.xxx.winio.api.VirtualKeyBoard.KeyDown;
import static com.xxx.winio.api.VirtualKeyBoard.KeyPress;
import static com.xxx.winio.api.VirtualKeyBoard.KeyUp;

public class KeyBoardUtil {

    public static String KBLOG = "";

    /**
     * 删除
     *
     * @param length 数量
     * @throws Exception
     */
    public static void delete(int length) throws Exception {
        for (int i = 0; i < length; i++) {
            KeyPress(VKMapping.toScanCode("Backspace"));
            Util.sleep(100);
        }
//        Util.sleep(1000);
    }

    /**
     * 使用WinIO发送字符串
     *
     * @param string
     * @throws Exception
     */
    public static void sendString(String string) throws Exception {
        KBLOG = "";
        for (int i = 0; i < string.length(); i++) {
            Character last = null, next = null;
            char c = string.charAt(i);
            if (i != 0) {
                last = string.charAt(i - 1);
            }
            if (i < string.length() - 1) {
                next = string.charAt(i + 1);
            }

            if (Character.isDigit(c)) {
                pressLowerCase(c, last, next);
            } else if (Character.isUpperCase(c)) {
                pressUpperCase(c, last, next);
            } else {
                pressLowerCase(c, last, next);
            }
        }
        Logger.i("Real Input: " + KBLOG);
    }

    private static void pressLowerCase(Character c, Character last, Character next) throws Exception {
        KBLOG += c;
        KeyPress(VKMapping.toScanCode("" + c));
        Util.sleep(Config.INPUT_LOW_WAIT_TIME);
    }

    private static void pressUpperCase(Character c, Character last, Character next) throws Exception {
        c = Character.toLowerCase(c);
        if (last == null || Character.isLowerCase(last) || Character.isDigit(last)) {
            KBLOG += "#shift_d#";
            KeyDown(User32.MapVirtualKeyA(KeyEvent.VK_SHIFT));
            Util.sleep(Config.INPUT_UP_WAIT_TIME);
        }

        KBLOG += c;
        KeyPress(VKMapping.toScanCode("" + c));
        Util.sleep(Config.INPUT_UP_WAIT_TIME);

        if (next == null || Character.isLowerCase(next) || Character.isDigit(next)) {
            KBLOG += "#shift_u#";
            KeyUp(User32.MapVirtualKeyA(KeyEvent.VK_SHIFT));
            Util.sleep(Config.INPUT_UP_WAIT_TIME);
        }
    }

    private static WinUser.INPUT input = new WinUser.INPUT();

    /**
     * 使用WIN32_API虚拟信号发送字符串
     * 效率较高
     *
     * @param string
     */
    public static void sendVirtualString(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            sendVChar(c);
            Util.sleep(Config.INPUT_VIRTUAL_WAIT_TIME);
        }
    }

    public static void sendChar(char ch) {
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki"); // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        // Press
        input.input.ki.wVk = new WinDef.WORD(Character.toUpperCase(ch)); // 0x41
        input.input.ki.dwFlags = new WinDef.DWORD(0);  // keydown

        com.xxx.winio.jna.User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());

        // Release
        input.input.ki.wVk = new WinDef.WORD(Character.toUpperCase(ch)); // 0x41
        input.input.ki.dwFlags = new WinDef.DWORD(2);  // keyup

        com.xxx.winio.jna.User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());

    }

    private static void sendVChar(char ch) {
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki"); // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
        input.input.ki.wVk = new WinDef.WORD(0); // 0x41
        input.input.ki.wScan = new WinDef.WORD(ch);
        input.input.ki.dwFlags = new WinDef.DWORD(KEYEVENTF_UNICODE);  // keydown
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        // Press

        com.xxx.winio.jna.User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    public static void sendVK(int k) {
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki"); // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
        input.input.ki.wVk = new WinDef.WORD(k); // 0x41
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.dwFlags = new WinDef.DWORD(KEYEVENTF_UNICODE);  // keydown
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        // Press

        com.xxx.winio.jna.User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    public static void main(String[] args) {
        String a = "wuJSDi8023J82asdbJ8JDBc";
        try {
            sendString(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

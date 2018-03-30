package com.xxx.winio.utils;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.xxx.winio.api.User32;
import com.xxx.winio.api.VKMapping;

import java.awt.event.KeyEvent;

import static com.sun.jna.platform.win32.WinUser.KEYBDINPUT.KEYEVENTF_UNICODE;
import static com.xxx.winio.api.VirtualKeyBoard.KeyDown;
import static com.xxx.winio.api.VirtualKeyBoard.KeyPress;
import static com.xxx.winio.api.VirtualKeyBoard.KeyUp;

public class KeyBoardUtil {
    public static void delete(int length) throws Exception {
        for (int i = 0; i < length; i++) {
            KeyPress(VKMapping.toScanCode("Backspace"));
//            Thread.sleep(1);
        }
    }

    public static void sendString(String string) throws Exception {
        for (int i = 0; i < string.length(); i++) {
            sleep(100);
            char c = string.charAt(i);
            if (Character.isDigit(c)) {
                pressLowerCase(c);
            } else if (Character.isUpperCase(c)) {
                pressUpperCase(c);
            } else {
                pressLowerCase(c);
            }
        }
    }

    private static void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String a = "AbCD123ABCqwe";
        try {
            sendString(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pressLowerCase(char c) throws Exception {
        KeyPress(VKMapping.toScanCode("" + c));
        sleep(300);
    }

    public static void pressUpperCase(char c) throws Exception {
        c = Character.toLowerCase(c);
//        sleep(1000);
        KeyDown(User32.MapVirtualKeyA(KeyEvent.VK_SHIFT));
        sleep(1000);
        KeyPress(VKMapping.toScanCode("" + c));
        sleep(1000);
        KeyUp(User32.MapVirtualKeyA(KeyEvent.VK_SHIFT));
        sleep(1000);
    }

    static WinUser.INPUT input = new WinUser.INPUT();

    public static void sendVirtualString(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            sendVChar(c);
            sleep(10);
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

    public static void sendVChar(char ch) {
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
}

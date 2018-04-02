package com.xxx.winio.model;

import com.sun.jna.platform.win32.WinDef;

public class WindowInfo {
    private WinDef.HWND hWnd;
    private String className;

    public WindowInfo(WinDef.HWND hWnd, String className) {
        this.hWnd = hWnd;
        this.className = className;
    }

    public WinDef.HWND gethWnd() {
        return hWnd;
    }

    public void sethWnd(WinDef.HWND hWnd) {
        this.hWnd = hWnd;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "WindowInfo{" +
                "hWnd=" + hWnd +
                ", className='" + className + '\'' +
                '}';
    }
}

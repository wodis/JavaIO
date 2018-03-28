package com.xxx.winio.model;

import com.sun.jna.platform.win32.WinDef;

public interface Callback {
    boolean callback(WinDef.HWND root, WinDef.HWND current) ;
}

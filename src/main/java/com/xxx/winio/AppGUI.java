package com.xxx.winio;

import com.xxx.winio.config.Config;
import com.xxx.winio.utils.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppGUI {
    private JLabel lname;
    private JTextField desc;
    private JPanel panel1;
    private JLabel pbcLable;
    private JTextField pbcEdit;
    private JLabel jsLable;
    private JTextField windowWait;
    private JTextField lowTime;
    private JTextField upTime;
    private JTextField virturalTime;
    private JButton run;
    private JButton stop;
    private JTextField jsEdit;
    private JTextPane cmd;

    private static AppGUI gui;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        gui = new AppGUI();
        JFrame frame = new JFrame("PBC Hook");
        frame.setContentPane(gui.panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        initView();
    }

    private static void initView() {
        gui.desc.setText(Config.PC_DESC);
        gui.pbcEdit.setText(Config.IE_EDIT);
        gui.jsEdit.setText(Config.DEV_EDIT);
        gui.windowWait.setText(Config.WINDOW_WAIT_TIME + "");
        gui.lowTime.setText(Config.INPUT_LOW_WAIT_TIME + "");
        gui.upTime.setText(Config.INPUT_UP_WAIT_TIME + "");
        gui.virturalTime.setText(Config.INPUT_VIRTUAL_WAIT_TIME + "");

        gui.run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (setConfig()){
                    gui.run.setEnabled(false);
                    new Thread(new Runnable() {
                        public void run() {
                            PbcApp.main(new String[0]);
                        }
                    }).start();
                }
            }
        });

        gui.stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.stop.setEnabled(false);
                PbcApp.RUN = false;
                new Thread(new Runnable() {
                    public void run() {
                        while (true){
                            if (PbcApp.BREAK){
                                gui.run.setEnabled(true);
                                gui.stop.setEnabled(true);
                                break;
                            }
                            Util.sleep(1000);
                        }
                    }
                }).start();
            }
        });
    }

    private static boolean setConfig() {
        if (Util.isStringEmpty(gui.desc.getText())
                || Util.isStringEmpty(gui.pbcEdit.getText())
                || Util.isStringEmpty(gui.jsEdit.getText())
                || Util.isStringEmpty(gui.windowWait.getText())
                || Util.isStringEmpty(gui.lowTime.getText())
                || Util.isStringEmpty(gui.upTime.getText())
                || Util.isStringEmpty(gui.virturalTime.getText())) {
            gui.cmd.setText("Can not be Empty.");
            return false;
        }


        Config.PC_DESC = gui.desc.getText();
        Config.IE_EDIT = gui.pbcEdit.getText();
        Config.DEV_EDIT = gui.jsEdit.getText();
        Config.WINDOW_WAIT_TIME = Long.parseLong(gui.windowWait.getText());
        Config.INPUT_LOW_WAIT_TIME = Long.parseLong(gui.lowTime.getText());
        Config.INPUT_UP_WAIT_TIME = Long.parseLong(gui.upTime.getText());
        Config.INPUT_VIRTUAL_WAIT_TIME = Long.parseLong(gui.virturalTime.getText());
        return true;
    }
}

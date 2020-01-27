import java.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;
import java.io.*;
import javax.swing.event.*;

import java.net.*;

public class Main {

    public static int elipCnt = 0;

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        GUI g = new GUI();
        Comms comms = new Comms();
        comms.go();
        System.out.println("x");
        while (true) {
            int t = 0;
            String linein = comms.getLine();
            System.out.println(linein);
            sendData(g, linein);
            if (g.engData.timing) {
                t = g.engData.updateTime();
            }

            g.tableLog.update(t, g.xAxis.getPos(), g.yAxis.getPos(), g.zAxis.getPos(), 6.5);
            if (g.engData.cntTest > 0) {
                g.engData.cntTest--;
                comms.testFins();
            }
        }
    }

    public static void sendData (GUI g, String line){
        System.out.println(line.length());
        if (line.contains("FinT")) {
            String elip = "";
            for (int i = 0; i < elipCnt; i++) {
                elip += ".";
            }
            g.engData._test.setText("Testing" + elip);
            elipCnt++;
            elipCnt %= 3;
        }
        else if (line.length() == 19) {
            g.engData.testing = false;
            g.engData.updateButton();

            String[] s = line.split(",");
            for (String a : s) {
                System.out.println("a");
            }
        }

        // send rgb values to gui
    }
}
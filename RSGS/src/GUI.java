import java.awt.image.BufferedImage;
import java.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;
import java.io.*;
import javax.swing.event.*;

public class GUI extends JFrame {

    // width and height
    private int w, h;
    public Axis xAxis, yAxis, zAxis;
    public Graph g;
    public EngData engData;
    public TableLog tableLog;

    public GUI (int width, int height) {
        super("Nocturne");
        setLayout(new BorderLayout());

        w = width;
        h = height;
        this.setVisible(true);

        xAxis = new Axis("X");
        yAxis = new Axis("Y");
        zAxis = new Axis("Z");
        tableLog = new TableLog();
        engData = new EngData();

        JPanel axes = new JPanel();
        axes.setLayout(new BoxLayout(axes, BoxLayout.LINE_AXIS));
        axes.add(xAxis);
        xAxis.setLayout(new BoxLayout(xAxis, BoxLayout.Y_AXIS));
        axes.add(yAxis);
        yAxis.setLayout(new BoxLayout(yAxis, BoxLayout.Y_AXIS));
        axes.add(zAxis);
        zAxis.setLayout(new BoxLayout(zAxis, BoxLayout.Y_AXIS));

        axes.add(tableLog);

        JPanel basicOperations = new JPanel();
        basicOperations.add(engData);

        add(axes, BorderLayout.CENTER);
        add(basicOperations, BorderLayout.PAGE_END);
//        JButton j = new JButton("asdasdsa");
//        add(j, BorderLayout.PAGE_START);
//        BtnL btnL = new BtnL();
//        j.addActionListener(btnL);

        pack();
    }

    public GUI () {
        new GUI(1600, 1400);
    }

    class BtnL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

}

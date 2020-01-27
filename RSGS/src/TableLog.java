import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.text.*;


public class TableLog extends JPanel {

    JScrollPane scrollPane;
    JTable table;
    Vector<String> colNames;
    DefaultTableModel model;

    public TableLog() {
        createHeader();
        setLayout(new BorderLayout());
        model = new DefaultTableModel(colNames, 0);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
//        scrollPane.getViewport().add(table);
//        table.setVisible(true);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });
        add(scrollPane, BorderLayout.CENTER);
    }

    public void createHeader() {
        colNames = createRow("Time", "Battery Voltage", "X", "Y", "Z");
    }

    public Vector<String> createRow(String t, String b, String x, String y, String z){
        Vector<String> r = new Vector<String>();
        r.add(t);
        r.add(b);
        r.add(x);
        r.add(y);
        r.add(z);
        return r;
    }

    public void addRow(Vector<String> r) {
        model.addRow(r);
    }

    public void update(int t, int x, int y, int z, double v) {
//        addRow(createRow("12ms", "1.5v", "180", "280", "803"));
        addRow(createRow(Integer.toString(t) + "ms", Double.toString(v), Integer.toString(x), Integer.toString(y), Integer.toString(z)));
    }

}

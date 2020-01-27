import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EngData extends JPanel {

    private JLabel title, batt_label, dur_label, fileName_label, imgLabel;
    private JTextField _batt, _dur;
    private JTextField _fileName;
    private String fName;
    public JButton _test, _start;
    private double batt;
    private int dur;
    private BtnL btnL = new BtnL();
    public int cntTest = 0;
    public boolean testing = false;
    public long timeS, timeL, timeC;
    public boolean timing = false;

    public EngData() {
        setLayout(new FlowLayout());
        JPanel d = new JPanel();
//        d.setLayout(new BoxLayout(d, BoxLayout.LINE_AXIS));
        title = new JLabel("Engineering Data");
        batt_label = new JLabel("Battery Voltage");
        dur_label = new JLabel("Flight Duration");
        fileName_label = new JLabel("File Path and Name");
        _batt = new JTextField(10);
        _batt.setEditable(false);
        _dur = new JTextField(8);
        _dur.setEditable(false);
        _fileName = new JTextField(30);
        _fileName.setText("output.txt");

        _test = new JButton("Test Fins");
        _test.addActionListener(btnL);
        _start = new JButton("Start");
        _start.setEnabled(false);
        _start.addActionListener(btnL);

        d.add(batt_label);
        d.add(_batt);
        d.add(dur_label);
        d.add(_dur);
        d.add(fileName_label);
        d.add(_fileName);
        d.add(_test);
        d.add(_start);

        JPanel logoPanel = new JPanel();
        try {
            ImageIcon logo = new ImageIcon(new ImageIcon("/C:/Users/jim/a/sph4u0/rsgs/RSGS/media/logo.png").getImage().getScaledInstance(400, 81, 4));
            imgLabel = new JLabel();
            imgLabel.setIcon(logo);
            logoPanel.add(imgLabel);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        add(logoPanel);
        add(title);
        add(d);

    }

    public void setBattery(double batt) {
        this.batt = batt;
        _batt.setText(Double.toString(batt) + "V");
    }

    public void setTime(long t) {
        dur = (int) t;
        _dur.setText(Integer.toString((int) t) + "ms");
    }

    public int updateTime() {
        timeC = System.currentTimeMillis();
        setTime(timeC - timeS);
        return (int) (timeC - timeS);
    }

    public String getfName() {
        return _fileName.getText();
    }

    public void updateButton() {
        _test.setEnabled(!testing);
        if (testing) {
            _test.setText("Testing Fins");
        }
        else {
            _test.setText("Test Fins");
        }
        _start.setEnabled(!testing);
    }

    class BtnL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if (cmd.equals("Test Fins")) {
                // send command to test fins
                cntTest++;
                testing = true;
                updateButton();
            }
            else if (cmd.equals("Start")) {
                // start recording data
                timeS = System.currentTimeMillis();
                timing = true;
                String f_name = getfName();
                _start.setText("Stop");
                _test.setEnabled(false);
                // open the file
            }
            else if (cmd.equals("Stop")) {
                timing = false;
            }
        }
    }
}

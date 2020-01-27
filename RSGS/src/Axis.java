import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;


public class Axis extends JPanel{

    private JLabel axisTitle;
    private JButton _pause;
    public Graph g;
    private RGBView rgbv;
    private AngleView av;
    private String axis;
    private JLabel imgLabel;
    private ImageIcon[] imgs;
    public boolean update = true;
    ArrayList<Integer> pos;
    double vel, acc, fin;
    long timeS, timeL = 0, timeC;

    private BtnL btnL;

    public Axis(String axis) {
//        setLayout(new BoxLayout(getContentPane, BoxLayout.Y_AXIS));
//        setLayout(new BoxLayout(this, ));

        pos = new ArrayList<>();
        timeS = System.currentTimeMillis();
        this.axis = axis;
        axisTitle = new JLabel(axis+"-Axis");
        axisTitle.setFont(new Font("Serif", Font.PLAIN, 20));
        imgLabel = new JLabel();
        setLayout(new GridBagLayout());
//        try {
////            img = ImageIO.read(new File("/C:/Users/jim/a/sph4u0/rsgs/pitch0.JPG"));
////            dimg = img.getScaledInstance(900, 600, Image.SCALE_SMOOTH);
////            imgLabel = new JLabel(new ImageIcon(img))
//
//            ImageIcon imageIcon = new ImageIcon(new ImageIcon("/C:/Users/jim/a/sph4u0/rsgs/pitch0.JPG").getImage().getScaledInstance(300, 200, 4));
//            imgLabel = new JLabel();
//            imgLabel.setIcon(imageIcon);
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
        getImages();
        imgLabel.setIcon(imgs[0]);
        btnL = new BtnL();

        _pause = new JButton("Pause");
        _pause.addActionListener(btnL);

        av = new AngleView();
        rgbv = new RGBView();

        g = new Graph(this.axis);

        add(axisTitle);
        add(imgLabel);
        add(g.cp);

        add(av);
        add(rgbv);

    }

    public void setRGB(int r, int g, int b) {
        rgbv.colorUpdate(r, g, b);
        long dt;
        if (timeL == 0) {
            timeL = System.currentTimeMillis();
            timeC = timeL;
            dt = 1;
        }
        else {
            timeL = timeC;
            timeC = System.currentTimeMillis();
            dt = timeC - timeL;
        }
        calculateAngle(r, g, b, dt);
        this.g.addValue(calculateMoving());
    }

    public int getPos() {
        if (!pos.isEmpty()) return pos.get(pos.size()-1);
        return 0;
    }

    public void calculateAngle(int r, int g, int b, long dt) {
        // calculate angle
        int angle = 0;
        int eps = 3;
        if (axis.equals("X")) {
            if (Math.abs(0.461*r+16.27 - b) < eps && Math.abs(0.769*r+16.0519 - g) < eps) {
                angle = (int) (4.453*r-189.897 + 0.5 + 180);
            }
            else {
                angle = (int) (2.177*g - 114.083 + 0.5);
            }
        }
        else if (axis.equals("Y")) {
            if (Math.abs(0.449*r+16.158 - b) < eps && Math.abs(0.659*r+16.0519 - g) < eps) {
                angle = (int) (4.196*r-145.604 +  0.5 + 180);
            }
            else {
                angle = (int) (2.467*g - 131.688 + 0.5);
            }
        }
        else {
            if (Math.abs(1.32*r+10.688 - b) < eps && Math.abs(0.750*r+20.588 - g) < eps) {
                angle = (int) (3.101*r-117.669 + 0.5);
            }
            else {
                angle = (int) (3.68*g - 187 + 180 + 0.5);
            }
        }

        pos.add(angle);
        if (pos.size() > this.g.n) {
            pos.remove(0);
        }

        // vel and acc
        if (pos.size() == 1) {
            vel = pos.get(0);
            acc = vel;
        }
        else if (pos.size() == 2){
            vel = (pos.get(1) * 1.0 - pos.get(0) * 1.0) / dt;
            acc = vel;
        }
        else {
            vel = (pos.get(pos.size()-1) - pos.get(pos.size()-2)) / (double) 0.001 * dt;
            acc = (pos.get(pos.size()-1) - pos.get(pos.size()-2)) / (double) 0.001 * dt - (pos.get(pos.size()-2) - pos.get(pos.size()-1)) / (0.001 * dt);
        }

        angle %= 360;

        // map to fin angle
        fin = 0;
        if (angle <= 90) fin = angle + 90;
        else if (angle <= 180) fin = angle - 90;
        else if (angle <= 270) fin = angle - 90;
        else fin = angle - 270;
        av.updateAxis(pos.get(pos.size()-1), vel, acc, fin);
    }

    public void updatePic() {
        int i = (int) Math.round(((double) getPos()) / 10) - 1;
        imgLabel.setIcon(imgs[i]);
    }

    public double calculateMoving() {
        double sum = 0.0;
        for (int i = 0; i < pos.size(); i++) {
            sum += pos.get(0);
        }
        return sum / (double) pos.size();
    }

    public String toString() {
//        return axis + "-Axis: s: "
        return axis;
    }

    public void getImages() {
        imgs = new ImageIcon[36];
        for (int i = 0; i < 36; i++) {
            imgs[i] = new ImageIcon(new ImageIcon("./media/" + axis + " " + Integer.toString(i*10) + ".PNG").getImage().getScaledInstance(300, 200, 4));
        }
    }

    class BtnL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if ("Pause".equals(cmd)) {
                _pause.setText("Continue");
                update = false;
            }
            else if ("Continue".equals(cmd)) {
                _pause.setText("Pause");
                update = true;
            }
        }
    }
}

class LabelBox extends JPanel {
    private JLabel _label;
    private JTextField _tf;

    public LabelBox(String label, String text) {
        setLayout(new BorderLayout());

        _label = new JLabel(label);
        _tf = new JTextField(text);
        _tf.setEditable(false);
        _tf.setPreferredSize(new Dimension(10, 10));

        add(_label, BorderLayout.PAGE_START);
        add(_tf, BorderLayout.CENTER);
    }

    public void updateText(String s) {
        _tf.setText(s);
    }

    public void setColor (Color c) {
        _tf.setBackground(c);
    }

    public void reverse() {
        remove(_label);
        add(_label, BorderLayout.PAGE_END);
    }

}

class AngleView extends JPanel {
    double pos = 0, vel = 0, acc = 0, fin = 0;
    private LabelBox _pos, _vel, _acc, _fin;

    public AngleView() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        _pos = new LabelBox("Position", "");
        _vel = new LabelBox("Velocity", "");
        _acc = new LabelBox("Acceleration", "");
        _fin = new LabelBox("Fin", "");
        _pos.setColor(Color.GREEN);
        _vel.setColor(Color.GREEN);
        _acc.setColor(Color.GREEN);
        _fin.setColor(Color.GREEN);

        add(_pos);
        add(_vel);
        add(_acc);
        add(_fin);
    }

    public void updateAxis(double pos, double vel, double acc, double fin) {
        this.pos = pos;
        _pos.updateText("Position: " + Double.toString(pos));
        this.vel = vel;
        _vel.updateText("Velocity: " + Double.toString(vel));
        this.acc = acc;
        _acc.updateText("Acceleration: " + Double.toString(acc));
        this.fin = fin;
        _fin.updateText("Fin Angle: " + Double.toString(fin) + " ");
    }
}

class RGBView extends JPanel {

    private int r, g, b;
    private LabelBox R, G, B;

    public RGBView() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        R = new LabelBox("Red", "");
        G = new LabelBox("Green", "");
        B = new LabelBox("Blue", "");
        R.setColor(Color.RED);
        G.setColor(Color.GREEN);
        B.setColor(Color.BLUE);
        R.reverse();
        G.reverse();
        B.reverse();

        add(R);
        add(G);
        add(B);
    }

    public void colorUpdate(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        R.updateText(Integer.toString(r));
        G.updateText(Integer.toString(g));
        B.updateText(Integer.toString(b));
    }

    public String toString() {
        return "R: " + Integer.toString(r) + " G: " + Integer.toString(g) + " B: " + Integer.toString(b);
    }

}

class WarnView extends JPanel {

    private Warning inflated, nochange;

    public WarnView() {
        setLayout((new BoxLayout(this, BoxLayout.LINE_AXIS)));

        inflated = new Warning("Inflated RGB Values ");
        nochange = new Warning("Little Change in RGB Values (10s) ");

        add(inflated);
        add(nochange);
    }

}

class Warning extends JPanel {

    private JTextField _tf;
    private String err;

    public Warning (String err){
        this.err = err;

        _tf = new JTextField(err);
        add(_tf);
        _tf.setBackground(Color.GREEN);
    }

    public void warn() {
        _tf.setBackground(Color.ORANGE);
    }

    public void okay() {
        _tf.setBackground(Color.GREEN);
    }

}
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class Comms {

    boolean scanFinished = false;
    RemoteDevice hc05device;
    String hc05Url;
    OutputStream os;
    InputStream is;
    int siz;
    byte[] b = new byte[9999];

    public static void main(String[] args) {
        try {
            new Comms().go();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void go() throws Exception {
        //btspp://001403062AF3:1;authenticate=false;encrypt=false;master=false
        //btspp://001403062A7A:1;authenticate=false;encrypt=false;master=false

        //if you know your hc05Url this is all you need:
        hc05Url = "btspp://001403062A7A:1;authenticate=false;encrypt=false;master=false";
        StreamConnection streamConnection = (StreamConnection) Connector.open(hc05Url);
        os = streamConnection.openOutputStream();
        is = streamConnection.openInputStream();


        System.out.println("connected!");

//        os.write("q".getBytes()); //just send '0' to the device
//
//        byte[] b = new byte[9999];
//        int cnt = 0;
//        int siz = 0;
//        while (true) {
//            os.write("0".getBytes()); //just send '0' to the device
//            System.out.println("reading!" + cnt);
//            siz=is.read(b);
//            System.out.println("read=" + siz);
//            String s = new String (b);
//            System.out.println(s.length()+"->" + s);
//            cnt++;
//
//            if (cnt>100) break;
//        }


//        os.close();
//        is.close();
//        streamConnection.close();
    }

    public String getLine() {
        try {
            siz = is.read(b);
            System.out.println("X");
            System.out.print(siz);
            System.out.println(new String (b));
            return new String(b);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public void testFins() throws Exception{
        os.write("T".getBytes());
    }
}


//https://create.arduino.cc/projecthub/millerman4487/view-serial-monitor-over-bluetooth-fbb0e5
//https://www.teachmemicro.com/arduino-bluetooth/
//http://homepages.ihug.com.au/~npyner/Arduino/GUIDE_2BT.pdf

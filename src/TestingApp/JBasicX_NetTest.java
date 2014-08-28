/*
 * Blah
 */
package TestingApp;

import JNetX.JPacketX.JPackectX;
import JNetX.JPacketX.JPacketFieldX;
import JNetX.JPacketX.JPacketTypeX;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author RlonRyan
 * @name JLobbyX
 * @date Jan 23, 2014
 *
 */
/**
 *
 * @author Ryan
 */
public class JBasicX_NetTest {

    public final int port;

    public final List<InetAddress> clients;

    static int x = 100;
    static int y = 100;
    static DatagramPacket packet;
    static JPackectX p = new JPackectX(JPacketTypeX.LOGON);

    /**
     *
     * @param port
     */
    public JBasicX_NetTest(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public static void main(String[] args) {
        DatagramSocket socket;
        String desired, external;
        InetAddress address;
        Timer t;

        try {
            URL checkip = new URL("http://checkip.amazonaws.com/");
            BufferedReader in = new BufferedReader(new InputStreamReader(checkip.openStream()));
            desired = InetAddress.getByName("rlonryan.selfip.com").getHostAddress();
            external = in.readLine();
            Logger.getLogger("Network").log(Level.INFO, "Desired: {0}\nThis Computer: {1}", new Object[]{desired, external});
            address = (desired.equalsIgnoreCase(external) ? InetAddress.getLoopbackAddress() : InetAddress.getByName(desired));
            Logger.getLogger("Network").log(Level.INFO, "Using: {0}", address.getHostAddress());

        } catch (IOException e) {
            Logger.getLogger("Network").log(Level.SEVERE, "Unable to resolve host address!");
            return;
        }

        try {
            socket = new DatagramSocket(7655, address);
            socket.send(p.convert(address, 7654, (byte) 0, null, (byte) 0));
            t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    p = new JPackectX(JPacketTypeX.UPDATE);
                    p.set(JPacketFieldX.XPOS, JPackectX.toBytes(x));
                    p.set(JPacketFieldX.YPOS, JPackectX.toBytes(y));
                    x += 10;
                    y += 10;
                    DatagramPacket pack = p.convert(address, 7654, (byte) 1, null, (byte) 1);
                    System.out.println(JPackectX.packetToString(pack));
                    //System.out.println(Arrays.toString(pack.getData()));
                    try {
                        socket.send(pack);
                    } catch (IOException e) {
                    }
                }
            }, 1000, 100);

            socket.setSoTimeout(10000);

            while (x < 540) {
                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
            }

            p = new JPackectX(JPacketTypeX.LOGOFF);
            socket.send(p.convert(address, 7654, (byte) 0, null, (byte) 0));
            socket.close();
            t.cancel();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //new JBasicX_NetTest(7654).start();
    }
}

/*
 * Blah
 */
package TestingApp;

import JNetX.JClientX;
import JNetX.JNetworkListenerX;
import JNetX.JPacketX.JPackectX;
import JNetX.JPacketX.JPacketFieldX;
import JNetX.JPacketX.JPacketTypeX;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
public class JBasicX_NetTest extends Thread implements JNetworkListenerX {

    /**
     *
     */
    public final int port;

    /**
     *
     */
    public final List<InetAddress> clients;

    /**
     * 7654 7655
     */
    @Override
    public void run() {
        JClientX clientX;

        try {
            clientX = new JClientX(InetAddress.getByName(""), 7654);
        } catch (UnknownHostException e) {
            return;
        }

        clientX.addListener(this);
        clientX.start();

        String cmd = "";
        Scanner reader = new Scanner(System.in);

        while (!cmd.equalsIgnoreCase("quit")) {
            cmd = reader.nextLine();
            JPackectX packet;

            switch (cmd.length()) {
                case 1:
                    packet = new JPackectX(JPacketTypeX.UPDATE);
                    packet.set(JPacketFieldX.KEY, cmd.getBytes());
                    System.out.println(Arrays.toString(cmd.getBytes()));
                    clientX.sendPacket(packet);
                    break;
                default:
                    packet = new JPackectX(JPacketTypeX.MESSAGE);
                    packet.set(JPacketFieldX.MESSAGE, cmd.getBytes());
                    clientX.sendPacket(packet);
                    break;
            }
        }
    }

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
            DatagramPacket packet;
            JPackectX p = new JPackectX(JPacketTypeX.LOGON);
            
            socket.send(p.convert(address, 7654, (byte) 0, null, (byte) 0));

            // get a few quotes
            for (int i = 0; i < 5; i++) {

                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                p = new JPackectX(packet);
            }

            p = new JPackectX(JPacketTypeX.LOGOFF);
            socket.send(p.convert(address, 7654, (byte) 0, null, (byte) 0));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //new JBasicX_NetTest(7654).start();
    }
}

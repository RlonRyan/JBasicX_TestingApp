/*
 * Blah
 */
package TestingApp;

import JNetX.JClientX;
import JNetX.JNetworkListenerX;
import JNetX.JPacketX.JPackectX;
import JNetX.JPacketX.JPacketFieldX;
import JNetX.JPacketX.JPacketTypeX;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        JClientX clientX = new JClientX(InetAddress.getLoopbackAddress(), 7654);
        clientX.addListener(this);
        clientX.run();
        
        String cmd = "";
        Scanner reader = new Scanner(System.in);
        
        while(!cmd.equalsIgnoreCase("quit")) {
            cmd = reader.nextLine();
            JPackectX packet;
            
            switch(cmd.length()) {
                case 1:
                    packet = new JPackectX(JPacketTypeX.UPDATE);
                    packet.set(JPacketFieldX.KEY, cmd.getBytes());
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
        new JBasicX_NetTest(7654).start();
    }
}

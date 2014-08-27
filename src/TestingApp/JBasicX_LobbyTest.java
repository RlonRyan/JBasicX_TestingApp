/*
 * Blah
 */
package TestingApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

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
public class JBasicX_LobbyTest extends Thread {

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
        
        String externalip;
        String ip;
        
        boolean active;
        
        try {
            URL checkip = new URL("http://checkip.amazonaws.com/");
            BufferedReader in = new BufferedReader(new InputStreamReader(checkip.openStream()));
            externalip = in.readLine();
            
            ip = InetAddress.getByName("rlonryan.selfip.com").getHostAddress();
        }
        catch (IOException e) {
            return;
        }
        
        System.out.println(externalip);
        
        if (externalip.equalsIgnoreCase(ip)) {
            ip = "";
        }
        
        try (Socket socket = new Socket(ip, port)) {
            
            System.out.println("Listening on port: " + socket.getLocalPort() + ".");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner console = new Scanner(System.in);
            
            active = true;
            
            Timer t = new Timer("heartbeat");
	    t.scheduleAtFixedRate(new TimerTask() {
		@Override
		public void run() {
		    out.println("heartbeat");
		}
	    }, 0, 500);
            
            while (active) {
                String cmd = console.nextLine();
                out.println(cmd);
                System.out.println(in.readLine());
                if (cmd.equalsIgnoreCase("quit") || cmd.toLowerCase().contains("q")) {
                    active = false;
                }
            }
            
            System.out.println("Stopping Background Processes...");
            t.cancel();
            System.out.println("Timer Stopped!");
            System.out.println("");
            System.out.println("Closing...");
            out.close();
            System.out.println("Output closed!");
            in.close();
            System.out.println("Input closed!");
            socket.close();
            System.out.println("Socket closed!");
        }
        catch (IOException e) {
            System.err.println("Could not listen on port " + port + ".");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param port
     */
    public JBasicX_LobbyTest(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public static void main(String[] args) {
        new JBasicX_LobbyTest(7654).start();
    }
}

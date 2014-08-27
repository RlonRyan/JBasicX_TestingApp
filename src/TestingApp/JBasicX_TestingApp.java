package TestingApp;

import JGameEngineX.JGameEngineX;
import JNetX.JHostX;
import JNetX.JPacketX.JPackectX;
import JNetX.JPacketX.JPacketFieldX;
import JNetX.JPacketX.JPacketTypeX;
import Modes.Main_Game;
import Modes.Main_Menu;
import Modes.Pause_Menu;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RlonRyan
 * @name JBasicX_TestingApp
 * @version 1.0.0
 * @date Jan 9, 2012
 * @info Powered by JBasicX
 *
 */
public class JBasicX_TestingApp {

    public static JGameEngineX instance;
    public static final String[] options = {"Lambda Style!", "Javaaa!", "Spaaaaaace!", "Generic!", "Automated!", "Title goes here."};

    public static void main(String args[]) {

        if (instance != null) {
            return;
        }

        String mode = args.length >= 1 ? args[0] : "windowed";

        int fps = args.length >= 3 ? Integer.parseInt(args[2]) : 100;
        int width = args.length >= 4 ? Integer.parseInt(args[3]) : 640;
        int height = args.length >= 5 ? Integer.parseInt(args[4]) : 480;
        instance = new JGameEngineX("JBasicX Testing Application: " + options[new Random().nextInt(options.length)], mode, fps, width, height);

        instance.registerGameMode(new Main_Menu(instance));
        instance.registerGameMode(new Main_Game(instance));
        instance.registerGameMode(new Pause_Menu(instance));

        instance.init();

        instance.start("main_menu");

        JHostX host;
        try {
            host = new JHostX(7654);
        } catch (IOException e) {
            return;
        }
        host.start();
    }

}

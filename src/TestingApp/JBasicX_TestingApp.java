package TestingApp;

import JGameEngineX.JGameEngineX;
import Modes.Main_Game;
import Modes.Main_Menu;

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

    public static void main(String args[]) {

        if (instance != null) {
            return;
        }

        String title = args.length >= 1 ? args[0] : "JGameX";
        String mode = args.length >= 2 ? args[1] : "Windowed";
        
        int fps = args.length >= 3 ? Integer.parseInt(args[2]) : 100;
        int width = args.length >= 4 ? Integer.parseInt(args[3]) : 640;
        int height = args.length >= 5 ? Integer.parseInt(args[4]) : 480;

        instance = new JGameEngineX(title, mode, fps, width, height);
        
        instance.registerGameMode("main_menu", new Main_Menu(instance));
        instance.registerGameMode("main_game", new Main_Game(instance));
        
        instance.setGameMode("main_menu");
        
        instance.start();

    }

}

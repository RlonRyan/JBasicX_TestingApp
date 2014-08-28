/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modes;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import JIOX.JMenuX.JMenuElementX.JMenuTextElementX;
import JIOX.JMenuX.JMenuX;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 *
 * @author RlonRyan
 */
public class Main_Menu extends JGameModeX {

    private JMenuX menu;

    public Main_Menu(JGameEngineX holder) {

        super("Main_Menu", holder);

    }

    @Override
    public void init() {
        menu = new JMenuX("Main Menu", 160, 120, 320, 240);
        menu.addMenuElement(new JMenuTextElementX("Start", () -> (holder.setGameMode("main_game"))));
        menu.addMenuElement(new JMenuTextElementX("Network", () -> (holder.setGameMode("network_controller"))));
        menu.addMenuElement(new JMenuTextElementX("Remote", () -> (holder.setGameMode("remote_control"))));
        menu.addMenuElement(new JMenuTextElementX("Toggle Game Data", () -> (holder.toggleGameDataVisable())));
        menu.addMenuElement(new JMenuTextElementX("Randomize!", () -> (holder.setBackgroundColor(new Color(new Random().nextInt(256),new Random().nextInt(256),new Random().nextInt(256))))));
        menu.addMenuElement(new JMenuTextElementX("Reset!", () -> (holder.setBackgroundColor(Color.BLACK))));
        menu.addMenuElement(new JMenuTextElementX("Quit", () -> (System.exit(0))));
    }

    @Override
    public void registerBindings() {
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN, (e) -> (menu.incrementHighlight()));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_UP, (e) -> (menu.deincrementHighlight()));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, (e) -> (menu.selectMenuElement()));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_ESCAPE, (e) -> (System.exit(0)));
        bindings.bind(KeyEvent.KEY_PRESSED, (e) -> (System.out.println("Keypress: " + KeyEvent.getKeyText(((KeyEvent)e).getKeyCode()) + " detected with lambda!")));
        bindings.bind(MouseEvent.MOUSE_CLICKED, (e) -> (menu.selectMenuElement(((MouseEvent) e).getPoint())));
        bindings.bind(MouseEvent.MOUSE_CLICKED, (e) -> (System.out.println("Mousepress!")));
    }

    @Override
    public void start() {
        menu.open();
    }

    @Override
    public void update() {
        // Update...
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public void stop() {
        menu.close();
    }

    @Override
    public void paint(Graphics2D g2d) {
        menu.paint(g2d);
        if (holder.isGameDataVisible()) {
            menu.paintBounds(g2d);
        }
    }
    
    @Override
    public void paintGameData(Graphics2D g2d) {
        menu.paintBounds(g2d);
    }

}

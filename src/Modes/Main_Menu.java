/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modes;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import JIOX.JMenuX.JMenuX;
import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;

/**
 *
 * @author RlonRyan
 */
public class Main_Menu extends JGameModeX {

    private final JMenuX menu;

    public Main_Menu(JGameEngineX holder) {

        super("Main_Menu", holder);

        menu = new JMenuX("Main Menu", 160, 120, 320, 240, "Start", "1", "2", "3");
        
    }

    @Override
    public void registerBindings() {
        try {
            Method m = menu.getClass().getMethod("incrementHighlight", int.class);
            holder.bind("main_menu", KeyEvent.VK_DOWN, m, menu, -1);
            m = menu.getClass().getMethod("incrementHighlight");
            holder.bind("main_menu", MouseEvent.MOUSE_CLICKED, m, menu);
            m = menu.getClass().getMethod("selectMenuElement");
            holder.bind("main_menu", KeyEvent.KEY_PRESSED, m, menu);
            m = System.out.getClass().getMethod("println", String.class);
            holder.bind("main_menu", KeyEvent.KEY_PRESSED, m, System.out, "keypress");
        } catch (NoSuchMethodException | SecurityException e) {
            System.err.println("Fail: " + e.getLocalizedMessage());
        }
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
    }

}

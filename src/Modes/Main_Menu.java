/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modes;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import JIOX.JMenuX.JMenuX;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author RlonRyan
 */
public class Main_Menu extends JGameModeX {

    private final JMenuX menu;

    public Main_Menu(JGameEngineX holder) {

        super("Main_Menu", holder);

        menu = new JMenuX("Main Menu", 160, 120, 320, 240, "Start");
        try {
            holder.bind("Main_Menu", "KeyEvent", menu.getClass().getMethod("deincrementHighlight"), menu);
        } catch (Exception e) {

        }
    }

    @Override
    public void start() {
        menu.open();
    }

    @Override
    public void update() {

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

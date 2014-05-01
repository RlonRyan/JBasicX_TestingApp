/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modes;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import JSpriteX.JPictureSpriteX;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import javax.imageio.ImageIO;

/**
 *
 * @author RlonRyan
 */
public class Main_Game extends JGameModeX {
    
    private JPictureSpriteX hero;

    public Main_Game(JGameEngineX holder) {
        super("Main_Game", holder);
        hero = new JPictureSpriteX(null, 0, 0);
    }

    @Override
    public void registerBindings() {
        try {
            Method m = hero.getClass().getMethod("incY");
            holder.bind("main_menu", KeyEvent.KEY_PRESSED, KeyEvent.VK_UP, m, hero, 10);
            m = hero.getClass().getMethod("incY", int.class);
            holder.bind("main_menu", KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN, m, hero, -10);
            m = hero.getClass().getMethod("incX");
            holder.bind("main_menu", KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, m, hero, 10);
            m = hero.getClass().getMethod("incX", int.class);
            holder.bind("main_menu", KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, m, hero, -10);
            m = System.out.getClass().getMethod("println", String.class);
            holder.bind("main_menu", KeyEvent.KEY_PRESSED, m, System.out, "Keypress!");
        } catch (NoSuchMethodException | SecurityException e) {
            System.err.println("Fail: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void start() {
        hero.setVel(10);
    }

    @Override
    public void update() {
        hero.update();
    }

    @Override
    public void pause() {
        hero.pause();
    }

    @Override
    public void stop() {
        hero.pause();
    }

    @Override
    public void paint(Graphics2D g2d) {
        hero.draw(g2d);
    }

}

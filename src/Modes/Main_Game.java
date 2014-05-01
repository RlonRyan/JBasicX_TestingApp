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

/**
 *
 * @author RlonRyan
 */
public class Main_Game extends JGameModeX {
    
    private JPictureSpriteX hero;

    public Main_Game(JGameEngineX holder) {
        super("Main_Game", holder);
    }

    @Override
    public void init() {
        hero = new JPictureSpriteX(holder.images.getDefaultImage(), 0, 0);
    }

    @Override
    public void registerBindings() {
        try {
            Method m = hero.getClass().getMethod("incY", double.class);
            holder.bind(name, KeyEvent.KEY_PRESSED, KeyEvent.VK_UP, m, hero, -10);
            m = hero.getClass().getMethod("incY", double.class);
            holder.bind(name, KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN, m, hero, 10);
            m = hero.getClass().getMethod("incX", double.class);
            holder.bind(name, KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, m, hero, 10);
            m = hero.getClass().getMethod("incX", double.class);
            holder.bind(name, KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, m, hero, -10);
            m = holder.getClass().getMethod("setGameMode", String.class);
            holder.bind(name, KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, m, holder, "main_menu");
            m = System.out.getClass().getMethod("println", String.class);
            holder.bind(name, KeyEvent.KEY_PRESSED, m, System.out, "Keypress!");
        } catch (NoSuchMethodException | SecurityException e) {
            System.err.println("There was an issue binding the function: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void start() {
        // Nothing for now...
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

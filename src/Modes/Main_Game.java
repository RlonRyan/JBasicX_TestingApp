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
        hero = new JPictureSpriteX(holder.images.getDefaultImage(), holder.getDimensions().getCenterX(), holder.getDimensions().getCenterY());
    }

    @Override
    public void registerBindings() {
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_ESCAPE, (e) -> (holder.setGameMode("main_menu")));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_UP, (e)->(hero.incY(-10)));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN, (e)->(hero.incY(10)));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, (e)->(hero.incX(-10)));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, (e)->(hero.incX(10)));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_W, (e)->(hero.incY(-10)));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_S, (e)->(hero.incY(10)));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_A, (e)->(hero.incX(-10)));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_D, (e)->(hero.incX(10)));
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

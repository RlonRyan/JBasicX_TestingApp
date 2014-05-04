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
    }
    
    @Override
    public void start() {
        // Nothing for now...
    }
    
    @Override
    public void update() {
        
        int theta = (int) Math.toDegrees(Math.atan2(hero.getBounds().getCenterY() - holder.mouse.getY(), hero.getBounds().getCenterX() - holder.mouse.getX()));
        
        this.hero.setRotation(theta + 90);
        this.hero.setDirection(theta);
        
        this.hero.setVel(holder.mouse.getPosition().distance(hero.getPosition()) - hero.getRadius());
        
        if (holder.keyboard.isKeyDown(KeyEvent.VK_UP) || holder.keyboard.isKeyDown(KeyEvent.VK_W)) {
            hero.incY(-10);
        }
        if (holder.keyboard.isKeyDown(KeyEvent.VK_DOWN) || holder.keyboard.isKeyDown(KeyEvent.VK_S)) {
            hero.incY(10);
        }
        if (holder.keyboard.isKeyDown(KeyEvent.VK_LEFT) || holder.keyboard.isKeyDown(KeyEvent.VK_A)) {
            hero.incX(-10);
        }
        if (holder.keyboard.isKeyDown(KeyEvent.VK_RIGHT) || holder.keyboard.isKeyDown(KeyEvent.VK_D)) {
            hero.incX(10);
        }
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
        if (holder.isGameDataVisible()) {
            hero.drawBoundsTo(g2d);
        }
    }
    
}

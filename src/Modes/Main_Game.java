/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modes;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import JSpriteX.JPictureSpriteX;
import JSpriteX.JSpriteHolderX;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 *
 * @author RlonRyan
 */
public class Main_Game extends JGameModeX {

    public JPictureSpriteX hero;
    public JSpriteHolderX spriteholder;

    public Main_Game(JGameEngineX holder) {
        super("Main_Game", holder);
    }

    @Override
    public boolean init() {
        hero = new JPictureSpriteX(holder.images.getDefaultImage(), holder.getDimensions().getCenterX(), holder.getDimensions().getCenterY());
        spriteholder = new JSpriteHolderX(holder);
        spriteholder.addImage("bullet", "/Resources/Bullet.png"); // Something odd is going on here with the distros...
        return true;
    }

    @Override
    public void registerBindings() {
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_ESCAPE, (e) -> holder.setGameMode("pause_menu"));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_SPACE, (e) -> spriteholder.addSprite(JSpriteHolderX.SPRITE_BASIC, hero.getDirection(), hero.getDirection() - 90, hero.getVel() + 100, hero.getBounds().getCenterX(), hero.getBounds().getCenterY(), "bullet"));
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_B, (e) -> spriteholder.addSprite(JSpriteHolderX.SPRITE_BOUNCER, (int) (Math.random() * 360), Math.random() * 100, hero.getX(), hero.getY()));
    }

    @Override
    public void start() {
        // Start the spriteholder
        spriteholder.start();
    }

    @Override
    public void update() {
        int theta = (int) Math.toDegrees(Math.atan2(hero.getBounds().getCenterY() - holder.mouse.getY(), hero.getBounds().getCenterX() - holder.mouse.getX()));

        this.hero.setRotation(theta + 90);
        this.hero.setDirection(theta);

        this.hero.setVel(holder.mouse.getPosition().distance(new Point.Double(hero.getBounds().getCenterX(), hero.getBounds().getCenterY())) - hero.getRadius());

        hero.update();
    }

    @Override
    public void pause() {
        hero.pause();
        spriteholder.pauseAll();
        spriteholder.stop(); // Again... odd...
    }

    @Override
    public void stop() {
        hero.pause();
        spriteholder.pauseAll();
        spriteholder.stop(); // Again... odd...
    }

    @Override
    public void paint(Graphics2D g2d) {
        spriteholder.paintSprites(g2d);
        hero.paint(g2d);
    }

    @Override
    public void paintGameData(Graphics2D g2d) {
        spriteholder.paintSpriteBounds(g2d);
        hero.paintBounds(g2d);
    }

}

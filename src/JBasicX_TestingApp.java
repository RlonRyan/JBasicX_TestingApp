/**
 * JBasicX_TestingApp a JBasicX powered game.
 ** Author: RlonRyan
 ** Version Date: Jan 9, 2012
 *
 */
import JGameEngineX.*;
import JIOX.JSoundX;
import JSpriteX.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class JBasicX_TestingApp extends JGameEngineX implements Runnable {

    private JPicSpriteX2 tom;
    private JPicSpriteX2 obs;
    private JPicSpriteHolderX sprholder;
    private JSoundX musica;
    private int hits = 0;
    private int fired = 0;

    public void gameStart() {
        tom = new JPicSpriteX2(this);
        tom.noScale();
        tom.setPosition(this.getGameWinWidthCenter() - this.tom.getWidth() / 2, this.getGameWinHeight() - this.tom.getHeight());
        obs = new JPicSpriteX2(this);
        obs.noScale();
        obs.setPosition(this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
        obs.setSpeed(new Random().nextInt(5) + 6);
        int i = new Random().nextInt(361);
        obs.setRotation(i - 90);
        obs.setDirection(i);
        sprholder = new JPicSpriteHolderX(this);
        musica = new JSoundX();
        this.setGameStatus(this.gamerunning);
    }

    public void gameEnd() {
    }

    public void gameMenu() {
        if ((this.keys.isKeyDown(KeyEvent.VK_M) || this.keys.isKeyDown(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(gamerunning);
        }
    }

    public void gameUpdate() {
        if ((this.keys.isKeyDown(KeyEvent.VK_LEFT) || this.keys.isKeyDown(KeyEvent.VK_A)) && this.tom.getXPosition() > 10) {

            this.tom.incX(-4);
        }
        if ((this.keys.isKeyDown(KeyEvent.VK_RIGHT) || this.keys.isKeyDown(KeyEvent.VK_D)) && this.tom.getXPosition() < this.getGameWinWidth() - 10) {
            this.tom.incX(4);
        }
        if ((this.keys.isKeyDown(KeyEvent.VK_UP) || this.keys.isKeyDown(KeyEvent.VK_W))) {
            JPicSpriteX2 spr = new JPicSpriteX2(this);
            spr.noScale();
            spr.setPosition(this.tom.getXPosition(), this.tom.getYPosition() - this.tom.getHeight() / 2);
            this.sprholder.addSprite(-90, 10, spr.getXPosition(), spr.getYPosition());
            fired++;
        }
        if ((this.keys.isKeyDown(KeyEvent.VK_DOWN) || this.keys.isKeyDown(KeyEvent.VK_S))) {
            this.musica.play();
        }
        else {
            this.musica.pause();
        }
        if ((this.keys.isKeyDownAndRemove(KeyEvent.VK_P) || this.keys.isKeyDownAndRemove(KeyEvent.VK_SPACE))) {
            this.pausegame();
        }
        if ((this.keys.isKeyDownAndRemove(KeyEvent.VK_M) || this.keys.isKeyDownAndRemove(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(gamemenu);
        }
        if (this.obs.getXPosition() < 0) {
            this.obs.setDirection(Math.abs(180 - this.obs.getDirection()));
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setXPosition(0);
        }
        else if (this.obs.getXPosition() + this.obs.getWidth() / 2 > this.getGameWinWidth()) {
            this.obs.setDirection(Math.abs(180 - this.obs.getDirection()));
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setXPosition(this.getGameWinWidth() - (this.obs.getWidth() / 2));
        }
        else if (this.obs.getYPosition() < 0) {
            this.obs.setDirection(Math.abs(this.obs.getDirection() - 360));
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setYPosition(0);
        }
        else if (this.obs.getYPosition() + this.obs.getHeight() / 2 > this.getGameWinHeight()) {
            this.obs.setDirection(Math.abs(this.obs.getDirection() - 360));
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setYPosition(this.getGameWinHeight() - (this.obs.getHeight() / 2));
        }
        this.sprholder.updateSprites();
        this.obs.update();
        hits += this.sprholder.checkCollisionWithAndRemoveAndReturnCollisions(obs);
    }

    public void gamePaused() {
        if ((this.keys.isKeyDownAndRemove(KeyEvent.VK_P) || this.keys.isKeyDownAndRemove(KeyEvent.VK_SPACE))) {
            this.unpausegame();
        }
    }

    public void gamePaint(Graphics2D g2d) {
        this.tom.draw();
        this.obs.draw();
        this.sprholder.drawSprites();
        g2d.drawString("Hits: " + hits, this.getGameWinWidth() - 100, this.getGameWinHeight() - 10);
        g2d.drawString("Fired: " + fired, this.getGameWinWidth() - 200, this.getGameWinHeight() - 10);
    }
}


/**
 ** @author RlonRyan
 * @name JBasicX_TestingApp a JBasicX powered game.
 ** @version 1.0.2
 * @date Jan 9, 2012
 */
import JBasicX.JImageHandlerX;
import JGameEngineX.*;
import JIOX.JSoundX;
import JSpriteX.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class JBasicX_TestingApp extends JGameEngineX {

    private JPictureSpriteX tom;
    private JPictureSpriteX obs;
    private JSoundX musica;
    private int hits = 0;
    private int fired = 0;
    private int bouncers = 0;
    private JSpriteHolderX targets;
    private long lastfire = 0;

    @Override
    public void gameStart() {
        this.setDFPS(100);
        images = new JImageHandlerX();
        tom = new JPictureSpriteX(images.getDefaultImage(), this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
        tom.noScale();
        tom.setPosition(this.getGameWinWidthCenter() - this.tom.getWidth() / 2, this.getGameWinHeight() - this.tom.getHeight());
        obs = new JPictureSpriteX(images.getDefaultImage(), this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
        obs.noScale();
        obs.setPosition(this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
        obs.setSpeed(new Random().nextInt(5) * 10 + 50);
        int i = new Random().nextInt(361);
        obs.setRotation(i - 90);
        obs.setDirection(i);
        musica = new JSoundX();
        targets = new JSpriteHolderX(this);
        for (double c = images.getDefaultImage().getWidth(this) / 2; c < this.getGameWinWidth(); c += images.getDefaultImage().getWidth(this)) {
            for (double r = images.getDefaultImage().getHeight(this) / 2; r < this.getGameWinHeight() - 50; r += images.getDefaultImage().getHeight(this)) {
                targets.addSprite(1, c, r);
            }
        }
        //  Actual game status
        this.setGameStatus(JBasicX_TestingApp.gamerunning);
    }

    @Override
    public void gameEnd() {
    }

    @Override
    public void gameMenu() {
        if ((this.isKeyDownAndRemove(KeyEvent.VK_M) || this.isKeyDownAndRemove(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(gamerunning);
        }
    }

    @Override
    public void gameUpdate() {
        if ((this.isKeyDown(KeyEvent.VK_LEFT) || this.isKeyDown(KeyEvent.VK_A)) && this.tom.getXPosition() > 10) {

            this.tom.incX(-4);
        }
        if ((this.isKeyDown(KeyEvent.VK_RIGHT) || this.isKeyDown(KeyEvent.VK_D)) && this.tom.getXPosition() < this.getGameWinWidth() - 10) {
            this.tom.incX(4);
        }
        if ((this.isKeyDown(KeyEvent.VK_UP) || this.isKeyDown(KeyEvent.VK_W))) {
            if (System.currentTimeMillis() - this.lastfire > 250) {
                lastfire = System.currentTimeMillis();
                spriteholder.addSprite(JSpriteHolderX.SPRITE_BASIC, 270, 100, this.tom.getXPosition(), this.tom.getYPosition() - this.tom.getHeight() / 2);
                fired++;
            }
        }
        if (this.isKeyDown(KeyEvent.VK_B)) {
            spriteholder.addSprite(JSpriteHolderX.SPRITE_BOUNCER, new Random().nextInt(361), new Random().nextInt(5) * 10 + 10, this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
            bouncers++;
        }
        if ((this.isKeyDown(KeyEvent.VK_DOWN) || this.isKeyDown(KeyEvent.VK_S))) {
            this.musica.play();
        }
        else {
            this.musica.pause();
        }
        if ((this.isKeyDownAndRemove(KeyEvent.VK_P) || this.isKeyDownAndRemove(KeyEvent.VK_SPACE))) {
            this.pausegame();
        }
        if ((this.isKeyDownAndRemove(KeyEvent.VK_M) || this.isKeyDownAndRemove(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(gamemenu);
        }
        if (this.obs.getXPosition() < 0) {
            this.obs.setDirection(180 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setXPosition(0);
        }
        else if (this.obs.getXPosition() > this.getGameWinWidth()) {
            this.obs.setDirection(180 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setXPosition(this.getGameWinWidth());
        }
        else if (this.obs.getYPosition() < 0) {
            this.obs.setDirection(360 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setYPosition(0);
        }
        else if (this.obs.getYPosition() > this.getGameWinHeight()) {
            this.obs.setDirection(360 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setYPosition(this.getGameWinHeight());
        }
        JSpriteX temp = this.spriteholder.collidesWith(this.obs);
        if (temp != null) {
            this.obs.applyForce(temp.getSpeed(), temp.getDirection());
            if (this.obs.getSpeed() > 10) {
                this.obs.setSpeed(10);
            }
        }
        //this.sprholder.updateSprites();
        this.obs.update();
        //hits += spriteholder.checkCollisionsWith(obs);
        hits += targets.checkCollisionsWithAndRemove(obs);
    }

    @Override
    public void gamePaused() {
        if (this.spriteholder.isActive())
            this.spriteholder.stop();
        if ((this.isKeyDownAndRemove(KeyEvent.VK_P) || this.isKeyDownAndRemove(KeyEvent.VK_SPACE))) {
            this.unpausegame();
        }
    }

    @Override
    public void unpausegame() {
        this.spriteholder.start();
        super.unpausegame();
    }

    @Override
    public void gamePaint(Graphics2D g2d) {
        this.tom.draw(g2d);
        this.resetAffineTransform();
        this.obs.draw(g2d);
        this.resetAffineTransform();
        spriteholder.drawSprites(g2d);
        this.resetAffineTransform();
        targets.drawSprites(g2d);
        this.resetAffineTransform();
        g2d.drawString("Hits: " + hits, this.getGameWinWidth() - 100, this.getGameWinHeight() - 10);
        g2d.drawString("Fired: " + fired, this.getGameWinWidth() - 200, this.getGameWinHeight() - 10);
        g2d.drawString("Bouncers: " + bouncers, this.getGameWinWidth() - 300, this.getGameWinHeight() - 10);
    }

    @Override
    public void gameMenuPaint(Graphics2D g2d) {
        g2d.drawString("GAME MENU", this.getGameWinWidthCenter() - 40, this.getGameWinHeightCenter());
    }

    @Override
    public void gamePausePaint(Graphics2D g2d) {
        g2d.drawString("GAME PAUSED", this.getGameWinWidthCenter() - 10, this.getGameWinHeightCenter());
        gamePaint(g2d);
    }

    @Override
    public void gameStoppedPaint(Graphics2D g2d) {
        g2d.drawString("GAME STOPPED", this.getGameWinWidthCenter() - 40, this.getGameWinHeightCenter());
    }
}

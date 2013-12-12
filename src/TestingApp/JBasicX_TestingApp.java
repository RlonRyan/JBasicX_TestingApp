package TestingApp;

/**
 * @author RlonRyan
 * @name JBasicX_TestingApp
 * @version 1.0.0
 * @date Jan 9, 2012
 * @info Powered by JBasicX
 *
 */
import JBasicX.JImageHandlerX;
import JGameEngineX.*;
import JIOX.JMenuX.JMenuListenerX;
import JIOX.JMenuX.JMenuX;
import JIOX.JSoundX;
import JSpriteX.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class JBasicX_TestingApp extends JGameEngineX implements JMenuListenerX {

    private JMenuX mainmenu;
    private JMenuX pausemenu;
    private JSpriteHolderX targets;
    private JPictureSpriteX tom;
    private JPictureSpriteX obs;
    private JSoundX musica;
    private int hits = 0;
    private int fired = 0;
    private int bouncers = 0;
    private long lastfire = 0;

    @Override
    public void gameStart() {
        this.setDFPS(100);
        images = new JImageHandlerX();

        mainmenu = new JMenuX("Main Menu", this.getGameWinWidth() / 4, this.getGameWinHeight() / 4, this.getGameWinWidth() / 2, this.getGameWinHeight() / 2, "Start", "Reset", "Info", "Quit");
        mainmenu.addEventListener(this);

        pausemenu = new JMenuX("Pause Menu", this.getGameWinWidth() / 4, this.getGameWinHeight() / 4, this.getGameWinWidth() / 2, this.getGameWinHeight() / 2, "Resume", "Main Menu");
        pausemenu.setStyleElement("background", new Color(0, 0, 0, 200));
        pausemenu.addEventListener(this);

        tom = new JPictureSpriteX(images.getDefaultImage(), this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
        tom.noScale();

        obs = new JPictureSpriteX(images.getDefaultImage(), this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
        obs.noScale();

        musica = new JSoundX();
        targets = new JSpriteHolderX(this);

        this.spriteholder.addPicture("/Resources/Bullet.png", "bullet");

        //  Actual game status
        this.setGameStatus(GAME_STATUS.GAME_MENU);

        //  Finally setup the board
        setup();
    }

    public void setup() {
        tom.setPosition(this.getGameWinWidthCenter() - this.tom.getWidth() / 2, this.getGameWinHeight() - this.tom.getHeight());
        obs.setPosition(this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
        obs.setVel(new Random().nextInt(5) * 10 + 50);
        obs.setAccel(-10.00);
        int i = new Random().nextInt(361);
        obs.setRotation(i - 90);
        obs.setDirection(i);
        spriteholder.deleteAllSprites();

        for (double c = images.getDefaultImage().getWidth(this) / 2; c < this.getGameWinWidth(); c += images.getDefaultImage().getWidth(this)) {
            for (double r = images.getDefaultImage().getHeight(this) / 2; r < this.getGameWinHeight() - 50; r += images.getDefaultImage().getHeight(this)) {
                targets.addSprite(1, c, r);
            }
        }
    }

    @Override
    public void gameEnd() {
    }

    @Override
    public void gameMenu() {
        obs.pause();
        if ((this.isKeyDownAndRemove(KeyEvent.VK_M) || this.isKeyDownAndRemove(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(GAME_STATUS.GAME_RUNNING);
        }
        if (this.isKeyDownAndRemove(KeyEvent.VK_DOWN)) {
            this.mainmenu.incrementHighlight();
        }
        if (this.isKeyDownAndRemove(KeyEvent.VK_UP)) {
            this.mainmenu.deincrementHighlight();
        }
        if (this.isKeyDownAndRemove(KeyEvent.VK_ENTER)) {
            this.mainmenu.selectMenuElement();
        }
        /*  To Be Re-added
        mainmenu.highlightNearest(this.getGameGraphics(), (int) mouse.getPosition().getY());
        if (this.mouse.isMousedown() && !this.mouse.isMousedrag()) {
            mainmenu.selectNearest(this.getGameGraphics(), (int) mouse.getPosition().getY());
        }
        */
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
                spriteholder.addSprite(JSpriteHolderX.SPRITE_BASIC, 270, 100, this.tom.getXPosition(), this.tom.getYPosition() - this.tom.getHeight() / 2, "bullet");
                fired++;
            }
        }
        if (this.isKeyDown(KeyEvent.VK_B)) {
            spriteholder.addSprite(JSpriteHolderX.SPRITE_BOUNCER, new Random().nextInt(361), new Random().nextInt(5) * 10 + 10, this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
            bouncers++;
        }
        if ((this.isKeyDown(KeyEvent.VK_DOWN) || this.isKeyDown(KeyEvent.VK_S))) {
            this.musica.play();
        } else {
            this.musica.pause();
        }
        if ((this.isKeyDownAndRemove(KeyEvent.VK_P) || this.isKeyDownAndRemove(KeyEvent.VK_SPACE))) {
            this.pausegame();
        }
        if ((this.isKeyDownAndRemove(KeyEvent.VK_M) || this.isKeyDownAndRemove(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(GAME_STATUS.GAME_MENU);
        }
        if (this.obs.getXPosition() < 0) {
            this.obs.setDirection(180 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setXPosition(0);
        } else if (this.obs.getXPosition() > this.getGameWinWidth()) {
            this.obs.setDirection(180 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setXPosition(this.getGameWinWidth());
        } else if (this.obs.getYPosition() < 0) {
            this.obs.setDirection(360 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setYPosition(0);
        } else if (this.obs.getYPosition() > this.getGameWinHeight()) {
            this.obs.setDirection(360 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setYPosition(this.getGameWinHeight());
        }
        JSpriteX temp = this.spriteholder.collidesWithAndRemove(this.obs);
        if (temp != null) {
            this.obs.applyForce(temp.getVel(), temp.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
        }

        //this.sprholder.updateSprites();
        this.obs.update();
        //hits += spriteholder.checkCollisionsWith(obs);
        hits += targets.checkCollisionsWithAndRemove(obs);
    }

    @Override
    public void gamePaused() {
        obs.pause();
        if ((this.isKeyDownAndRemove(KeyEvent.VK_M) || this.isKeyDownAndRemove(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(GAME_STATUS.GAME_MENU);
        }
        if (this.isKeyDownAndRemove(KeyEvent.VK_SPACE) || this.isKeyDownAndRemove(KeyEvent.VK_P)) {
            this.setGameStatus(GAME_STATUS.GAME_RUNNING);
        }
        if (this.isKeyDownAndRemove(KeyEvent.VK_DOWN)) {
            this.pausemenu.incrementHighlight();
        }
        if (this.isKeyDownAndRemove(KeyEvent.VK_UP)) {
            this.pausemenu.deincrementHighlight();
        }
        if (this.isKeyDownAndRemove(KeyEvent.VK_ENTER)) {
            this.pausemenu.selectMenuElement();
        }
        if (this.isKeyDownAndRemove(KeyEvent.VK_S)) {
            this.setGameStatus(GAME_STATUS.GAME_STOPPED);
        }
        /* To be readded
        pausemenu.highlightNearest(this.getGameGraphics(), (int) mouse.getPosition().getY());
        if (this.mouse.isMousedown() && !this.mouse.isMousedrag()) {
            pausemenu.selectNearest(this.getGameGraphics(), (int) mouse.getPosition().getY());
        }
        */
    }

    @Override
    public void gamePaint(Graphics2D g2d) {
        this.tom.draw(g2d);
        this.resetAffineTransform();
        this.tom.drawBoundsTo(g2d);
        this.resetAffineTransform();
        this.obs.draw(g2d);
        this.resetAffineTransform();
        this.obs.drawBoundsTo(g2d);
        this.resetAffineTransform();
        targets.drawSpriteBounds(g2d);
        targets.drawSprites(g2d);
        this.resetAffineTransform();
        spriteholder.drawSprites(g2d);
        spriteholder.drawSpriteBounds(g2d);
        this.resetAffineTransform();
        g2d.drawString("Hits: " + hits, this.getGameWinWidth() - 100, this.getGameWinHeight() - 10);
        g2d.drawString("Fired: " + fired, this.getGameWinWidth() - 200, this.getGameWinHeight() - 10);
        g2d.drawString("Bouncers: " + bouncers, this.getGameWinWidth() - 300, this.getGameWinHeight() - 10);
    }

    @Override
    public void gameMenuPaint(Graphics2D g2d) {
        mainmenu.draw(g2d);
    }

    @Override
    public void gamePausePaint(Graphics2D g2d) {
        gamePaint(g2d);
        pausemenu.draw(g2d);
    }

    @Override
    public void gameStoppedPaint(Graphics2D g2d) {
        g2d.drawString("GAME STOPPED", this.getGameWinWidthCenter() - 40, this.getGameWinHeightCenter());
        this.drawRecurisive(g2d, this.getGameWinWidthCenter(), this.getGameWinHeightCenter(), 100);
        this.resetGraphics();
        g2d.translate(this.getGameWinWidthCenter(), this.getGameWinHeightCenter());
        g2d.scale(0.25, 0.25);
        this.drawRecurisive2(g2d, 5, 5);
    }

    @Override
    public void elementHighlighted(Object source, int... data) {
        System.out.println("Highlighted Element: " + data[0]);
    }

    @Override
    public void elementSelected(Object source, int... data) {
        switch (((JMenuX) source).getTitle()) {
            case "JBasicX Testing Application":
                switch (data[0]) {
                    case 0:
                        this.setGameStatus(GAME_STATUS.GAME_RUNNING);
                        this.spriteholder.start();
                        break;
                    case 1:
                        this.setup();
                        this.setGameStatus(GAME_STATUS.GAME_RUNNING);
                        break;
                    case 2:
                        this.setGameDataVisable(!this.isGameDataVisible());
                        break;
                    case 3:
                        this.setGameStatus(GAME_STATUS.GAME_STOPPED);
                        this.stop();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Selected Element: " + data[0]);
                }
                break;
            case "Game Paused":
                switch (data[0]) {
                    case 0:
                        this.setGameStatus(GAME_STATUS.GAME_RUNNING);
                        this.spriteholder.start();
                        break;
                    case 1:
                        this.setGameStatus(GAME_STATUS.GAME_MENU);
                }
        }
    }

    public void drawRecurisive(Graphics2D g2d, int x, int y, int i) {
        g2d.rotate(2 * Math.PI / i, x, y);
        g2d.drawLine(x, y, x + (i / 10), y + (i / 10));
        if (i - 1 > 0) {
            drawRecurisive(g2d, x + (i / 10), y + (i / 10), i - 1);
        }
    }
    
    public void drawRecurisive2(Graphics2D g2d, int sides, int depth) {
        for(int i = 0; i < sides; i++) {
            for(int ii = 0; ii < 360 / sides; ii++) {
                g2d.drawLine(0, 0, depth, depth);
                g2d.translate(depth, depth);
                g2d.rotate(Math.toRadians(1));
            }
            if (depth - 1 > 0) {
                drawRecurisive2(g2d, sides, depth - 1);
            }
        }
    }

}

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
import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameEngineX.GAME_STATUS;
import JIOX.JMenuX.JMenuListenerX;
import JIOX.JMenuX.JMenuX;
import JIOX.JSoundX;
import JNetX.JClientX;
import JNetX.JHostX;
import JNetX.JNetworkListenerX;
import JNetX.JPacketX.JPackectX;
import JNetX.JPacketX.JPacketTypeX;
import JSpriteX.JPictureSpriteX;
import JSpriteX.JSpriteHolderX;
import JSpriteX.JSpriteX;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class JBasicX_TestingApp extends JGameEngineX implements JMenuListenerX, JNetworkListenerX {

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
    //private static JHostX host;
    //private static JClientX client;

    @Override
    public void onPacket(JPackectX packet) {
        System.out.println("Packet Received:\n\t" + packet.toString());
        if (packet.getType() == JPacketTypeX.UPDATE) {
            try {
                //null for now
            }
            catch (Exception e) {
            }
        }
    }

    @Override
    public void onTerminate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onTimeout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onError() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameInit() {

        this.setDFPS(100);

        images = new JImageHandlerX();
        targets = new JSpriteHolderX(this);
        musica = new JSoundX();

        mainmenu = new JMenuX("Main Menu", (int)this.getGameWinWidth() / 4, (int)this.getGameWinHeight() / 4, (int)this.getGameWinWidth() / 2, (int)this.getGameWinHeight() / 2, "Start", "Reset", "Info", "Quit");
        pausemenu = new JMenuX("Pause Menu", (int)this.getGameWinWidth() / 4, (int)this.getGameWinHeight() / 4, (int)this.getGameWinWidth() / 2, (int)this.getGameWinHeight() / 2, "Resume", "Main Menu");

    }


    @Override
    public void gameStart() {


        mainmenu.addEventListener(this);
        pausemenu.setStyleElement("background", new Color(0, 0, 0, 200));
        pausemenu.addEventListener(this);

        spriteholder.addPicture("/Resources/Bullet.png", "bullet");

        //  Finally setup the board
        setup();

        //  Actual game status
        setGameStatus(GAME_STATUS.GAME_MENU);

    }

    public void setup() {

        spriteholder.deleteAllSprites();
        targets.deleteAllSprites();

        for (int c = 25; c + 50 < this.getGameWinWidth(); c += 50) {
            for (int r = 25; r + 100 < this.getGameWinHeight(); r += 50) {
                targets.addSprite(1, c, r);
            }
        }

        tom = new JPictureSpriteX(images.getDefaultImage(), 0, 0);
        tom.setPosition((int) (this.getCenterX() - (this.tom.getWidth() / 2)), (int)(this.getGameWinHeight() - this.tom.getHeight() - 25));

        obs = new JPictureSpriteX(images.getDefaultImage(), (int)this.getCenterX(), (int)this.getCenterY());
        obs.setVel(new Random().nextInt(5) * 10 + 50);
        obs.setAccel(-10.00);
        obs.setRotation(new Random().nextInt(361));
        obs.setDirection((int) obs.getRotation() + 90);

    }

    @Override
    public void gameEnd() {
    }

    @Override
    public void gameMenu() {
        obs.pause();
        if ((this.keyboard.isKeyDownAndRemove(KeyEvent.VK_M) || this.keyboard.isKeyDownAndRemove(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(GAME_STATUS.GAME_RUNNING);
        }
        if (this.keyboard.isKeyDownAndRemove(KeyEvent.VK_DOWN)) {
            this.mainmenu.incrementHighlight();
        }
        if (this.keyboard.isKeyDownAndRemove(KeyEvent.VK_UP)) {
            this.mainmenu.deincrementHighlight();
        }
        if (this.keyboard.isKeyDownAndRemove(KeyEvent.VK_ENTER)) {
            this.mainmenu.selectMenuElement();
        }
        /*
         * To Be Re-added
         * mainmenu.highlightNearest(this.getGameGraphics(), (int)
         * mouse.getPosition().getY());
         * if (this.mouse.isMousedown() && !this.mouse.isMousedrag()) {
         * mainmenu.selectNearest(this.getGameGraphics(), (int)
         * mouse.getPosition().getY());
         * }
         */
    }

    @Override
    public void gameUpdate() {
        if ((this.keyboard.isKeyDown(KeyEvent.VK_LEFT) || this.keyboard.isKeyDown(KeyEvent.VK_A)) && this.tom.getX() > 10) {
            this.tom.incX(-4);
            /*if (!this.host.isListening()) {
                this.client.queuePacket(new JPackectX(JPackectX.PACKET_TYPE.UPDATE, "-4"));
            }*/
        }
        if ((this.keyboard.isKeyDown(KeyEvent.VK_RIGHT) || this.keyboard.isKeyDown(KeyEvent.VK_D)) && this.tom.getX() < this.getGameWinWidth() - 10) {
            this.tom.incX(4);
            /*if (!this.host.isListening()) {
                this.client.queuePacket(new JPackectX(JPackectX.PACKET_TYPE.UPDATE, "4"));
            }*/
        }
        if ((this.keyboard.isKeyDown(KeyEvent.VK_UP) || this.keyboard.isKeyDown(KeyEvent.VK_W))) {
            if (System.currentTimeMillis() - this.lastfire > 250) {
                lastfire = System.currentTimeMillis();
                spriteholder.addSprite(JSpriteHolderX.SPRITE_BASIC, 270, 100, (int)this.tom.getX(), (int)(this.tom.getY() - this.tom.getHeight() / 2), "bullet");
                fired++;
            }
        }
        if (this.keyboard.isKeyDown(KeyEvent.VK_B)) {
            spriteholder.addSprite(JSpriteHolderX.SPRITE_BOUNCER, new Random().nextInt(361), new Random().nextInt(5) * 10 + 10, (int)this.getCenterX(), (int)this.getCenterY());
            bouncers++;
        }
        if ((this.keyboard.isKeyDown(KeyEvent.VK_DOWN) || this.keyboard.isKeyDown(KeyEvent.VK_S))) {
            this.musica.play();
        }
        else {
            this.musica.pause();
        }
        if ((this.keyboard.isKeyDownAndRemove(KeyEvent.VK_P) || this.keyboard.isKeyDownAndRemove(KeyEvent.VK_SPACE))) {
            this.pausegame();
        }
        if ((this.keyboard.isKeyDownAndRemove(KeyEvent.VK_M) || this.keyboard.isKeyDownAndRemove(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(GAME_STATUS.GAME_MENU);
        }
        if (this.obs.getX() < 0) {
            this.obs.setDirection(180 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setX(0);
        }
        else if (this.obs.getX() > this.getGameWinWidth()) {
            this.obs.setDirection(180 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setX(this.getGameWinWidth());
        }
        else if (this.obs.getY() < 0) {
            this.obs.setDirection(360 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setY(0);
        }
        else if (this.obs.getY() > this.getGameWinHeight()) {
            this.obs.setDirection(360 - this.obs.getDirection());
            this.obs.setRotation(this.obs.getDirection() - 90);
            this.obs.setY(this.getGameWinHeight());
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
        if ((this.keyboard.isKeyDownAndRemove(KeyEvent.VK_M) || this.keyboard.isKeyDownAndRemove(KeyEvent.VK_ESCAPE))) {
            this.setGameStatus(GAME_STATUS.GAME_MENU);
        }
        if (this.keyboard.isKeyDownAndRemove(KeyEvent.VK_SPACE) || this.keyboard.isKeyDownAndRemove(KeyEvent.VK_P)) {
            this.setGameStatus(GAME_STATUS.GAME_RUNNING);
        }
        if (this.keyboard.isKeyDownAndRemove(KeyEvent.VK_DOWN)) {
            this.pausemenu.incrementHighlight();
        }
        if (this.keyboard.isKeyDownAndRemove(KeyEvent.VK_UP)) {
            this.pausemenu.deincrementHighlight();
        }
        if (this.keyboard.isKeyDownAndRemove(KeyEvent.VK_ENTER)) {
            this.pausemenu.selectMenuElement();
        }
        if (this.keyboard.isKeyDownAndRemove(KeyEvent.VK_S)) {
            this.setGameStatus(GAME_STATUS.GAME_STOPPED);
        }
        /*
         * To be readded
         * pausemenu.highlightNearest(this.getGameGraphics(), (int)
         * mouse.getPosition().getY());
         * if (this.mouse.isMousedown() && !this.mouse.isMousedrag()) {
         * pausemenu.selectNearest(this.getGameGraphics(), (int)
         * mouse.getPosition().getY());
         * }
         */
    }

    @Override
    public void gamePaint(Graphics2D g2d) {
        this.tom.draw(g2d);
        this.tom.drawBoundsTo(g2d);
        this.obs.draw(g2d);
        this.obs.drawBoundsTo(g2d);
        targets.drawSprites(g2d);
        targets.drawSpriteBounds(g2d);
        spriteholder.drawSprites(g2d);
        spriteholder.drawSpriteBounds(g2d);
        g2d.drawString("Hits: " + hits, (int)this.getGameWinWidth() - 100, (int)this.getGameWinHeight() - 10);
        g2d.drawString("Fired: " + fired, (int)this.getGameWinWidth() - 200, (int)this.getGameWinHeight() - 10);
        g2d.drawString("Bouncers: " + bouncers, (int)this.getGameWinWidth() - 300, (int)this.getGameWinHeight() - 10);
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
        g2d.drawString("GAME STOPPED", (int)this.getCenterX() - 40, (int)this.getCenterY());
        this.drawRecurisive(g2d, (int)this.getCenterX(), (int)this.getCenterY(), 100);
        g2d.setTransform(new AffineTransform());
        g2d.translate(this.getCenterX(), this.getCenterY());
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
            case "Main Menu":
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
            case "Pause Menu":
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
        for (int i = 0; i < sides; i++) {
            for (int ii = 0; ii < 360 / sides; ii++) {
                g2d.drawLine(0, 0, depth, depth);
                g2d.translate(depth, depth);
                g2d.rotate(Math.toRadians(1));
            }
            if (depth - 1 > 0) {
                drawRecurisive2(g2d, sides, depth - 1);
            }
        }
    }

    public JBasicX_TestingApp(String mode) throws HeadlessException {
        super("JBasicTesting Application", mode);
    }

    public static void main(String args[]) {
        JBasicX_TestingApp temp = new JBasicX_TestingApp(args.length > 0 ? args[0] : "windowed");
        temp.init();
        temp.start();
        /*host = new JHostX(4444);
        host.start();
        try {
            client = new JClientX(InetAddress.getLocalHost(), 4444);
        }
        catch (UnknownHostException e) {
            // !!!
        }
        client.addListener(temp);
        client.start();
        */
    }

}

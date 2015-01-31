/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modes;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import JIOX.JMenuX.JMenuElementX.JMenuTextElementX;
import JIOX.JMenuX.JMenuX;
import JNetX.JHostX;
import JNetX.JPacketX.JPackectX;
import JNetX.JPacketX.JPacketFieldX;
import JNetX.JPacketX.JPacketTypeX;
import JSpriteX.JPictureSpriteX;
import JSpriteX.JSpriteHolderX;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RlonRyan
 */
public class Network_Controller extends JGameModeX {

    private JMenuX menu;
    private JHostX host;
    public JPictureSpriteX hero;
    public JSpriteHolderX spriteholder;

    public Network_Controller(JGameEngineX holder) {

        super("Network_Controller", holder);

    }

    @Override
    public boolean init() {
        hero = new JPictureSpriteX(holder.images.getDefaultImage(), holder.getDimensions().getCenterX(), holder.getDimensions().getCenterY());

        spriteholder = new JSpriteHolderX(holder);
        spriteholder.addImage("bullet", "/resources/bullet.png");

        menu = new JMenuX("Network", 160, 120, 320, 240);
        menu.addMenuElement(new JMenuTextElementX("Back", () -> (holder.setGameMode("main_menu"))));
        if (this.host == null) {
            try {
                this.host = new JHostX(7654);
                this.host.bindings.bind(JPacketTypeX.LOGON, (p) -> {
                    this.menu.addMenuElement(0, new JMenuTextElementX("Client :" + p.getAddress()));
                    this.menu.getMenuElement(0).setHighlightable(false);
                    this.menu.getMenuElement(0).setSelectable(false);
                });
                this.host.bindings.bind(JPacketTypeX.UPDATE, (p) -> {
                    if (p.hasField(JPacketFieldX.XPOS) && p.hasField(JPacketFieldX.YPOS)) {
                        this.spriteholder.addSprite(JSpriteHolderX.SPRITE_BASIC, JPackectX.getIntFromBytes(p.get(JPacketFieldX.XPOS)), JPackectX.getIntFromBytes(p.get(JPacketFieldX.YPOS)));
                    }
                    if (p.hasField(JPacketFieldX.KEY)) {
                        int key = JPackectX.getIntFromBytes(p.get(JPacketFieldX.KEY));
                        switch (key) {
                            case KeyEvent.VK_DOWN:
                                hero.incY(10);
                                break;
                            case KeyEvent.VK_UP:
                                hero.incY(-10);
                                break;
                            case KeyEvent.VK_LEFT:
                                hero.incX(-10);
                                break;
                            case KeyEvent.VK_RIGHT:
                                hero.incX(10);
                                break;
                        }
                    }
                });
                this.host.bindings.bind(JPacketTypeX.UPDATE, (p) -> {
                    if (p.hasField(JPacketFieldX.XPOS) && p.hasField(JPacketFieldX.YPOS)) {
                        Logger.getLogger("Network").log(Level.INFO, "On Update Packet: X:{0} Y:{1}", new Object[]{JPackectX.getIntFromBytes(p.get(JPacketFieldX.XPOS)), JPackectX.getIntFromBytes(p.get(JPacketFieldX.YPOS))});
                    }
                });
                this.host.bindings.bind(JPacketTypeX.LOGOFF, (p) -> {
                    this.menu.removeElement(this.menu.findElementContaining(p.getAddress().toString()));
                });
            } catch (IOException e) {
                Logger.getLogger("Network").log(Level.SEVERE, "Failed to start network connection!");
                return false;
            }
        }
        return true;
    }

    @Override
    public void registerBindings() {
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN, (e) -> menu.incrementHighlight());
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_UP, (e) -> menu.deincrementHighlight());
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, (e) -> menu.selectMenuElement());
        bindings.bind(MouseEvent.MOUSE_CLICKED, (e) -> menu.selectMenuElement(((MouseEvent) e).getPoint()));
    }

    @Override
    public void start() {
        menu.open();
        if (!this.host.isAlive()) {
            this.host.start();
        }
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
        spriteholder.paintSprites(g2d);
        holder.resetGraphics();
        menu.paint(g2d);
        hero.paint(g2d);
    }

    @Override
    public void paintGameData(Graphics2D g2d) {
        spriteholder.paintSpriteBounds(g2d);
        holder.resetGraphics();
        menu.paintBounds(g2d);
        hero.paintBounds(g2d);
    }

}

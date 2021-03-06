/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modes;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import JNetX.JClientX;
import JNetX.JPacketX.JPackectX;
import JNetX.JPacketX.JPacketFieldX;
import JNetX.JPacketX.JPacketTypeX;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RlonRyan
 */
public class Remote_Control extends JGameModeX {

    public JClientX client;

    public Remote_Control(JGameEngineX holder) {
        super("Remote_Control", holder);
    }

    @Override
    public boolean init() {
        try {
            client = new JClientX("127.0.0.1", 7654);
        } catch (IOException e) {
            Logger.getLogger(this.name).log(Level.SEVERE, "Unable to create client connection endpoint!");
            return false;
        }

        return true;
    }

    @Override
    public void registerBindings() {
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_ESCAPE, (e) -> holder.setGameMode("pause_menu"));
        bindings.bind(KeyEvent.KEY_PRESSED, (e) -> {
            JPackectX p = new JPackectX(JPacketTypeX.UPDATE);
            p.set(JPacketFieldX.KEY, JPackectX.toBytes(((KeyEvent) e).getKeyCode()));
            client.sendPacket(p);
        });
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_SPACE, (e) -> {
            JPackectX p = new JPackectX(JPacketTypeX.UPDATE);
            p.set(JPacketFieldX.MESSAGE, JPackectX.toBytes("Hello!"));
            client.sendPacket(p);
        });
    }

    @Override
    public void start() {
        // Nothing for now...
        client.sendPacket(new JPackectX(JPacketTypeX.LOGON));
    }

    @Override
    public void update() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
        client.sendPacket(new JPackectX(JPacketTypeX.LOGOFF));
    }

    @Override
    public void paint(Graphics2D g2d) {

    }

    @Override
    public void paintGameData(Graphics2D g2d) {

    }

}

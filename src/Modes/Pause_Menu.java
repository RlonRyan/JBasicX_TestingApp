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
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author RlonRyan
 */
public class Pause_Menu extends JGameModeX {

    private JMenuX menu;

    public Pause_Menu(JGameEngineX holder) {

        super("Pause_Menu", holder);

    }

    @Override
    public boolean init() {
        menu = new JMenuX("Pause Menu", 160, 120, 320, 240); // Alpha seems too high... or low... whatever is too transparent...
        menu.addMenuElement(new JMenuTextElementX("Main Menu", () -> (holder.setGameMode("main_menu"))));
        menu.addMenuElement(new JMenuTextElementX("Toggle Game Data", () -> (holder.toggleGameDataVisable())));
        menu.addMenuElement(new JMenuTextElementX("Resume", () -> (holder.previousGameMode())));
        return true;
    }

    @Override
    public void registerBindings() {
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN, (e) -> menu.incrementHighlight());
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_UP, (e) -> menu.deincrementHighlight());
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, (e) -> menu.selectMenuElement());
        bindings.bind(KeyEvent.KEY_PRESSED, KeyEvent.VK_ESCAPE, (e) -> holder.previousGameMode());
        //bindings.bind(KeyEvent.KEY_PRESSED, (e) -> System.out.println("Keypress: " + KeyEvent.getKeyText(((KeyEvent) e).getKeyCode()) + " detected with lambda!"));
        bindings.bind(MouseEvent.MOUSE_CLICKED, (e) -> menu.selectMenuElement(((MouseEvent) e).getPoint()));
        //bindings.bind(MouseEvent.MOUSE_CLICKED, (e) -> System.out.println("Mousepress!"));
    }

    @Override
    public void start() {
        menu.open();
    }

    @Override
    public void update() {
        // Update...
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
        holder.getGameMode("main_game").paint(g2d);
        if (holder.isGameDataVisible()) {
            holder.getGameMode("main_game").paintGameData(g2d); // Manual workaround for layering issue...
        }
        holder.resetGraphics();
        menu.paint(g2d);
    }

    @Override
    public void paintGameData(Graphics2D g2d) {
        menu.paintBounds(g2d);
    }

}

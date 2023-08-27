//package view;
//
//
//import controller.KeyboardInputs;
//import model.Game;
//
//import javax.swing.*;
//
//import java.awt.*;
//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;
//
//
//public class GamePanel extends JPanel {
//
//    private static final int GAME_WIDTH = 1920;
//    private static final int GAME_HEIGHT = 1080;
//
//    private Game game;
//
//    private int panelWidth;
//
//    public GamePanel(Game game) {
//
//
//
//        this.game = game;
//
//
//        setPanelSize();
//        addKeyListener(new KeyboardInputs(this));
//
//    }
//
//
//
//    private void setPanelSize() {
//        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
//        setPreferredSize(size);
//        System.out.println("size : " + GAME_WIDTH + " : " + GAME_HEIGHT);
//    }
//
//
//    public void updateGame() {
//
//    }
//
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
////        game.render(g);
//
//
//    }
//
//    public Game getGame() {
//        return game;
//    }
//}

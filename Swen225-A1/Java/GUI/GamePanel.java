package GUI;


import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class GamePanel extends JPanel {

    private static final int GAME_WIDTH = 1920;
    private static final int GAME_HEIGHT = 1080;

    private MouseInputs mouseInputs;
    private Game game;

    private int panelWidth;

    public GamePanel(Game game) {

        JLabel currentPlayer = new JLabel ("Testing");


        mouseInputs = new MouseInputs(this);
        this.game = game;


        setPanelSize();
        addKeyListener(new KeyboardInputs(this));

        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }



    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size : " + GAME_WIDTH + " : " + GAME_HEIGHT);
    }


    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);


    }

    public Game getGame() {
        return game;
    }
}

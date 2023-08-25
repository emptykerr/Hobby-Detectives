package GUI;
import main.*;

import javax.swing.*;
import java.awt.*;

public class Ongoing extends JFrame {

    public Ongoing (Game game){
        super();
    }


    private void initUI(){
        getContentPane().setLayout(new BorderLayout());
    }

    public void draw(Graphics g){
        int width = Game.GAME_WIDTH;
        int height = Game.GAME_HEIGHT;

    }

    public void update(){}

}

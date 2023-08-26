package GUI;
import main.*;

import javax.swing.*;

import inputs.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ongoing extends JFrame {

    private BufferedImage wallpaperBackground = LoadSave.GetSpriteAtlas(LoadSave.ONGOING_WALLPAPER);
    public int backgroundWidth, backgroundHeight;
    
    public Ongoing (Game game){
        super();
    }

    private void initUI(){
        getContentPane().setLayout(new BorderLayout());
    }

    public void draw(Graphics g){
        int width = Game.GAME_WIDTH;
        int height = Game.GAME_HEIGHT;

        double imgWidth = wallpaperBackground.getWidth();
        double imgHeight = wallpaperBackground.getHeight();

        float widthScale = (float) (width / imgWidth);
        float heightScale = (float) (height / imgHeight);

        backgroundWidth = (int)((wallpaperBackground.getWidth() * widthScale) * Game.SCALE);
        backgroundHeight = (int)((wallpaperBackground.getHeight() * heightScale) * Game.SCALE);
        
        g.drawImage(wallpaperBackground, Game.getPanelWidth()/2 - backgroundWidth/2, 0, backgroundWidth , backgroundHeight, null);
    }

    public void update(){}

}

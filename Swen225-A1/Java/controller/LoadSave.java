package controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    /** use this to save the sprite images
     * eg public static final String CARD_SPRITE = "card_sprite.png";
     * */
    public static final String BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_WALLPAPER = "background_menu.png";
    public static final String ONGOING_WALLPAPER = "ongoing_background.png";

    public static final String TITLE = "HobbyDetectivesTitle.png";


    public static final String BERT = "bert.png";
    public static final String MALINA = "malina.png";
    public static final String LUCILLA = "lucilla.png";
    public static final String PERCY = "percy.png";

    public static final String KNIFE = "knife.png";
    public static final String SHOVEL = "shovel.png";
    public static final String IPAD = "ipad.png";
    public static final String BROOM = "broom.png";
    public static final String SCISSORS = "scissors.png";










    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/resources/" + fileName);

        try {
            img = ImageIO.read(is);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}

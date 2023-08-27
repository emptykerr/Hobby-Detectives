//package GUI;
//import main.*;
//import inputs.LoadSave;
//
//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;
//
//public class Menu {
//
//    private Button[] buttons = new Button[2];
//    private BufferedImage backgroundImg;
//    private BufferedImage wallpaperBackground = LoadSave.GetSpriteAtlas(LoadSave.MENU_WALLPAPER);
//    private BufferedImage titleImg;
//    private int menuX, menuY, menuWidth, menuHeight;
//    private int titleX, titleY, titleWidth, titleHeight;
//
//    public int backgroundWidth, backgroundHeight;
//
//
//    public Menu(Game game) {
//        super();
//        loadButtons();
//        loadBackground();
//
//    }
//
//    private void loadBackground() {
//        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
//        titleImg = LoadSave.GetSpriteAtlas(LoadSave.TITLE);
//
//        menuWidth = (int) ((backgroundImg.getWidth()*2) * Game.SCALE );
//        menuHeight = (int) ((backgroundImg.getHeight()*2) * Game.SCALE );
//        menuX = Game.getPanelWidth() / 2 - menuWidth / 2;
//        menuY = (int) (( backgroundHeight/3));
//
//        titleWidth = (int) (titleImg.getWidth() * Game.SCALE);
//        titleHeight = (int) (titleImg.getHeight() * Game.SCALE);
//        titleX = Game.getPanelWidth() / 2 - titleWidth/2;
//        titleY = backgroundHeight/8;
//    }
//
//    private void loadButtons() {
//        buttons[0] = new Button(Game.getPanelWidth() / 2, (int) (((backgroundHeight/3) + 215 * Game.SCALE)), 0, HobbyDetectives.GameState.ONGOING);
////        buttons[1] = new Button(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, GameState.OPTIONS);
//        buttons[1] = new Button(Game.getPanelWidth() / 2, (int) (((backgroundHeight/3) + 465 * Game.SCALE)), 2, HobbyDetectives.GameState.QUIT);
//
//    }
//
//   public void rescaleButtons(){
//       buttons[0].rescale(Game.getPanelWidth() / 2, (int) (((backgroundHeight/3) + 215 * Game.SCALE)));
//       buttons[1].rescale(Game.getPanelWidth() / 2, (int) (((backgroundHeight/3) + 465 * Game.SCALE)));
//   }
//
//    public void update() {
//        rescaleButtons();
//        for(Button mb : buttons)
//            mb.update();
//    }
//
//    public void draw(Graphics g) {
//        int width = Game.GAME_WIDTH;
//        int height = Game.GAME_HEIGHT;
//
//        double imgWidth = wallpaperBackground.getWidth();
//        double imgHeight = wallpaperBackground.getHeight();
//
//        float widthScale = (float) (width / imgWidth);
//        float heightScale = (float) (height / imgHeight);
//
//        backgroundWidth = (int)((wallpaperBackground.getWidth() * widthScale) * Game.SCALE);
//        backgroundHeight = (int)((wallpaperBackground.getHeight() * heightScale) * Game.SCALE);
//
//
//        loadBackground();
//
//
//
//        g.drawImage(wallpaperBackground, Game.getPanelWidth()/2 - backgroundWidth/2, 0, backgroundWidth , backgroundHeight, null);
//        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
//        g.drawImage(titleImg, titleX, titleY, titleWidth, titleHeight, null);
//
//        for(Button mb : buttons)
//            mb.draw(g);
//
//
//    }
//
//
//
//
//    public void mouseClicked(MouseEvent e) {
//        // TODO Auto-generated method stub
//
//    }
//
//    public void mousePressed(MouseEvent e) {
//        for(Button mb : buttons) {
//            if(isIn(e,mb)) {
//                mb.setMousePressed(true);
//                break;
//            }
//        }
//
//    }
//
//    public void mouseReleased(MouseEvent e) {
//        for(Button mb : buttons) {
//            if(isIn(e,mb)) {
//                if(mb.isMousePressed()){
//                    mb.applyGameState();
//                }
//
//                break;
//            }
//        }
//        resetButtons();
//    }
//
//    private void resetButtons() {
//        for(Button mb : buttons)
//            mb.resetBools();
//
//    }
//
//    public void mouseMoved(MouseEvent e) {
//        for(Button mb : buttons)
//            mb.setMouseOver(false);
//        for(Button mb : buttons)
//            if(isIn(e, mb)) {
//                mb.setMouseOver(true);
//                break;
//            }
//
//    }
//
//    public void keyPressed(KeyEvent e) {
////        if(e.getKeyCode() == KeyEvent.VK_ENTER)
////            HobbyDetectives.setGamestate(HobbyDetectives.GameState.ONGOING);
////            break;
//
//
//    }
//
//    public void keyReleased(KeyEvent e) {
//        // TODO Auto-generated method stub
//
//    }
//
//    public void mouseDragged(MouseEvent e) {
//        // TODO Auto-generated method stub
//
//    }
//
//    public boolean isIn(MouseEvent e, Button mb) {
//        return mb.getBounds().contains(e.getX(), e.getY());
//    }
//
//}
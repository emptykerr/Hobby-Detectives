//package model;
//import main.*;
//import view.GamePanel;
//import view.GameWindow;
//import view.Ongoing;
//
//import java.awt.*;
//
//public class Game {
//
//    protected GameWindow gameWindow;
//    public GamePanel gamePanel;
//    private Menu menu;
//
//    private Ongoing ongoing;
//
//    private boolean startGame = false;
//
//
//    public final static int TILES_DEFAULT_SIZE = 32;    //number of pixels for each square. 32*32 for each square.
//    public static float SCALE = 1.0f; //use this to scale every variable
//
//    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
//    public final static int GAME_WIDTH = 1920;
//    public final static int GAME_HEIGHT = 1080;
//    public static int panelWidth = GAME_WIDTH;
//    public static int panelHeight = GAME_HEIGHT;
//
//    public Game() {
//        gamePanel = new GamePanel(this);
//
//        initClasses();
//        gameWindow = new GameWindow(gamePanel);
//        gamePanel.setFocusable(true);
//        gamePanel.requestFocus();
//
//
//    }
//
//    private void initClasses() {
////        menu = new Menu(this);
////        ongoing = new Ongoing(this);
//        ongoing.jframe.setVisible(false);
//
//
//
//
//    }
//
////
////
////    public void update() {
////        switch (state) {
////            case MENU:
////                menu.update();
////
////                break;
////            case ONGOING:
////                if(!startGame){
////                    startGame = true;
////                    gameWindow.jframe.dispose();
////                    ongoing.jframe.setVisible(true);
////                    break;
////                }
////                if(startGame && ongoing !=null) {
////                    ongoing.update();
////                }
////                break;
////            case QUIT:
////                System.exit(0);
////                break;
////
////            default:
////                System.exit(0);
////                break;
////
////        }
////
////    }
////
////    public void render(Graphics g) {
////        switch (state) {
////
////            case MENU:
////                menu.draw(g);
////                break;
////
////            case ONGOING:
////                if(startGame && ongoing != null) {
////                    ongoing.draw(g);
////                }
////                break;
////
////            default:
////                break;
////
////        }
////
////
////    }
//
//
//    public void windowFocusLost() {
//
//    }
//
//
//
//    public Menu getMenu(){
//        return menu;
//    }
//
//    public Ongoing getOngoing(){
//        return ongoing;
//    }
//
//    public static int getPanelWidth(){
//        return panelWidth;
//    }
//
//    public static int getPanelHeight(){
//        return panelHeight;
//    }
//}
//package GUI;
//
//import inputs.LoadSave;
//import main.*;
//import java.io.BufferedInputStream;
//
//
//import java.awt.image.BufferedImage;
//import java.awt.Graphics;
//import java.awt.Rectangle;
//
//public class Button {
//    public static final int B_WIDTH_DEFAULT = 140;
//    public static final int B_HEIGHT_DEFAULT = 56;
//    public int B_WIDTH = (int) ((B_WIDTH_DEFAULT*2) * Game.SCALE);
//    public int B_HEIGHT = (int) ((B_HEIGHT_DEFAULT*2) * Game.SCALE);
//    private int xPos, yPos, rowIndex, index;
//    private int xOffsetCentre = B_WIDTH / 2;
//    private HobbyDetectives.GameState state;
//    private BufferedImage[] imgs;
//    private boolean mouseOver, mousePressed;
//    private Rectangle bounds;
//
//
//    public Button(int xPos, int yPos, int rowIndex, HobbyDetectives.GameState state) {
//        this.xPos = xPos;
//        this.yPos = yPos;
//        this.rowIndex = rowIndex;
//        this.state = state;
//        loadImgs();
//        initBounds();
//    }
//    private void initBounds() {
//        bounds = new Rectangle(xPos - xOffsetCentre, yPos, B_WIDTH, B_HEIGHT);
//    }
//    private void loadImgs() {
//        imgs = new BufferedImage[3];
//        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.BUTTONS);
//        for(int i = 0 ; i < imgs.length; i++)
//            imgs[i] = temp.getSubimage(i*B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
//    }
//
//    public void draw(Graphics g) {
//        g.drawImage(imgs[index], xPos - xOffsetCentre, yPos, B_WIDTH, B_HEIGHT, null);
//    }
//
//    public void update(){
//        index = 0;
//        if(mouseOver)
//            index = 1;
//        if(mousePressed)
//            index = 2;
//
//    }
//
//    public void rescale(int x, int y){
//        B_WIDTH = (int) ((B_WIDTH_DEFAULT*2) * Game.SCALE);
//        B_HEIGHT = (int) ((B_HEIGHT_DEFAULT*2) * Game.SCALE);
//        this.xPos = x;
//        this.yPos = y;
//        xOffsetCentre = B_WIDTH / 2;
//        bounds = new Rectangle(xPos - xOffsetCentre, yPos, B_WIDTH, B_HEIGHT);
//    }
//
//
//    public boolean isMouseOver() {
//        return mouseOver;
//    }
//    public void setMouseOver(boolean mouseOver) {
//        this.mouseOver = mouseOver;
//    }
//    public boolean isMousePressed() {
//        return mousePressed;
//    }
//    public void setMousePressed(boolean mousePressed) {
//        this.mousePressed = mousePressed;
//    }
//    public Rectangle getBounds() {
//        return bounds;
//    }
//
//    public void applyGameState() {
//        HobbyDetectives.state = this.state;
//    }
//    public void resetBools() {
//        mouseOver = false;
//        mousePressed = false;
//    }
//
//    public HobbyDetectives.GameState getState() {
//        return state;
//    }
//}

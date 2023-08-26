package GUI;
import main.*;
import java.awt.Graphics;
public class Game implements Runnable {

    private HobbyDetectives.GameState state = HobbyDetectives.getGamestate();;
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private double timePerFrame = 1000000000.0 / FPS_SET;
    public int FPS_VALUE = FPS_SET;

    private Menu menu;

    private Ongoing ongoing;


    public final static int TILES_DEFAULT_SIZE = 32;    //number of pixels for each square. 32*32 for each square.
    public static float SCALE = 1.0f; //use this to scale every variable

    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = 1920;
    public final static int GAME_HEIGHT = 1080;

    public static int panelWidth = GAME_WIDTH;
    public static int panelHeight = GAME_HEIGHT;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();

    }

    private void initClasses() {
        menu = new Menu(this);
        ongoing = new Ongoing(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void update() {
        switch (state) {
            case MENU:
                menu.update();
                break;
            case ONGOING:
                ongoing.update();
                break;
            case QUIT:
                System.exit(0);
                break;

            default:
                System.exit(0);
                break;

        }

    }

    public void render(Graphics g) {
        switch (state) {

            case MENU:
                menu.draw(g);
                break;

            case ONGOING:
                ongoing.draw(g);
                break;

            default:
                break;

        }


    }

    @Override
    public void run() {
        timePerFrame = 1000000000.0 / FPS_VALUE;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();


        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;


        while (true) {
            state = HobbyDetectives.getGamestate();
             panelWidth = gamePanel.getWidth();
             panelHeight = gamePanel.getHeight();

            double scaleWidth = (double) panelWidth / GAME_WIDTH;
            double scaleHeight = (double) panelHeight / GAME_HEIGHT;

            SCALE = (float) Math.min(scaleWidth, scaleHeight);
            long currentTime = System.nanoTime();


            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // if(now - lastFrame >= timePerFrame){

            //     gamePanel.repaint();
            //     lastFrame = now;
            //     frames++;
            // }


            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS:" + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }

    }

    public void windowFocusLost() {

    }

    public void setFPS(int fps) {
        this.FPS_VALUE = fps;
        System.out.println("workin fps: " + fps);
        timePerFrame = 1000000000.0 / FPS_VALUE;
    }

    public Menu getMenu(){
        return menu;
    }

    public static int getPanelWidth(){
        return panelWidth;
    }

    public static int getPanelHeight(){
        return panelHeight;
    }
}
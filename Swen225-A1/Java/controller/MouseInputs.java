//package controller;
//import main.*;
//import view.GamePanel;
//
//
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//
//public class MouseInputs implements MouseListener, MouseMotionListener {
//    private HobbyDetectives.GameState state = HobbyDetectives.getGamestate();
//    private GamePanel gamePanel;
//
//    public MouseInputs(GamePanel gamePanel) {
//        this.gamePanel = gamePanel;
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//        state = HobbyDetectives.getGamestate();
//        switch (state) {
//            case MENU:
////                gamePanel.getGame().getMenu().mouseMoved(e);
//                break;
//
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e) {
//        state = HobbyDetectives.getGamestate();
//        switch (state) {
//            case MENU:
////                gamePanel.getGame().getMenu().mouseMoved(e);
//                break;
//
//
//            default:
//                break;
//
//        }
//
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        state = HobbyDetectives.getGamestate();
//        switch (state) {
//
//            case MENU:
////                gamePanel.getGame().getMenu().mouseMoved(e);
//                break;
//
//            default:
//                break;
//
//        }
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        state = HobbyDetectives.getGamestate();
//        switch (state) {
//            case MENU:
////                gamePanel.getGame().getMenu().mousePressed(e);
//                break;
//
//
//            default:
//                break;
//
//        }
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        state = HobbyDetectives.getGamestate();
//        switch (state) {
//            case MENU:
////                gamePanel.getGame().getMenu().mouseReleased(e);
//                System.out.println("dasda");
//
//                break;
//            case ONGOING:
//                System.out.println("dasda");
////                gamePanel.getGame().getOngoing().mouseReleased(e);
//            default:
//                break;
//
//        }
//
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
//
//
//}

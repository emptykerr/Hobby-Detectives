//package view;
//
//
//import main.HobbyDetectives;
//
//import javax.swing.*;
//import java.awt.event.*;
//
//
//public class GameWindow {
//    protected JFrame jframe;
//
//    public GameWindow(GamePanel gamePanel) {
//
//        jframe = new JFrame();
//        jframe.setTitle("Hobby Detectives");
//
//        jframe.add(gamePanel);
//
//        jframe.setResizable(true);
//        jframe.pack();
//        jframe.setLocationRelativeTo(null);
//
//        jframe.setVisible(true);
//        jframe.addWindowFocusListener(new WindowFocusListener() {
//
//            @Override
//            public void windowLostFocus(WindowEvent e) {
//                gamePanel.getGame().windowFocusLost();
//            }
//
//            @Override
//            public void windowGainedFocus(WindowEvent e) {
//            }
//        }
//        );
//
//
//
//        JButton playButton = new JButton("Play");
//        jframe.add(playButton);
//
////        playButton.addActionListener(l -> HobbyDetectives.state = HobbyDetectives.GameState.ONGOING);
//
//        // Adding exit operations
//        jframe.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                // default icon, custom title
//                int n = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to exit the game?",
//                        "Warning", JOptionPane.YES_NO_OPTION);
//                // Yes
//                if (n == 0)
//                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                    // No
//                else {
//                    jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//                }
//            }
//        });
//
//    }
//
//
//}
//Eclipse Evermore -> eclipse -> Game_Screen.java
//Kealdor created Game_Screen.java on 10-27-2023 at 8:30:39 PM
package eclipse.screens;

import eclipse.Player_Party;
import eclipse.core;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.io.IOException;

public class Game_Screen extends JFrame {

    static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    static int tab = Game_Screen.tabbedPane.getTabCount();
    static JPanel title_screen;
    static Player_Party party = null;

    Game_Screen() throws IOException {
        getContentPane().setEnabled(false);
        setMinimumSize(new Dimension(800, 600));
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(new BorderLayout(0, 0));
        tabbedPane.setSize(new Dimension(800, 600));
        tabbedPane.setBackground(Color.BLACK);

        title_screen = new title_screen();
        core.log("title screen", "title screen rendered");
        tabbedPane.insertTab("Title", null, title_screen, null, 0);

        getContentPane().add(tabbedPane);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
        device.setFullScreenWindow(this);
        core.log("Game Window", "game window rendered to fullscreen");

    }

    //public void Change_Screen(JPanel o_screen, JPanel n_screen){
    //this.remove(o_screen);
    //this.add(n_screen);
//}
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Game_Screen frame = new Game_Screen();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

//Eclipse Evermore -> eclipse.screens -> title_screen.java
//Kealdor created title_screen.java on 10-29-2023 at 10:29:40 PM
package eclipse.screens;

import eclipse.core;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class title_screen extends JPanel {

    int tab = Game_Screen.tab;
    static boolean newgame = false;
    static boolean loadgame = false;
    static JPanel load_screen;
    static JPanel new_screen;

    public title_screen() throws IOException {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("Eclipse Evermore");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        add(lblNewLabel, BorderLayout.NORTH);

        JLabel lblNewLabel_2 = new JLabel("New Game");
        lblNewLabel_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (newgame == false) {
                    try {
                        new_screen = new character_creation();
                    } catch (IOException ex) {
                        try {
                            core.log("title screen", "failed to change screen to character creation");
                        } catch (IOException ex1) {
                        }
                    }
                    if (Game_Screen.tab < 2) {
                        Game_Screen.tabbedPane.insertTab("New Game", null, new_screen, null, 1);
                        Game_Screen.tabbedPane.setSelectedIndex(1);
                    }
                    if (Game_Screen.tab >= 2) {
                        Game_Screen.tabbedPane.insertTab("New Game", null, new_screen, null, 2);
                        Game_Screen.tabbedPane.setSelectedIndex(2);
                    }
                    newgame = true;
                }
                //Game_Screen.tabbedPane.setEnabledAt(0, false);
            }
        });
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_2.setForeground(Color.WHITE);
        add(lblNewLabel_2, BorderLayout.WEST);

        JLabel lblNewLabel_3 = new JLabel("Load Game");
        lblNewLabel_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    load_screen = new load_game_screen();
                } catch (IOException ex) {
                }

                if (Game_Screen.tab < 2 && loadgame == false) {
                    Game_Screen.tabbedPane.insertTab("Load Game", null, load_screen, null, 1);
                    Game_Screen.tab = 1;
                    Game_Screen.tabbedPane.setSelectedIndex(1);
                    loadgame = true;
                }
                if (Game_Screen.tab > 2 && loadgame == false) {
                    Game_Screen.tabbedPane.insertTab("Load Game", null, load_screen, null, 2);
                    Game_Screen.tabbedPane.setSelectedIndex(2);
                    loadgame = true;
                }
            }
        });
        lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_3.setForeground(Color.WHITE);
        add(lblNewLabel_3, BorderLayout.EAST);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setIcon(new ImageIcon(title_screen.class.getResource("/eclipse/logo.jpg")));
        add(lblNewLabel_1, BorderLayout.CENTER);

        JLabel lblNewLabel_4 = new JLabel("Exit Game");
        lblNewLabel_4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_4.setForeground(Color.WHITE);
        add(lblNewLabel_4, BorderLayout.SOUTH);

        core.log("title screen", "title screen has now been rendered");
    }
}

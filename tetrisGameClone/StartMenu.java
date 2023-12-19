package tetrisGameClone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JPanel {
    private final JButton playButton;

    public StartMenu() {
        setLayout(new GridBagLayout());

        ImageIcon backgroundImage = new ImageIcon("imgs/tecnamenu.gif");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        add(backgroundLabel, new GridBagConstraints());

        playButton = new JButton("PLAY!");
        playButton.setFont(new Font("Arial", Font.BOLD, 24));

        playButton.addActionListener(e -> System.out.println("Play button clicked!"));
        GamePanel.se.play(1,false);

        Timer glitterTimer = new Timer(500, new ActionListener() {
            private boolean glitter = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                glitter = !glitter;
                final Color buttonGlitter = new Color(200, 80, 200);
                final Color buttonGlitter2 = new Color(140, 30, 200);

                playButton.setForeground(glitter ? buttonGlitter2 : buttonGlitter);
            }
        });
        glitterTimer.start();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(playButton);
        playButton.setFocusPainted(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(buttonPanel, gbc);
    }
    public JButton getPlayButton() {

        return playButton;
    }
}

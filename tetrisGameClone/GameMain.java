package tetrisGameClone;

import javax.swing.*;

public class GameMain {

	public static void main(String[] args) {
		JFrame window = new JFrame("Tetris");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);

		StartMenu startMenu = new StartMenu();
		window.add(startMenu);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		JButton playButton = startMenu.getPlayButton();

		playButton.addActionListener(e -> {
			window.getContentPane().removeAll();
			GamePanel gp = new GamePanel();
			window.add(gp);
			window.setLocationRelativeTo(null);
			window.pack();
			window.revalidate();
			window.repaint();

			gp.launchGame();
			gp.requestFocusInWindow();
		});
	}
}
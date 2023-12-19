package tetrisGameClone;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("ALL")
public class GamePanel extends JPanel implements Runnable {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public static Sound music = new Sound();
	public static Sound se = new Sound();

	final int FPS = 60;
	Thread gameThread;
	PlayManager pm;
	private Image backgroundImage;

	public GamePanel() {
		try {
			backgroundImage = loadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// panel settings
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(null);
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);

		pm = new PlayManager();
	}

	private Image loadImage() throws IOException {
		return ImageIO.read(Objects.requireNonNull(getClass().getResource("/imgs/tecna2.png")));
	}

	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();

		music.play(0, true);
		music.loop();
	}

	@Override
	public void run() {
		// game loop
		double drawInterval = (double) 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
			if (getParent() != null) {
				Window window = SwingUtilities.windowForComponent(this);
				if (window != null) {
					window.setLocationRelativeTo(null);
				}
			}
		}
	}

	private void update() {
		if (!KeyHandler.pausePressed && !pm.gameOver) {
			pm.update();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		final Color Background_Lilac = new Color(150, 150, 200);
		g.setColor(Background_Lilac);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		int gameBackground = 100;
		g.setColor(new Color(0, 0, 0, gameBackground));
		g.fillRect(459, 49, 367, 607);


		int minoBackground = 100;
		g.setColor(new Color(0, 0, 0, minoBackground));
		g.fillRect(920, 450, 200, 200);
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, 370, 700, this);


			int scoreBackground = 100;
			g.setColor(new Color(0, 0, 0, scoreBackground));
			g.fillRect(920, 50, 250, 300);
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, 370, 700, this);

			}
			Graphics2D g2 = (Graphics2D) g;
			pm.draw(g2);

		}
	}
}
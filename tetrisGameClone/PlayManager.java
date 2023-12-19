package tetrisGameClone;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {

	final int WIDTH = 360;
	final int HEIGHT = 600;
	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;

	// Mino
	Mino currentMino;
	final int MINO_START_X;
	final int MINO_START_Y;
	Mino nextMino;
	final int NEXTMINO_X;
	final int NEXTMINO_Y;
	public static ArrayList<Block> staticBlocks = new ArrayList<>();

	// others

	public static int dropInterval = 60;
	boolean gameOver;

	// Effect

	boolean effectCounterOn;
	int effectCounter;
	ArrayList<Integer> effectY = new ArrayList<>();

	// Score

	int level = 1;
	int lines;
	int score;

	public PlayManager() {

		// Main Play Area Frame
		left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2); // 1280/2 - 360/2 = 460
		right_x = left_x + WIDTH;
		top_y = 50;
		bottom_y = top_y + HEIGHT;

		MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
		MINO_START_Y = top_y + Block.SIZE;

		NEXTMINO_X = right_x + 175;
		NEXTMINO_Y = top_y + 500;
		// set starting mino
		currentMino = pickMino();
		currentMino.setXY(MINO_START_X, MINO_START_Y);
		nextMino = pickMino();
		nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
	}

	private Mino pickMino() {
		Mino mino = null;
		int i = new Random().nextInt(7);

		mino = switch (i) {
			case 0 -> new Mino_L1();
			case 1 -> new Mino_L2();
			case 2 -> new Mino_Square();
			case 3 -> new Mino_Bar();
			case 4 -> new Mino_T();
			case 5 -> new Mino_Z1();
			case 6 -> new Mino_Z2();
			default -> mino;
		};
		return mino;
	}

	public void update() {

		if (!currentMino.active) {
			staticBlocks.add(currentMino.b[0]);
			staticBlocks.add(currentMino.b[1]);
			staticBlocks.add(currentMino.b[2]);
			staticBlocks.add(currentMino.b[3]);


			// gameover check

			if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
				// This means the currentMino collided a block and can't move, so the xy are the same with the nextMino
				gameOver = true;
				GamePanel.music.stop();
				GamePanel.se.play(2,false);

			}

			currentMino.deactivating = false;

			currentMino = nextMino;
			currentMino.setXY(MINO_START_X, MINO_START_Y);
			nextMino = pickMino();
			nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

			checkDelete();
		} else {
			currentMino.update();

		}
	}

	private void checkDelete() {

		int x = left_x;
		int y = top_y;
		int blockCount = 0;
		int lineCount = 0;

		while (x < right_x && y < bottom_y) {

			for (Block staticBlock : staticBlocks) {
				if (staticBlock.x == x && staticBlock.y == y) {
					blockCount++;
				}
			}

			x += Block.SIZE;
			if (x == right_x) {

				if (blockCount == 12) {

					effectCounterOn = true;
					effectY.add(y);

					for (int i = staticBlocks.size() - 1; i > -1; i--) {
						if (staticBlocks.get(i).y == y) {
							staticBlocks.remove(i);
						}
					}
					lineCount++;
					lines++;

					// Drop Speed
					// if the line score hits a certain number, it increases the drop speed
					// 1 is the fastest
					if (lines % 10 == 0 && dropInterval > 1) {

						level++;
						if (dropInterval > 10) {
							dropInterval -= 10;
						} else {
							dropInterval -= 1;
						}
					}


					for (Block staticBlock : staticBlocks) {
						if (staticBlock.y < y) {
							staticBlock.y += Block.SIZE;
						}
					}
					if (lineCount > 0) {
						GamePanel.se.play(1,false);
						int singleLineScore = 10 * level;
						score += singleLineScore * lineCount;
					}
				}

				x = left_x;
				y += Block.SIZE;
			}
		}
	}

	public void draw(Graphics2D g2) {

		// play area
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4f));
		g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

		// NextMino frame
		int x = right_x + 100;
		int y = bottom_y - 200;
		g2.drawRect(x, y, 200, 200);
		g2.setFont(new Font("Arial", Font.PLAIN, 30));
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.drawString("NEXT", x + 60, y + 60);

		// Score frame
		g2.drawRect(x, top_y, 250, 300);
		x += 40;
		y = top_y + 90;
		g2.drawString("LEVEL: " + level, x, y);
		y += 70;
		g2.drawString("LINES: " + lines, x, y);
		y += 70;
		g2.drawString("SCORE: " + score, x, y);

		// CurrentMino
		if (currentMino != null) {
			currentMino.draw(g2);
		}
		nextMino.draw(g2);

		for (Block staticBlock : staticBlocks) {
			staticBlock.draw(g2);
		}

		// Effect Draw

		if (effectCounterOn) {
			effectCounter++;
			g2.setColor(Color.red);
			for (Integer integer : effectY) {
				g2.fillRect(left_x, integer, WIDTH, Block.SIZE);
			}

			if (effectCounter == 10) {
				effectCounterOn = false;
				effectCounter = 0;
				effectY.clear();
			}
		}
		// Pause and Game Over
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(50f));
		if (gameOver) {
			x = left_x + 25;
			y = top_y + 320;
			g2.drawString("GAME OVER", x, y);
		} else if (KeyHandler.pausePressed) {
			x = left_x + 70;
			y = top_y + 320;
			g2.drawString(("PAUSED"), x, y);
		}

	}}
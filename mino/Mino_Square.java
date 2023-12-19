package mino;

import java.awt.Color;

public class Mino_Square extends Mino {
	public static final Color DARK_PURPLE = new Color(169,112,227);

	public Mino_Square() {
		create(DARK_PURPLE);
	}

	public void setXY(int x, int y) {
		// o o 
		// o o
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x;
		b[1].y = b[0].y + Block.SIZE;
		b[2].x = b[0].x + Block.SIZE;
		b[2].y = b[0].y;
		b[3].x = b[0].x + Block.SIZE;
		b[3].y = b[0].y + Block.SIZE;

}

}

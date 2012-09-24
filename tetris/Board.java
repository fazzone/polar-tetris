package tetris;

import java.awt.Color;
import java.awt.Point;

public class Board {
	public static final Color blank = Color.black;
	public final int width, height;
	private Color[][] bx;
	
	Board(int w, int h) {
		width = w;
		height = h;
		bx = new Color[width][height];
		for (int x=0; x<w; x++)
			for (int y=0; y<h; y++)
				bx[x][y] = blank;
	}
	public boolean get(int x, int y) {
		return !bx[x][y].equals(blank);
	}
	public Color getColor(int x, int y) {
		return bx[x][y];
	}
	int clearLines() {
		int nLines = 0;
		for (int row=0; row<height; row++)
			if (hasLine(row)) {
				clearLine(row);
				++nLines;
			}
		return nLines;
	}
	
	void setAll(Point[] ps, Color c) {
		for (Point p : ps)
			bx[p.x][p.y] = c;
	}
	private boolean hasLine(int row) {
		for (int i=0; i<width; i++)
			if (!get(i, row))
				return false;
		return true;
	}
	private void clearLine(int row) {
		for (int i = 0; i < width; i++)
			bx[i][row] = blank;
		for (int y = row-1; y > 0; y--)
			for (int x=0; x<width; x++) {
				Color t = bx[x][y];
				bx[x][y] = bx[x][y+1];
				bx[x][y+1] = t;
			}
	}
}
